package uppu.model;

import io.parmigiano.Permutation;

public class Command {
    
    private final Permutation left;
    private final Permutation right;

    private Command(Permutation left, Permutation right) {
        this.left = left;
        this.right = right;
    }

    public static Command command(Permutation left, Permutation right) {
        return new Command(left, right);
    }

    public Permutation left() {
        return left;
    }

    public Permutation right() {
        return right;
    }
}
