package uppu.model;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

public abstract class Action {

    private static final int FONT_SIZE = 36;
    private static final Font MONOSPACED = new Font("Monospaced", Font.BOLD, FONT_SIZE);

    public static final int BALL_SIZE = 40;
    private static final Color WILD_WATERMELON = new Color(252, 108, 133).brighter();
    private static final Color PANTONE_GREEN = new Color(152, 251, 152);
    private static final Color NCS_YELLOW = new Color(255, 211, 0);
    private static final Color ROBIN_EGG_BLUE = new Color(0, 204, 204).brighter();
    private static final Color ASH_GRAY = new Color(178, 190, 181).brighter();
    
    public static final Color CARMINE = new Color(150, 0, 24);
    private final Ellipse2D.Float ellipse = new Ellipse2D.Float(0, 0, BALL_SIZE, BALL_SIZE);

    public abstract boolean move();

    public abstract void show(Graphics2D g);

    final void show(Graphics2D g, Quadruple quadruple) {
        g.clearRect(quadruple.getOffsetX(), quadruple.getOffsetY(), quadruple.getWidth(), quadruple.getHeight());
        g.setPaint(WILD_WATERMELON);
        ellipse.x = quadruple.getX(uppu.model.Color.RED) + quadruple.getOffsetX();
        ellipse.y = quadruple.getY(uppu.model.Color.RED) + quadruple.getOffsetY();
        g.fill(ellipse);
        g.setPaint(PANTONE_GREEN);
        ellipse.x = quadruple.getX(uppu.model.Color.GREEN) + quadruple.getOffsetX();
        ellipse.y = quadruple.getY(uppu.model.Color.GREEN) + quadruple.getOffsetY();
        g.fill(ellipse);
        g.setPaint(ROBIN_EGG_BLUE);
        ellipse.x = quadruple.getX(uppu.model.Color.BLUE) + quadruple.getOffsetX();
        ellipse.y = quadruple.getY(uppu.model.Color.BLUE) + quadruple.getOffsetY();
        g.fill(ellipse);
        g.setPaint(NCS_YELLOW);
        ellipse.x = quadruple.getX(uppu.model.Color.YELLOW) + quadruple.getOffsetX();
        ellipse.y = quadruple.getY(uppu.model.Color.YELLOW) + quadruple.getOffsetY();
        g.fill(ellipse);
        g.setPaint(ASH_GRAY);
        ellipse.x = quadruple.getX(uppu.model.Color.WHITE) + quadruple.getOffsetX();
        ellipse.y = quadruple.getY(uppu.model.Color.WHITE) + quadruple.getOffsetY();
        g.fill(ellipse);
    }
}
