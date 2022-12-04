package uppu.engine;

import uppu.model.Color;
import uppu.model.Quadruple;
import uppu.model.Slot;

public class Mover {

    private final Color color;
    private final Quadruple quadruple;
    private final Slot.AbstractSlot source;
    private final Slot.AbstractSlot target;
    private final float dx;
    private final float dy;
    
    private boolean started;

    private Mover(
            Color color,
            Quadruple quadruple,
            Slot.AbstractSlot source, 
            Slot.AbstractSlot target, 
            float dx,
            float dy) {
        this.color = color;
        this.quadruple = quadruple;
        this.source = source;
        this.target = target;
        this.dx = dx;
        this.dy = dy;
    }

    public static Mover create(
            Color color, 
            Quadruple quadruple, 
            Slot.AbstractSlot sourceSlot,
            Slot.AbstractSlot targetSlot) {
        float start_x = sourceSlot.getX();
        float start_y = sourceSlot.getY();
        float target_x = targetSlot.getX();
        float target_y = targetSlot.getY();
        double delta_x = target_x - start_x;
        double delta_y = target_y - start_y;
        double dx = delta_x / 66;
        double dy = delta_y / 66;
        return new Mover(color, quadruple, sourceSlot, targetSlot, (float) dx, (float) dy);
    }

    public boolean move() {
        if (!started) {
            setX(source.getX());
            setY(source.getY());
            started = true;
            return true;
        }
        float x = getX();
        float y = getY();
        double dist1 = dist(x, y, target);
        float x2 = x + dx;
        float y2 = y + dy;
        double dist2 = dist(x2, y2, target);
        if (dist2 >= dist1) {
            return false;
        }
        setX(x2);
        setY(y2);
        return true;
    }

    static double dist(float x1, float y1, Slot.AbstractSlot slot) {
        double delta_x = x1 - slot.getX();
        double delta_y = y1 - slot.getY();
        return Math.sqrt(delta_x * delta_x + delta_y * delta_y);
    }

    private float getX() {
        return quadruple.getX(color);
    }

    private float getY() {
        return quadruple.getY(color);
    }

    private void setX(float x) {
        quadruple.setX(color, x);
    }

    private void setY(float y) {
        quadruple.setY(color, y);
    }

    public void init() {
        setX(source.getX());
        setY(source.getY());
        started = false;
    }
}
