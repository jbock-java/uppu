package uppu.view;

import io.parmigiano.Permutation;
import uppu.engine.Animation;
import uppu.model.State;

import java.util.List;

class PermutationViewChecker {

    private final PermutationView view = PermutationView.create();

    public static void main(String[] args) {
        new PermutationViewChecker().run();
    }

    private void run() {
        view.setLocationRelativeTo(null);
        List<Permutation> permutations = List.of(Permutation.create(0, 1, 2),
                Permutation.create(0, 1, 3),
                Permutation.create(0, 2, 3),
                Permutation.create(1, 2, 3),
                Permutation.create(0, 1).compose(2, 3),
                Permutation.create(0, 2).compose(1, 3),
                Permutation.create(0, 3).compose(1, 2),
                Permutation.create(0, 1, 2, 3),
                Permutation.create(0, 1, 3, 2),
                Permutation.create(0, 2, 1, 3));
        Permutation c1 = Permutation.create(0, 2, 1).invert();
        Permutation c2 = Permutation.create(0, 3, 1).invert();
        Permutation id = Permutation.identity();
        Animation.create(view)
                .startAnimation(
                        List.of(c1, c2, id),
                        List.of(id, id, c2.compose(c1)));
    }
}