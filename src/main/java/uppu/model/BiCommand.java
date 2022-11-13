package uppu.model;

import io.parmigiano.Permutation;

public class BiCommand {
    
    private final Command left;
    private final Command right;
    private final String label;

    private BiCommand(
            Command left,
            Command right,
            String label) {
        this.left = left;
        this.right = right;
        this.label = label;
    }

    public static BiCommand command(
            Permutation left,
            Permutation right,
            String label) {
        return new BiCommand(new MoveCommand(left), new MoveCommand(right), label);
    }

    public static BiCommand wait(String label) {
        return new BiCommand(new WaitCommand(), new WaitCommand(), label);
    }

    public Command left() {
        return left;
    }

    public Command right() {
        return right;
    }

    public String label() {
        return label;
    }
}
