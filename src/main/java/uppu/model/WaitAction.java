package uppu.model;

import java.awt.Graphics2D;

public final class WaitAction extends Action {
    
    private int cycles;

    private WaitAction(int cycles) {
        this.cycles = cycles;
    }

    static WaitAction create(int cycles) {
        return new WaitAction(cycles);
    }

    @Override
    public boolean move() {
        cycles--;
        return cycles >= 0;
    }

    @Override
    public void show(Graphics2D g, Label label) {
    }
}
