package uppu.model;

import io.parmigiano.Permutation;

import java.util.List;

public final class State {

    private final List<Color> colors;

    private State(List<Color> colors) {
        this.colors = colors;
    }

    public static State create(Color c0, Color c1, Color c2, Color c3) {
        return new State(List.of(c0, c1, c2, c3));
    }

    public State permute(Permutation p) {
        return new State(p.apply(colors));
    }
}
