package uppu.model;

public final class WaitCommand implements Command {
    
    private final int cycles;

    public WaitCommand(int cycles) {
        this.cycles = cycles;
    }

    public int cycles() {
        return cycles;
    }
}
