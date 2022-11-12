package uppu.engine;

import io.parmigiano.Permutation;
import uppu.model.Action;
import uppu.model.State;
import uppu.view.PermutationView;

import javax.swing.Timer;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public final class Animation {

    private Timer timer;
    private final PermutationView view;
    private final Deque<Permutation> queue;

    private Animation(
            PermutationView view,
            Deque<Permutation> queue) {
        this.view = view;
        this.queue = queue;
    }

    public static Animation create(
            PermutationView view,
            List<Permutation> permutations) {
        Deque<Permutation> queue = new ArrayDeque<>();
        for (Permutation p : permutations) {
            int order = p.order();
            for (int i = 0; i < order; i++) {
                queue.addLast(p);
            }
            for (int i = 0; i < order; i++) {
                queue.addLast(p.invert());
            }
        }
        return new Animation(view, queue);
    }

    public void startAnimation(State state) {
        Action action = state.permute(queue.pollFirst());
        Movers movers = action.movers();
        timer = new Timer(25, __ -> {
            if (!movers.move()) {
                timer.stop();
                if (!queue.isEmpty()) {
                    setTimeout(1000, () -> startAnimation(action.target()));
                }
            }
            view.show(state.quadruple());
        });
        timer.start();
    }

    private void setTimeout(int millis, Runnable task) {
        Timer t = new Timer(millis, ___ -> task.run());
        t.setRepeats(false);
        t.start();
    }
}
