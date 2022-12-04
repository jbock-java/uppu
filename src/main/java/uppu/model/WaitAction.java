package uppu.model;

import java.awt.Graphics2D;

public final class WaitAction extends Action {

    private final int cyclesInit;
    private int cycles;

    private WaitAction(int cycles) {
        this.cyclesInit = cycles;
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
    public void show(Graphics2D g) {
    }

    @Override
    String type() {
        return "WAIT";
    }

    @Override
    public void init() {
        this.cycles = cyclesInit;
    }
}
