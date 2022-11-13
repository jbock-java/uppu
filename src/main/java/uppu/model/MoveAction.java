package uppu.model;

import uppu.engine.Mover;
import uppu.engine.Movers;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.List;

public class MoveAction extends Action {

    private static final int BALL_SIZE = 40;
    private static final Color WILD_WATERMELON = new Color(252, 108, 133).brighter();
    private static final Color PANTONE_GREEN = new Color(152, 251, 152);
    private static final Color NCS_YELLOW = new Color(255, 211, 0);
    private static final Color ROBIN_EGG_BLUE = new Color(0, 204, 204).brighter();

    private final State finalState;
    private final Movers movers;

    private final Ellipse2D.Float ellipse = new Ellipse2D.Float(0, 0, BALL_SIZE, BALL_SIZE);

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

    private void show(Graphics2D g, Quadruple quadruple) {
        g.clearRect(quadruple.getOffsetX(), quadruple.getOffsetY(), quadruple.getWidth(), quadruple.getHeight());
        g.setPaint(WILD_WATERMELON);
        ellipse.x = quadruple.getRx() + quadruple.getOffsetX();
        ellipse.y = quadruple.getRy() + quadruple.getOffsetY();
        g.fill(ellipse);
        g.setPaint(PANTONE_GREEN);
        ellipse.x = quadruple.getGx() + quadruple.getOffsetX();
        ellipse.y = quadruple.getGy() + quadruple.getOffsetY();
        g.fill(ellipse);
        g.setPaint(ROBIN_EGG_BLUE);
        ellipse.x = quadruple.getBx() + quadruple.getOffsetX();
        ellipse.y = quadruple.getBy() + quadruple.getOffsetY();
        g.fill(ellipse);
        g.setPaint(NCS_YELLOW);
        ellipse.x = quadruple.getYx() + quadruple.getOffsetX();
        ellipse.y = quadruple.getYy() + quadruple.getOffsetY();
        g.fill(ellipse);
    }
}
