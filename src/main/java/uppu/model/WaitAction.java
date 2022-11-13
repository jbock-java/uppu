package uppu.model;

import java.awt.Graphics2D;

public final class WaitAction extends Action {
    
    private int cycles = 50;
    private final State state;

    private WaitAction(State state) {
        this.state = state;
    }

    static WaitAction create(State state) {
        return new WaitAction(state);
    }

    @Override
    public boolean move() {
        cycles--;
        return cycles >= 0;
    }

    @Override
    public State finalState() {
        return state;
    }

    @Override
    public void show(Graphics2D g, String label) {
        showLabel(g, label);
    }
}
