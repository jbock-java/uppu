package uppu.view;

import io.parmigiano.Permutation;
import uppu.engine.Animation;
import uppu.model.Command;

import java.util.ArrayList;
import java.util.List;

import static uppu.model.Command.command;

class PermutationViewChecker {

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
        commands.addAll(commands(a, b));
        commands.addAll(commands(a.invert(), b));
        commands.addAll(commands(a, b.invert()));
        commands.addAll(commands(a.invert(), b.invert()));
        Animation.create(view).startAnimation(commands);
    }

    private List<Command> commands(Permutation a, Permutation b) {
        return List.of(
                command(a, id),
                command(b, id),
                command(id, b.compose(a)),
                command(b.compose(a).invert(), b.compose(a).invert()),
                command(b, id),
                command(a, id),
                command(id, a.compose(b)),
                command(a.compose(b).invert(), a.compose(b).invert()));
    }
}