package uppu.engine;

import uppu.model.Action;
import uppu.model.Command;
import uppu.model.State;
import uppu.view.PermutationView;

import javax.swing.Timer;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public final class Animation {

    private Timer timer;
    private final PermutationView view;
    private final State leftState;
    private final State rightState;
    private final List<State> states;

    private Animation(
            PermutationView view) {
        this.view = view;
        this.leftState = State.create().offset(50, 50);
        this.rightState = State.create().offset(250, 50);
        this.states = List.of(leftState, rightState);
    }

    public static Animation create(PermutationView view) {
        return new Animation(view);
    }

    public void startAnimation(List<Command> commands) {
        Deque<List<Action>> q = new ArrayDeque<>();
        List<Action> actionsA = leftState.getActions(commands.stream().map(Command::left).toList());
        List<Action> actionsB = rightState.getActions(commands.stream().map(Command::right).toList());
        for (int i = 0; i < commands.size(); i++) {
            q.addLast(List.of(actionsA.get(i), actionsB.get(i)));
        }
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
