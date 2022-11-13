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
    private final State rightState;

    private Animation(
            PermutationView view) {
        this.view = view;
        this.leftState = State.create().offset(50, 50);
        this.rightState = State.create().offset(250, 50);
    }

    public static Animation create(PermutationView view) {
        return new Animation(view);
    }

    public void startAnimation(List<BiCommand> commands) {
        Deque<Phase> q = new ArrayDeque<>();
        List<Action> actionsA = leftState.getActions(commands.stream().map(BiCommand::left).toList());
        List<Action> actionsB = rightState.getActions(commands.stream().map(BiCommand::right).toList());
        for (int i = 0; i < commands.size(); i++) {
            q.addLast(Phase.create(commands.get(i).label(), List.of(actionsA.get(i), actionsB.get(i))));
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
                action.show(g, phase.label());
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
}
