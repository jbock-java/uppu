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
                if (timer.isRunning()) {
                    current++;
                    cleanCurrent();
                }
            }
            bufferStrategy.show();
            g.dispose();
            Toolkit.getDefaultToolkit().sync();
        });
        timer.start();
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
        return get(current);
    }

    private Action get(int n) {
        if (n >= q.size()) {
            return null;
        }
        return q.get(n);
    }

    public void ff() {
        timer.stop();
        if (isShowState(current)) {
            current++;
        }
        while (current < q.size() && !isShowState(current)) {
            current++;
        }
        cleanCurrent();
        timer.start();
    }

    private boolean isShowState(int n) {
        Action action = get(n);
        if (action == null) {
            return false;
        }
        return action.isShowState();
    }

    public void rewind() {
        timer.stop();
        for (int i = 0; i < 2; i++) {
            if (isShowState(current)) {
                current--;
            }
            while (current >= 0 && !isShowState(current)) {
                current--;
            }
        }
        cleanCurrent();
        timer.start();
    }

    public void pause() {
        if (timer.isRunning()) {
            timer.stop();
        } else {
            timer.start();
        }
    }
}
