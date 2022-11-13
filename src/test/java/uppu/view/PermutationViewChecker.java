package uppu.view;

import io.parmigiano.Permutation;
import uppu.engine.Animation;
import uppu.model.Command;

import java.util.ArrayList;
import java.util.List;

import static uppu.model.Command.command;

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
        List<Command> commands = new ArrayList<>();
        commands.addAll(commands(a, "a", b, "b"));
        commands.addAll(commands(a.invert(), "a" + INVERT, b, "b"));
        commands.addAll(commands(a, "a", b.invert(), "b" + INVERT));
        commands.addAll(commands(a.invert(), "a" + INVERT, b.invert(), "b" + INVERT));
        Animation.create(view).startAnimation(commands);
    }

    private List<Command> commands(
            Permutation a, String aLabel,
            Permutation b, String bLabel) {
        return List.of(
                command(a, id, aLabel),
                command(b, id, bLabel),
                command(id, b.compose(a), bLabel + aLabel),
                command(b.compose(a).invert(), b.compose(a).invert(), ""),
                command(b, id, bLabel),
                command(a, id, aLabel),
                command(id, a.compose(b), aLabel + bLabel),
                command(a.compose(b).invert(), a.compose(b).invert(), ""));
    }
}