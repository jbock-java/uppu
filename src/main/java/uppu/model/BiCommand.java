package uppu.model;

import io.parmigiano.Permutation;

import java.util.List;

public class BiCommand {

    private final String title;
    private final List<Command> left;

    public BiCommand(String title, List<Command> left) {
        this.title = title;
        this.left = left;
    }

    public static Command command(Permutation left) {
        return new MoveCommand(left);
    }

    public static Command wait(int cycles) {
        return new WaitCommand(cycles);
    }

    public List<Command> left() {
        return left;
    }

    public String title() {
        return title;
    }

    @Override
    public String toString() {
        return title;
    }
}
