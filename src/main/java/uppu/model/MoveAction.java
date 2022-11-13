package uppu.model;

import uppu.engine.Mover;
import uppu.engine.Movers;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.List;

public class MoveAction extends Action {

    private final State finalState;
    private final Movers movers;


    private MoveAction(State finalState, Movers movers) {
        this.finalState = finalState;
        this.movers = movers;
    }

    static MoveAction create(State finalState, List<Mover> movers) {
        return new MoveAction(finalState, Movers.create(movers));
    }

    @Override
    public boolean move() {
        return movers.move();
    }

    @Override
    public State finalState() {
        return finalState;
    }

    @Override
    public void show(Graphics2D g, Label label) {
        show(g, finalState.quadruple());
        showLabel(g, label);
    }
}
