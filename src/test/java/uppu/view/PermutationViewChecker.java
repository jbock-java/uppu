package uppu.view;

import io.parmigiano.Permutation;
import uppu.engine.Movers;
import uppu.model.Action;
import uppu.model.Color;
import uppu.model.Quadruple;
import uppu.model.State;

import javax.swing.SwingUtilities;
import javax.swing.Timer;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

class PermutationViewChecker {

    private final PermutationView view = PermutationView.create();
    private final Quadruple quadruple = Quadruple.create();
    private final Deque<Permutation> queue = new ArrayDeque<>();
    private Timer timer;

    public static void main(String[] args) {
        new PermutationViewChecker().run();
    }

    private void run() {
        view.setLocationRelativeTo(null);
        SwingUtilities.invokeLater(() -> {
            view.show(quadruple, 50, 50);
            view.show(quadruple, 50, 50);
        });
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
        for (Permutation p : permutations) {
            int order = p.order();
            for (int i = 0; i < order; i++) {
                queue.addLast(p);
            }
            for (int i = 0; i < order; i++) {
                queue.addLast(p.invert());
            }
        }
        setTimeout(() -> startAnimation(
                State.create(quadruple, Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW)));
    }

    private void startAnimation(
            State state) {
        Action action = state.permute(queue.pollFirst());
        Movers movers = action.movers();
        timer = new Timer(25, __ -> {
            if (!movers.move()) {
                timer.stop();
                if (!queue.isEmpty()) {
                    setTimeout(() -> startAnimation(action.target()));
                }
            }
            view.show(quadruple, 50, 50);
        });
        timer.start();
    }

    private void setTimeout(Runnable task) {
        Timer t = new Timer(1000, ___ -> task.run());
        t.setRepeats(false);
        t.start();
    }
}