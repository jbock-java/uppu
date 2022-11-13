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

    public abstract void show(Graphics2D g, Label label);

    void showLabel(Graphics2D g, Label label) {
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
}
