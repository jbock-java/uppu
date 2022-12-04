package uppu.model;

import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

public abstract class Action {

    public static final int BALL_SIZE = (int) (50 * Slot.SCALE);
    private static final int HOME_SIZE = 12;

    private static final Ellipse2D.Float ellipse = new Ellipse2D.Float(0, 0, BALL_SIZE, BALL_SIZE);
    private static final Ellipse2D.Float home = new Ellipse2D.Float(0, 0, HOME_SIZE, 12);

    public abstract boolean move();

    public abstract void show(Graphics2D g);

    abstract String type();

    final void show(Graphics2D g, State state) {
        Quadruple quadruple = state.quadruple();
        g.clearRect(quadruple.getOffsetX(), quadruple.getOffsetY(), quadruple.getWidth(), quadruple.getHeight());
        uppu.model.Color[] colors = quadruple.colors();
        for (uppu.model.Color color : colors) {
            g.setPaint(color.awtColor());
            ellipse.x = quadruple.getX(color) + quadruple.getOffsetX();
            ellipse.y = quadruple.getY(color) + quadruple.getOffsetY();
            g.fill(ellipse);
        }
        for (int i = 0; i < colors.length; i++) {
            Color color = colors[i];
            g.setPaint(color.awtColor());
            Slot.Point slot = state.slot().forIndex(i);
            float centerX = slot.x() + quadruple.getOffsetX() + ((BALL_SIZE - HOME_SIZE) / 2f);
            float centerY = slot.y() + quadruple.getOffsetY() + ((BALL_SIZE - HOME_SIZE) / 2f);
            home.x = centerX - 10;
            home.y = centerY - 10;
            g.fill(home);
            home.x = centerX - 10;
            home.y = centerY + 10;
            g.fill(home);
            home.x = centerX + 10;
            home.y = centerY - 10;
            g.fill(home);
            home.x = centerX + 10;
            home.y = centerY + 10;
            g.fill(home);
        }
    }

    public abstract void init();

    @Override
    public final String toString() {
        return type();
    }
}
