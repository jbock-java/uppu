package uppu.model;

import uppu.engine.Mover;
import uppu.engine.Movers;

import java.util.List;

public class Action {

    private final State finalState;
    private final Movers movers;

    private Action(State finalState, Movers movers) {
        this.finalState = finalState;
        this.movers = movers;
    }

    static Action create(State finalState, List<Mover> movers) {
        return new Action(finalState, Movers.create(movers));
    }

    public boolean move() {
        return movers.move();
    }

    public State finalState() {
        return finalState;
    }
}
