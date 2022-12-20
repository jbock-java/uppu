package uppu;

import io.jbock.util.Either;
import io.parmigiano.Permutation;
import uppu.engine.Animation;
import uppu.model.BiAction;
import uppu.model.BiCommand;
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
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.JOptionPane.showMessageDialog;

public class S4Checker {

    private final PermutationView view = PermutationView.create();
    private final CommandLine commandLine;
    private final State state;

    private S4Checker(
            CommandLine commandLine,
            State state) {
        this.commandLine = commandLine;
        this.state = state;
    }

    public static void main(String[] args) throws IOException {
        CommandLine commandLine = new CommandLineParser().parseOrExit(args);
        List<String> lines = Files.readAllLines(commandLine.input().toPath());
        readLines(lines).ifLeftOrElse(
                error -> showMessageDialog(null, error, "Error", JOptionPane.ERROR_MESSAGE),
                commands -> {
                    State state = State.create(4).offset((int) (25 * HomePoints.SCALE), (int) (20 * HomePoints.SCALE));
                    List<BiAction> actions = state.getActions(commands);
                    new S4Checker(commandLine, state).run(actions);
                });
    }

    private void run(List<BiAction> actions) {
        view.setLocationRelativeTo(null);
        Animation animation = Animation.create(view);
        view.setOnActionSelected(animation::select);
        view.setOnSliderMoved(value -> animation.setSpeed(value <= 16 ? (0.5f + value / 32f) : value / 16f));
        view.setOnEditButtonClicked(() -> {
            if (animation.isRunning()) {
                view.setRunning(false);
                animation.setRunning(false);
            }
            InputView inputView = InputView.create(view);
            inputView.setContent(animation.getActions());
            inputView.setOnSave(lines -> {
                readLines(lines).ifLeftOrElse(
                        error -> showMessageDialog(view, error, "Error", JOptionPane.ERROR_MESSAGE),
                        newCommands -> {
                            List<BiAction> newActions = state.getActions(newCommands);
                            animation.setActions(newActions);
                            view.setActions(newActions);
                            try {
                                Files.write(commandLine.input().toPath(), newActions.stream().map(BiAction::toString).toList(), StandardOpenOption.TRUNCATE_EXISTING);
                            } catch (IOException e) {
                                JOptionPane.showMessageDialog(view, e, "Error", JOptionPane.ERROR_MESSAGE);
                            }
                            newActions.stream().findFirst().ifPresent(view::setSelectedAction);
                        });
                inputView.dispose();
            });
        });
        view.setOnPauseButtonClicked(() -> {
            view.setRunning(!animation.isRunning());
            animation.setRunning(!animation.isRunning());
        });
        animation.setOnNext(view::setSelectedAction);
        SwingUtilities.invokeLater(() -> {
            view.setRunning(true);
            animation.setRunning(true);
            animation.setActions(actions);
            view.setActions(actions);
            actions.stream().findFirst().ifPresent(view::setSelectedAction);
        });
    }

    private static Either<String, List<BiCommand>> readLines(List<String> lines) {
        Permutation current = Permutation.identity();
        List<BiCommand> result = new ArrayList<>(lines.size());
        for (String line : lines) {
            Either<String, Row> parsed = LineParser.parse(line);
            if (parsed.isLeft()) {
                return parsed.map(x -> List.of());
            }
            Row row = parsed.getRight().orElseThrow();
            BiCommand command = BiCommand.singleCommand(row, current);
            result.add(command);
            current = command.permutation().compose(current);
        }
        return Either.right(result);
    }
}