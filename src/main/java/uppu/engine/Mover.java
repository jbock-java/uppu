package uppu.engine;

import uppu.model.Color;
import uppu.model.Quadruple;
import uppu.model.Slot;

public class Mover {

    private static final float stepSize = 1f;
    
    private final Color color;
    private final Quadruple quadruple;
    private final float target_x; // target
    private final float target_y; // target

    public Mover(Color color, Quadruple quadruple, Slot slot) {
        this.color = color;
        this.quadruple = quadruple;
        this.target_x = slot.getX();
        this.target_y = slot.getY();
    }

    public boolean move() {
        float x = getX();
        boolean moved = false;
        if (Math.abs(target_x - x) >= stepSize) {
            float newX = x > target_x ? x - stepSize : x + stepSize;
            setX(newX);
            moved = true;
        }
        float y = getY();
        if (Math.abs(target_y - y) >= stepSize) {
            float newY = y > target_y ? y - stepSize : y + stepSize;
            setY(newY);
            moved = true;
        }
        return moved;
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
}