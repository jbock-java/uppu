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

    public static State create() {
        return new State(Quadruple.create(), List.of(Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW));
    }

    public State offset(int x, int y) {
        return new State(quadruple.offset(x, y), colors);
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

    public Quadruple quadruple() {
        return quadruple;
    }
}
