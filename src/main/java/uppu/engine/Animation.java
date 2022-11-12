package uppu.engine;

import io.parmigiano.Permutation;
import uppu.model.Action;
import uppu.model.State;
import uppu.view.PermutationView;

import javax.swing.Timer;
import java.util.ArrayList;
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
        List<Action> actions = new ArrayList<>();
        Permutation permutation = p;
        for (State state : states) {
            Action stateAction = state.getAction(permutation);
            actions.add(stateAction);
            permutation = permutation.invert();
        }
        timer = new Timer(25, __ -> {
            boolean anyMove = false;
            for (Action action : actions) {
                anyMove |= action.move();
            }
            if (!anyMove) {
                timer.stop();
            }
            view.show(states);
        });
        timer.start();
    }
}
