package uppu.model;

import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

public abstract class Action {

    public static final int BALL_SIZE = (int) (50 * Slot.SCALE);

    private static final Ellipse2D.Float ellipse = new Ellipse2D.Float(0, 0, BALL_SIZE, BALL_SIZE);

    private final String title;

    Action(String title) {
        this.title = title;
    }

    public abstract boolean move();

    public abstract void show(Graphics2D g);

    abstract String type();

    final void show(Graphics2D g, Quadruple quadruple) {
        g.clearRect(quadruple.getOffsetX(), quadruple.getOffsetY(), quadruple.getWidth(), quadruple.getHeight());
        uppu.model.Color[] colors = quadruple.colors();
        for (uppu.model.Color color : colors) {
            g.setPaint(color.awtColor());
            ellipse.x = quadruple.getX(color) + quadruple.getOffsetX();
            ellipse.y = quadruple.getY(color) + quadruple.getOffsetY();
            g.fill(ellipse);
        }
    }

    public abstract void init();

    public final String title() {
        return title;
    }

    @Override
    public final String toString() {
        return type() + ' ' + title();
    }
}
