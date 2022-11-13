package uppu.view;

import io.parmigiano.Permutation;
import uppu.engine.Animation;
import uppu.model.BiCommand;

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
        return List.of(
                BiCommand.wait(bLabel + aLabel),
                command(a, id, bLabel + aLabel),
                command(b, id, bLabel + aLabel),
                command(id, b.compose(a), bLabel + aLabel),
                command(b.compose(a).invert(), b.compose(a).invert(), bLabel + aLabel),
                BiCommand.wait(aLabel + bLabel),
                command(b, id, aLabel + bLabel),
                command(a, id, aLabel + bLabel),
                command(id, a.compose(b), aLabel + bLabel),
                command(a.compose(b).invert(), a.compose(b).invert(), aLabel + bLabel));
    }
}