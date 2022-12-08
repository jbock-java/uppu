package uppu.view;

import io.jbock.util.Either;
import io.jbock.util.Eithers;
import uppu.engine.Animation;
import uppu.input.Input;
import uppu.model.BiAction;
import uppu.model.BiCommand;
import uppu.model.Slot;
import uppu.parse.LineParser;

import javax.swing.JOptionPane;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static javax.swing.JOptionPane.showMessageDialog;

class S4Checker {

    private final PermutationView view = PermutationView.create();
    private final List<BiCommand> commands;

    S4Checker(List<BiCommand> commands) {
        this.commands = commands;
    }

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("input").resolve("tricks.txt"));
        readLines(lines).ifLeftOrElse(
                error -> showMessageDialog(null, error, "Error", JOptionPane.ERROR_MESSAGE),
                commands -> new S4Checker(commands).run());
    }

    private void run() {
        view.setLocationRelativeTo(null);
        Animation animation = Animation.create(view, 4, (int) (50 * Slot.SCALE));
        List<BiAction> actions = animation.startAnimation(commands);
        view.setActions(actions);
        view.setOnActionSelected(animation::select);
        view.setOnSliderMoved(value -> animation.setSpeed(value <= 16 ? (0.5f + value / 32f) : value / 16f));
        view.setOnEditButtonClicked(() -> {
            if (animation.isRunning()) {
                view.setRunning(animation.togglePause());
            }
            InputView inputView = InputView.create(view);
            inputView.setContent(animation.getActions());
            inputView.setOnSave(lines -> {
                readLines(lines).ifLeftOrElse(
                        error -> showMessageDialog(view, error, "Error", JOptionPane.ERROR_MESSAGE),
                        newCommands -> {
                            List<BiAction> newActions = animation.startAnimation(newCommands);
                            view.setActions(newActions);
                            newActions.stream().findFirst().ifPresent(view::setSelectedAction);
                        });
                inputView.dispose();
            });
        });
        view.setOnPauseButtonClicked(() -> view.setRunning(animation.togglePause()));
        actions.stream().findFirst().ifPresent(view::setSelectedAction);
        animation.setOnNext(view::setSelectedAction);
        view.setRunning(animation.togglePause());
        view.validate();
    }
    
    private static Either<String, List<BiCommand>> readLines(List<String> lines) {
        return lines.stream()
                .map(line -> LineParser.parse(line).map(Input::singleCommand))
                .collect(Eithers.firstFailure());
    }
}