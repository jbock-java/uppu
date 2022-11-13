package uppu.model;

import io.parmigiano.Permutation;

public class BiCommand {
    
    private final Command left;
    private final Command right;
    private final Label label;

    private BiCommand(
            Command left,
            Command right,
            Label label) {
        this.left = left;
        this.right = right;
        this.label = label;
    }

    public static BiCommand command(
            Permutation left,
            Permutation right,
            Label label) {
        return new BiCommand(new MoveCommand(left), new MoveCommand(right), label);
    }

    public static BiCommand wait(Label label) {
        return new BiCommand(new WaitCommand(), new WaitCommand(), label);
    }

    public Command left() {
        return left;
    }

    public Command right() {
        return right;
    }

    public Label label() {
        return label;
    }
}
