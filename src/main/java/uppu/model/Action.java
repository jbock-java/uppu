package uppu.model;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

public abstract class Action {

    private static final int FONT_SIZE = 36;
    private static final Font MONOSPACED = new Font("Monospaced", Font.BOLD, FONT_SIZE);

    private static final int BALL_SIZE = 40;
    private static final Color WILD_WATERMELON = new Color(252, 108, 133).brighter();
    private static final Color PANTONE_GREEN = new Color(152, 251, 152);
    private static final Color NCS_YELLOW = new Color(255, 211, 0);
    private static final Color ROBIN_EGG_BLUE = new Color(0, 204, 204).brighter();
    private final Ellipse2D.Float ellipse = new Ellipse2D.Float(0, 0, BALL_SIZE, BALL_SIZE);

    public abstract boolean move();

    public abstract void show(Graphics2D g, Label label);

    final void showLabel(Graphics2D g, Label label) {
        g.clearRect(100, 260 - FONT_SIZE + 5, FONT_SIZE * 6, FONT_SIZE);
        if (!label.text().isEmpty()) {
            g.setFont(MONOSPACED);
            FontMetrics fm = g.getFontMetrics();
            int w = fm.stringWidth(label.text());
            int x = 220 - (w / 2);
            String text = label.text();
            for (int i = 0; i < text.length(); i++) {
                String charString = Character.toString(text.charAt(i));
                g.setColor(label.isHighlight(i) ? Color.WHITE : Color.RED);
                g.drawString(charString, x, 260);
                x += fm.stringWidth(charString);
            }
        }
    }

    final void show(Graphics2D g, Quadruple quadruple) {
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
