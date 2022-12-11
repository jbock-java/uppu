package uppu.model;

import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

public abstract class Action {

    public static final int BALL_SIZE = (int) (50 * Slot.SCALE);
    public static final int GLOW_SIZE = 3;

    private static final OffsetEllipse LARGE_ELLIPSE = new OffsetEllipse(
            new Ellipse2D.Float(0, 0, BALL_SIZE, BALL_SIZE),
            new Ellipse2D.Float(0, 0, BALL_SIZE + 2 * GLOW_SIZE, BALL_SIZE + 2 * GLOW_SIZE),
            0);

    private static final OffsetEllipse SMALL_ELLIPSE = new OffsetEllipse(
            new Ellipse2D.Float(0, 0, BALL_SIZE / 1.5f, BALL_SIZE / 1.5f),
            new Ellipse2D.Float(0, 0, (BALL_SIZE / 1.5f) + 2 * GLOW_SIZE, (BALL_SIZE / 1.5f) + 2 * GLOW_SIZE),
            BALL_SIZE / 6.0f);

    public abstract boolean move();

    public abstract void show(Graphics2D g);

    abstract String type();

    final void show(Graphics2D g, State state) {
        Quadruple quadruple = state.quadruple();
        quadruple.clearRect(g);
        uppu.model.Color[] colors = quadruple.colors();
        for (uppu.model.Color color : colors) {
            OffsetEllipse offsetEllipse = ellipse(quadruple.getZ(color));
            Ellipse2D.Float ellipse = offsetEllipse.ellipse;
            Ellipse2D.Float glow = offsetEllipse.glow;
            ellipse.x = quadruple.getX(color) + quadruple.getOffsetX() + offsetEllipse.offset;
            ellipse.y = quadruple.getY(color) + quadruple.getOffsetY() + offsetEllipse.offset;
            glow.x = ellipse.x - GLOW_SIZE;
            glow.y = ellipse.y - GLOW_SIZE;
            g.setPaint(color.glowColor());
            g.fill(glow);
            g.setPaint(color.awtColor());
            g.fill(ellipse);
        }
        for (int i = 0; i < colors.length; i++) {
            g.setPaint(colors[i].awtColor());
            Point slot = state.slot().forIndex(i);
            slot.paintHome(g, quadruple);
        }
    }

    record OffsetEllipse(
            Ellipse2D.Float ellipse,
            Ellipse2D.Float glow,
            float offset) {
    }

    private static OffsetEllipse ellipse(float z) {
        if (z < 1.3f) {
            return LARGE_ELLIPSE;
        }
        return SMALL_ELLIPSE;
    }

    public abstract void init();

    @Override
    public final String toString() {
        return type();
    }
}
