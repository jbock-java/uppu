package uppu.model;

import io.parmigiano.Permutation;

import java.util.List;

public class BiCommand {

    private final Permutation permutation;
    private final String title;
    private final List<Command> commands;

    public BiCommand(
            Permutation permutation,
            String title,
            List<Command> commands) {
        this.permutation = permutation;
        this.title = title;
        this.commands = commands;
    }

    public List<Command> commands() {
        return commands;
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
