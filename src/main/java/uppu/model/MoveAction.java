package uppu.model;

import uppu.engine.Mover;
import uppu.engine.Movers;

import java.awt.Graphics2D;
import java.util.List;

public class MoveAction extends Action {

    private final Quadruple quadruple;
    private final Movers movers;

    private MoveAction(String title, Quadruple quadruple, Movers movers) {
        super(title);
        this.quadruple = quadruple;
        this.movers = movers;
    }

    static MoveAction create(String title, Quadruple quadruple, List<Mover> movers) {
        return new MoveAction(title, quadruple, Movers.create(movers));
    }

    @Override
    public boolean move() {
        return movers.move();
    }

    @Override
    public void show(Graphics2D g) {
        show(g, quadruple);
    }

    @Override
    String type() {
        return "MOVE";
    }

    @Override
    public void init() {
        movers.init();
    }
}
