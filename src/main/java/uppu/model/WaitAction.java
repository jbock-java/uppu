package uppu.model;

import java.awt.Graphics2D;

public final class WaitAction extends Action {
    
    private int cycles;
    private final State state;

    private WaitAction(int cycles, State state) {
        this.cycles = cycles;
        this.state = state;
    }

    static WaitAction create(int cycles, State state) {
        return new WaitAction(cycles, state);
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
    public void show(Graphics2D g, Label label) {
    }
}
