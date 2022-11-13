package uppu.view;

import io.parmigiano.Permutation;
import uppu.engine.Animation;
import uppu.model.BiCommand;
import uppu.model.Label;

import java.util.ArrayList;
import java.util.List;

import static uppu.model.BiCommand.command;

class PermutationViewChecker {

    private static final String INVERT = "\u207B\u00B9";

    private final PermutationView view = PermutationView.create();
    private final Permutation id = Permutation.identity();

    public static void main(String[] args) {
        new PermutationViewChecker().run();
    }

    private void run() {
        view.setLocationRelativeTo(null);
        Permutation a = Permutation.create(0, 1, 2);
        Permutation b = Permutation.create(0, 1, 3);
        List<BiCommand> commands = new ArrayList<>();
        commands.addAll(commands(a, "a", b, "b"));
        commands.addAll(commands(a.invert(), "a" + INVERT, b, "b"));
        commands.addAll(commands(a, "a", b.invert(), "b" + INVERT));
        commands.addAll(commands(a.invert(), "a" + INVERT, b.invert(), "b" + INVERT));
        Animation.create(view).startAnimation(commands);
    }

    private List<BiCommand> commands(
            Permutation a, String aLabel,
            Permutation b, String bLabel) {
        Label label1 = Label.create(bLabel + aLabel);
        Label label2 = Label.create(aLabel + bLabel);
        return List.of(
                BiCommand.wait(label1),
                command(a, id, label1.highlight(bLabel.length())),
                command(b, id, label1.highlight(0, bLabel.length())),
                command(id, b.compose(a), label1.highlight()),
                command(b.compose(a).invert(), b.compose(a).invert(), Label.create("")),
                BiCommand.wait(label2),
                command(b, id, label2.highlight(aLabel.length())),
                command(a, id, label2.highlight(0, aLabel.length())),
                command(id, a.compose(b), label2.highlight()),
                command(a.compose(b).invert(), a.compose(b).invert(), Label.create("")));
    }
}