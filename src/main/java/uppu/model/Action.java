package uppu.model;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

public abstract class Action {

    private static final int FONT_SIZE = 36;
    private static final Font MONOSPACED = new Font("Monospaced", Font.BOLD, FONT_SIZE);

    public abstract boolean move();

    public abstract State finalState();

    public abstract void show(Graphics2D g, String label);

    void showLabel(Graphics2D g, String label) {
        g.clearRect(100, 260 - FONT_SIZE + 5, FONT_SIZE * 6, FONT_SIZE);
        if (!label.isEmpty()) {
            g.setFont(MONOSPACED);
            g.setColor(Color.RED);
            FontMetrics fm = g.getFontMetrics();
            int w = fm.stringWidth(label);
            g.drawString(label, 220 - (w / 2), 260);
        }
    }
}
