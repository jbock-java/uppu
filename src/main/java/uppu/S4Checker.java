package uppu;

import io.jbock.util.Either;
import io.parmigiano.Permutation;
import uppu.engine.Animation;
import uppu.model.ActionSequence;
import uppu.model.CommandSequence;
import uppu.model.HomePoints;
import uppu.model.State;
import uppu.parse.LineParser;
import uppu.parse.Row;
import uppu.view.InputView;
import uppu.view.PermutationView;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import static io.jbock.util.Either.right;
import static javax.swing.JOptionPane.showMessageDialog;

public class S4Checker {

    private final PermutationView view;
    private final CommandLine commandLine;
    private final State state;
    private final Animation animation;

    private S4Checker(
            PermutationView view, CommandLine commandLine,
            State state,
            Animation animation) {
        this.view = view;
        this.commandLine = commandLine;
        this.state = state;
        this.animation = animation;
    }

    public static void main(String[] args) throws IOException {
        CommandLine commandLine = new CommandLineParser().parseOrExit(args);
        List<String> lines = Files.readAllLines(commandLine.input().toPath());
        readLines(lines).ifLeftOrElse(
                error -> showMessageDialog(null, error, "Error", JOptionPane.ERROR_MESSAGE),
                sequences -> {
                    State state = State.create(4).offset((int) (25 * HomePoints.SCALE), (int) (20 * HomePoints.SCALE));
                    List<ActionSequence> actions = state.getActions(sequences);
                    PermutationView view = PermutationView.create();
                    new S4Checker(view, commandLine, state, Animation.create(view)).run(actions);
                });
    }

    private void run(List<ActionSequence> actions) {
        view.setLocationRelativeTo(null);
        view.setOnActionSelected(animation::select);
        view.setOnSliderMoved(value -> animation.setSpeed(value <= 16 ? (0.5f + value / 32f) : value / 16f));
        view.setOnEditButtonClicked(() -> {
            setRunning(false);
            InputView inputView = InputView.create(view);
            inputView.setContent(animation.getActions());
            inputView.setOnSave(lines -> {
                readLines(lines).ifLeftOrElse(
                        error -> showMessageDialog(view, error, "Error", JOptionPane.ERROR_MESSAGE),
                        newCommands -> {
                            List<ActionSequence> newActions = state.getActions(newCommands);
                            animation.setActions(newActions);
                            view.setActions(newActions);
                            writeToFile(newActions);
                            newActions.stream().findFirst().ifPresent(view::setSelectedAction);
                            setRunning(true);
                        });
                inputView.dispose();
            });
        });
        view.setOnPauseButtonClicked(() -> setRunning(!animation.isRunning()));
        animation.setOnNext(view::setSelectedAction);
        SwingUtilities.invokeLater(() -> {
            setRunning(true);
            animation.setActions(actions);
            view.setActions(actions);
            actions.stream().findFirst().ifPresent(view::setSelectedAction);
        });
    }

    private void setRunning(boolean running) {
        view.setRunning(running);
        animation.setRunning(running);
    }

    private void writeToFile(List<ActionSequence> newActions) {
        try {
            List<String> lines = newActions.stream().map(ActionSequence::toString).toList();
            Path p = commandLine.input().toPath();
            Files.write(p, lines, StandardOpenOption.TRUNCATE_EXISTING);
            Files.writeString(p, System.lineSeparator(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            showMessageDialog(view, e, "IO Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static Either<String, List<CommandSequence>> readLines(List<String> lines) {
        Permutation current = Permutation.identity();
        List<CommandSequence> result = new ArrayList<>(lines.size());
        for (String line : lines) {
            Either<String, Row> parsed = LineParser.parse(line);
            if (parsed.isLeft()) {
                return parsed.map(x -> List.of());
            }
            Row row = parsed.getRight().orElseThrow();
            CommandSequence.Result r = CommandSequence.toSequence(row, current);
            result.add(r.sequence());
            current = r.permutation().compose(current);
        }
        return right(result);
    }
}