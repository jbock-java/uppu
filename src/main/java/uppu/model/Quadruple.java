package uppu.model;

import java.awt.Graphics2D;
import java.util.Arrays;

public final class Quadruple {

    private static final int X0 = 0;
    private static final int Y0 = 1;
    private static final int X1 = 3;
    private static final int Y1 = 4;
    private static final int X2 = 6;
    private static final int Y2 = 7;
    private static final int X3 = 9;
    private static final int Y3 = 10;
    private static final int X4 = 12;
    private static final int Y4 = 13;
    private final int offsetX;
    private final int offsetY;
    private final float[] state;
    private final Color[] colors;

    private final int clear_offsetX;
    private final int clear_offsetY;
    private final int clear_width;
    private final int clear_height;

    private Quadruple(
            int offsetX,
            int offsetY,
            float[] state,
            Color[] colors) {
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.state = state;
        this.colors = colors;
         clear_offsetX = offsetX - Action.GLOW_SIZE;
         clear_offsetY = offsetY - Action.GLOW_SIZE;
         clear_width = getWidth() + 2 * Action.GLOW_SIZE;
         clear_height = getHeight() + 2 * Action.GLOW_SIZE;
    }

    public static Quadruple create(int n) {
        Color[] colors = Arrays.stream(Color.values()).limit(n).toArray(Color[]::new);
        return new Quadruple(0, 0, new float[3 * n], colors);
    }

    public Quadruple offset(int x, int y) {
        return new Quadruple(x, y, state, colors);
    }

    public void set(Color c, float x, float y) {
        switch (c) {
            case RED -> {
                state[X0] = x;
                state[Y0] = y;
            }
            case GREEN -> {
                state[X1] = x;
                state[Y1] = y;
            }
            case BLUE -> {
                state[X2] = x;
                state[Y2] = y;
            }
            case YELLOW -> {
                state[X3] = x;
                state[Y3] = y;
            }
            case WHITE -> {
                state[X4] = x;
                state[Y4] = y;
            }
        }
    }

    public void setX(Color c, float x) {
        switch (c) {
            case RED -> state[X0] = x;
            case GREEN -> state[X1] = x;
            case BLUE -> state[X2] = x;
            case YELLOW -> state[X3] = x;
            case WHITE -> state[X4] = x;
        }
    }

    public void setY(Color c, float y) {
        switch (c) {
            case RED -> state[Y0] = y;
            case GREEN -> state[Y1] = y;
            case BLUE -> state[Y2] = y;
            case YELLOW -> state[Y3] = y;
            case WHITE -> state[Y4] = y;
        }
    }

    public float getX(Color c) {
        return switch (c) {
            case RED -> state[X0];
            case GREEN -> state[X1];
            case BLUE -> state[X2];
            case YELLOW -> state[X3];
            case WHITE -> state[X4];
        };
    }

    public float getY(Color c) {
        return switch (c) {
            case RED -> state[Y0];
            case GREEN -> state[Y1];
            case BLUE -> state[Y2];
            case YELLOW -> state[Y3];
            case WHITE -> state[Y4];
        };
    }

    public int getWidth() {
        return Slot.MAX + Action.BALL_SIZE;
    }

    public int getHeight() {
        return Slot.MAX + Action.BALL_SIZE;
    }

    public int getOffsetX() {
        return offsetX;
    }

    public int getOffsetY() {
        return offsetY;
    }

    public Color[] colors() {
        return colors;
    }

    public void clearRect(Graphics2D g) {
        g.clearRect(clear_offsetX, clear_offsetY, clear_width, clear_height);
    }
}
