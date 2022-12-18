package uppu.engine;

import uppu.model.Action;
import uppu.model.BiAction;
import uppu.view.PermutationView;

import javax.swing.Timer;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public final class Animation {

    private Timer timer = new Timer(25, __ -> {
        if (onTimerTick()) {
            showState();
        }
    });

    private final PermutationView view;
    private final List<BiAction> q = new ArrayList<>();
    private Consumer<BiAction> onNext = action -> {
    };
    private int current;

    private Animation(PermutationView view) {
        this.view = view;
    }

    public static Animation create(PermutationView view) {
        return new Animation(view);
    }

    private boolean onTimerTick() {
        BiAction biAction = peekFirst();
        if (biAction == null) {
            view.setTitle("");
            timer.stop();
            return false;
        }
        Action action = biAction.peekFirst();
        if (action == null) {
            current++;
            cleanCurrent();
            BiAction next = peekFirst();
            if (next != null) {
                onNext.accept(next);
            }
            return false;
        }
        boolean anyMove = action.move();
        if (!anyMove) {
            biAction.increment();
            return false;
        }
        return true;
    }

    private void showState() {
        BiAction biAction = peekFirst();
        if (biAction == null) {
            return;
        }
        Action action = biAction.peekFirst();
        if (action == null) {
            return;
        }
        BufferStrategy bufferStrategy = view.getBufferStrategy();
        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
        action.show(g);
        bufferStrategy.show();
        g.dispose();
        Toolkit.getDefaultToolkit().sync();
    }

    private void cleanCurrent() {
        BiAction biAction = peekFirst();
        if (biAction == null) {
            view.setTitle("");
            return;
        }
        view.setTitle(biAction.title());
        if (current == 0) {
            return;
        }
        biAction.init();
    }

    private BiAction peekFirst() {
        return get(current);
    }

    private BiAction get(int n) {
        if (n >= q.size()) {
            return null;
        }
        return q.get(n);
    }

    public void setRunning(boolean running) {
        if (running && !timer.isRunning()) {
            timer.start();
            return;
        }
        if (!running && timer.isRunning()) {
            timer.stop();
        }
    }

    public boolean isRunning() {
        return timer.isRunning();
    }

    public void setSpeed(float speed) {
        timer.stop();
        timer = new Timer((int) (25 / speed), __ -> {
            onTimerTick();
            showState();
        });
        timer.start();
    }

    public void select(BiAction action) {
        for (int i = 0; i < q.size(); i++) {
            BiAction qs = q.get(i);
            if (action == qs) {
                current = i;
                cleanCurrent();
            }
        }
    }

    public void setOnNext(Consumer<BiAction> onNext) {
        this.onNext = onNext;
    }

    public List<BiAction> getActions() {
        return q;
    }

    public void setActions(List<BiAction> actions) {
        q.clear();
        q.addAll(actions);
        actions.stream().findFirst().map(BiAction::title).ifPresent(view::setTitle);
    }
}
