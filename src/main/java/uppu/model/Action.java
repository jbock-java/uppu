package uppu.model;

import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

public abstract class Action {

    public static final int BALL_SIZE = (int) (50 * Slot.SCALE);
    public static final int GLOW_SIZE = 3;

    private static final OffsetEllipse[] ELLIPSES = new OffsetEllipse[120];
    
    static {
        float v = (BALL_SIZE - BALL_SIZE / 1.5f) / 60f;
        float vglow = BALL_SIZE / 360.0f;
        for (int i = 0; i < ELLIPSES.length; i++) {
            float ballSize = BALL_SIZE - (i * v);
            ELLIPSES[i] = new OffsetEllipse(
                    new Ellipse2D.Float(0, 0, ballSize, ballSize),
                    new Ellipse2D.Float(0, 0, ballSize + 2 * GLOW_SIZE, ballSize + 2 * GLOW_SIZE),
                    vglow * i);
        }
    }
    
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

    static OffsetEllipse ellipse(float z) {
        int bucket = Math.round((z - 1f) * 120);
        if (bucket >= 60) {
            bucket = 59;
        }
        return ELLIPSES[bucket];
    }

    public abstract void init();

    @Override
    public final String toString() {
        return type();
    }
}
