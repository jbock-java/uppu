package uppu.model;

import io.parmigiano.Permutation;
import uppu.engine.Mover;

import java.util.ArrayList;
import java.util.List;

public final class State {

    private final Quadruple quadruple;
    private final List<Color> colors;

    private State(Quadruple quadruple, List<Color> colors) {
        this.quadruple = quadruple;
        this.colors = colors;
    }

    public static State create(Quadruple quadruple, Color c0, Color c1, Color c2, Color c3) {
        return new State(quadruple, List.of(c0, c1, c2, c3));
    }

    public Action permute(Permutation p) {
        List<Mover> movers = new ArrayList<>();
        Color[] newColors = new Color[4];
        for (int i = 0; i < colors.size(); i++) {
            Color color = colors.get(i);
            int j = p.apply(i);
            if (j != i) {
                Slot targetSlot = Slot.forIndex(j);
                movers.add(new Mover(color, quadruple, targetSlot));
            }
            newColors[j] = color;
        }
        return Action.create(new State(quadruple, List.of(newColors)), movers);
    }
}
