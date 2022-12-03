package uppu.model;

import java.awt.Graphics2D;

public final class WaitAction extends Action {

    private final int cyclesInit;
    private int cycles;

    private WaitAction(String title, int cycles) {
        super(title);
        this.cyclesInit = cycles;
        this.cycles = cycles;
    }

    static WaitAction create(String title, int cycles) {
        return new WaitAction(title, cycles);
    }

    @Override
    public boolean move() {
        cycles--;
        return cycles >= 0;
    }

    public void skipWait() {
        cycles = 1;
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

    @Override
    public boolean isShowState() {
        return false;
    }
}
