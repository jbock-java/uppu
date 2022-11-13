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
        Permutation a = Permutation.create(2, 3);
        Permutation b = Permutation.create(1, 2);
        Permutation c = Permutation.create(0, 1, 2);
        Permutation d = Permutation.create(0, 1, 3);
        List<BiCommand> commands = new ArrayList<>();
        commands.addAll(commands(b, "b", a, "a"));
        commands.addAll(commands(c, "c", a, "a"));
        commands.addAll(commands(d, "d", c, "c"));
        Animation.create(view).startAnimation(commands);
    }

    private List<BiCommand> commands(
            Permutation a, String aLabel,
            Permutation b, String bLabel) {
        Label label1 = Label.create(bLabel + aLabel);
        Label label2 = Label.create(aLabel + bLabel);
        return List.of(
                BiCommand.showState(label1),
                BiCommand.wait(50),
                command(a, id, label1.highlight(bLabel.length())),
                BiCommand.wait(20),
                command(b, id, label1.highlight(0, bLabel.length())),
                BiCommand.wait(20),
                command(id, b.compose(a), label1.highlight()),
                BiCommand.wait(20),
                command(b.compose(a).invert(), b.compose(a).invert(), Label.create("")),
                BiCommand.showState(label2),
                BiCommand.wait(50),
                command(b, id, label2.highlight(aLabel.length())),
                BiCommand.wait(20),
                command(a, id, label2.highlight(0, aLabel.length())),
                BiCommand.wait(20),
                command(id, a.compose(b), label2.highlight()),
                BiCommand.wait(20),
                command(a.compose(b).invert(), a.compose(b).invert(), Label.create("")));
    }
}