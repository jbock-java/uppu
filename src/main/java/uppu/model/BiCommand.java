package uppu.model;

import io.parmigiano.Permutation;

public class BiCommand {
    
    private final Command left;

    private BiCommand(Command left) {
        this.left = left;
    }

    public static BiCommand command(Permutation left) {
        return new BiCommand(new MoveCommand(left));
    }

    public static BiCommand wait(int cycles) {
        return new BiCommand(new WaitCommand(cycles));
    }

    public static BiCommand showState() {
        return new BiCommand(new ShowStateCommand());
    }

    public Command left() {
        return left;
    }
}
