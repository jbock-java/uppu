package uppu.model;

import io.parmigiano.Permutation;

public final class MoveCommand implements Command {

    private final Permutation p;

    MoveCommand(Permutation p) {
        this.p = p;
    }

    Permutation permutation() {
        return p;
    }
}
