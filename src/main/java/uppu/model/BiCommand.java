package uppu.model;

import io.parmigiano.Permutation;

import java.util.List;

public class BiCommand {

    private final List<Command> left;

    public BiCommand(List<Command> left) {
        this.left = left;
    }

    public static Command command(Permutation left) {
        return new MoveCommand(left);
    }

    public static Command wait(int cycles) {
        return new WaitCommand(cycles);
    }

    public static Command showState() {
        return new ShowStateCommand();
    }

    public List<Command> left() {
        return left;
    }
}
