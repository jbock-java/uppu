package uppu.model;

import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

public abstract class Action {

    public static final int BALL_SIZE = (int) (50 * Slot.SCALE);
    public static final int GLOW_SIZE = 3;

    private static final Ellipse2D.Float ellipse = new Ellipse2D.Float(0, 0, BALL_SIZE, BALL_SIZE);
    private static final Ellipse2D.Float glow = new Ellipse2D.Float(0, 0, BALL_SIZE + 2 * GLOW_SIZE, BALL_SIZE + 2 * GLOW_SIZE);

    public abstract boolean move();

    public abstract void show(Graphics2D g);

    abstract String type();

    final void show(Graphics2D g, State state) {
        Quadruple quadruple = state.quadruple();
        quadruple.clearRect(g);
        uppu.model.Color[] colors = quadruple.colors();
        for (uppu.model.Color color : colors) {
            ellipse.x = quadruple.getX(color) + quadruple.getOffsetX();
            ellipse.y = quadruple.getY(color) + quadruple.getOffsetY();
            glow.x = ellipse.x - GLOW_SIZE;
            glow.y = ellipse.y - GLOW_SIZE;
            g.setPaint(color.glowColor());
            g.fill(glow);
            g.setPaint(color.awtColor());
            g.fill(ellipse);
        }
        for (int i = 0; i < colors.length; i++) {
            Color color = colors[i];
            g.setPaint(color.awtColor());
            Point slot = state.slot().forIndex(i);
            slot.paintHome(g, quadruple);
        }
    }

    public abstract void init();

    @Override
    public final String toString() {
        return type();
    }
}
