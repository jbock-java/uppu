package uppu.model;

import io.parmigiano.Permutation;

import java.util.List;

public class BiCommand {

    private final Permutation permutation;
    private final String title;
    private final List<Command> left;

    public BiCommand(
            Permutation permutation,
            String title,
            List<Command> left) {
        this.permutation = permutation;
        this.title = title;
        this.left = left;
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

    public Permutation permutation() {
        return permutation;
    }
}
