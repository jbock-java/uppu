package uppu.model;

import io.parmigiano.Permutation;

public class Command {
    
    private final Permutation left;
    private final Permutation right;
    private final String label;

    private Command(
            Permutation left,
            Permutation right,
            String label) {
        this.left = left;
        this.right = right;
        this.label = label;
    }

    public static Command command(
            Permutation left,
            Permutation right,
            String label) {
        return new Command(left, right, label);
    }

    public Permutation left() {
        return left;
    }

    public Permutation right() {
        return right;
    }

    public String label() {
        return label;
    }
}
