package uppu.engine;

import uppu.model.Action;
import uppu.model.BiCommand;
import uppu.model.Phase;
import uppu.model.State;
import uppu.view.PermutationView;

import javax.swing.Timer;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public final class Animation {

    private Timer timer;
    private final PermutationView view;
    private final State leftState;
    private final Deque<Phase> q = new ArrayDeque<>();

    private Animation(
            PermutationView view,
            State leftState) {
        this.view = view;
        this.leftState = leftState;
    }

    public static Animation create(
            PermutationView view,
            int n,
            int offset) {
        return new Animation(view, State.create(n).offset(offset, offset));
    }

    public void startAnimation(List<BiCommand> commands) {
        List<Action> actionsA = leftState.getActions(commands.stream().map(BiCommand::left).toList());
        for (int i = 0; i < commands.size(); i++) {
            q.addLast(Phase.create(List.of(actionsA.get(i))));
        }
        timer = new Timer(25, __ -> {
            Phase phase = q.peekFirst();
            if (phase == null) {
                timer.stop();
                return;
            }
            BufferStrategy bufferStrategy = view.getBufferStrategy();
            Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
            boolean anyMove = false;
            for (Action action : phase.actions()) {
                anyMove |= action.move();
                action.show(g);
            }
            if (!anyMove) {
                q.removeFirst();
            }
            bufferStrategy.show();
            g.dispose();
            Toolkit.getDefaultToolkit().sync();
        });
        timer.start();
    }

    public void skip() {
        q.pop();
    }
}
