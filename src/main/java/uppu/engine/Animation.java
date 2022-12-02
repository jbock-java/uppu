package uppu.engine;

import uppu.model.Action;
import uppu.model.BiCommand;
import uppu.model.State;
import uppu.model.WaitAction;
import uppu.view.PermutationView;

import javax.swing.Timer;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.List;

public final class Animation {

    private static final int SKIP_SIZE = 2;

    private Timer timer;
    private final PermutationView view;
    private final State leftState;
    private final List<Action> q = new ArrayList<>();
    private int current;

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
        List<Action> actionsA = leftState.getActions(commands);
        q.addAll(actionsA);
        view.setTitle(actionsA.get(0).title());
        timer = new Timer(25, __ -> {
            Action action = peekFirst();
            if (action == null) {
                view.setTitle("");
                timer.stop();
                return;
            }
            BufferStrategy bufferStrategy = view.getBufferStrategy();
            Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
            boolean anyMove = action.move();
            action.show(g);
            if (!anyMove) {
                increaseCurrent(1);
            }
            bufferStrategy.show();
            g.dispose();
            Toolkit.getDefaultToolkit().sync();
        });
        timer.start();
    }

    private void increaseCurrent(int n) {
        current += n;
        cleanCurrent();
    }

    private void decreaseCurrent() {
        current -= SKIP_SIZE;
        if (current < 0) {
            current = 0;
        }
        cleanCurrent();
    }

    private void cleanCurrent() {
        Action action = peekFirst();
        if (action == null) {
            view.setTitle("");
            return;
        }
        view.setTitle(action.title());
        if (current == 0) {
            return;
        }
        if (action instanceof WaitAction) {
            ((WaitAction) action).skipWait();
        }
        action.init();
    }

    private Action peekFirst() {
        if (current >= q.size()) {
            return null;
        }
        return q.get(current);
    }

    public void ff() {
        increaseCurrent(SKIP_SIZE);
    }

    public void rewind() {
        decreaseCurrent();
        if (!timer.isRunning()) {
            timer.start();
        }
    }

    public void pause() {
        if (timer.isRunning()) {
            timer.stop();
        } else {
            timer.start();
        }
    }
}
