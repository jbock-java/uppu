package uppu.model;

import uppu.engine.Mover;
import uppu.engine.Movers;

import java.util.List;

public class Action {

    private final State target;
    private final Movers movers;

    private Action(State target, Movers movers) {
        this.target = target;
        this.movers = movers;
    }

    static Action create(State target, List<Mover> movers) {
        return new Action(target, Movers.create(movers));
    }

    public State target() {
        return target;
    }

    public Movers movers() {
        return movers;
    }
}
