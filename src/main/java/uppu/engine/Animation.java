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
    private final Permutation p;
    private final List<State> states;

    private Animation(
            PermutationView view,
            Permutation p,
            List<State> states) {
        this.view = view;
        this.p = p;
        this.states = states;
    }

    public static Animation create(
            PermutationView view,
            Permutation p,
            List<State> states) {
        return new Animation(view, p, states);
    }

    public void startAnimation() {
        Deque<List<Action>> q = new ArrayDeque<>();
        List<Action> actionsA = states.get(0).getActions(List.of(p, p.invert()));
        List<Action> actionsB = states.get(1).getActions(List.of(p.invert(), p));
        q.addLast(List.of(actionsA.get(0), actionsB.get(0)));
        q.addLast(List.of(actionsA.get(1), actionsB.get(1)));
        timer = new Timer(25, __ -> {
            List<Action> actions = q.peekFirst();
            if (actions == null) {
                timer.stop();
                return;
            }
            boolean anyMove = false;
            for (Action action : actions) {
                anyMove |= action.move();
            }
            if (!anyMove) {
                q.removeFirst();
            }
            view.show(states);
        });
        timer.start();
    }
}
