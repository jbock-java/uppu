package uppu.view;

import io.parmigiano.Permutation;
import uppu.engine.Animation;

import java.util.List;

import static uppu.model.Command.command;

class PermutationViewChecker {

    private final PermutationView view = PermutationView.create();

    public static void main(String[] args) {
        new PermutationViewChecker().run();
    }

    private void run() {
        view.setLocationRelativeTo(null);
        Permutation a = Permutation.create(0, 1, 2);
        Permutation b = Permutation.create(0, 1, 3);
        Permutation id = Permutation.identity();
        Animation.create(view).startAnimation(List.of(
                command(a, id),
                command(b, id),
                command(id, b.compose(a)),
                command(b.compose(a).invert(), b.compose(a).invert())));
    }
}