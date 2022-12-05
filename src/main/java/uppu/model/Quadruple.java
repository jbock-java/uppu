package uppu.model;

import java.util.Arrays;

public final class Quadruple {

    private final int offsetX;
    private final int offsetY;
    private final float[] state;
    private final Color[] colors;

    private Quadruple(
            int offsetX,
            int offsetY,
            float[] state,
            Color[] colors) {
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.state = state;
        this.colors = colors;
    }

    public static Quadruple create(int n) {
        Color[] colors = Arrays.stream(Color.values()).limit(n).toArray(Color[]::new);
        return new Quadruple(0, 0, new float[2 * n], colors);
    }

    public Quadruple offset(int x, int y) {
        return new Quadruple(x, y, state, colors);
    }

    public void set(Color c, float x, float y) {
        switch (c) {
            case RED -> {
                state[0] = x;
                state[1] = y;
            }
            case GREEN -> {
                state[2] = x;
                state[3] = y;
            }
            case BLUE -> {
                state[4] = x;
                state[5] = y;
            }
            case YELLOW -> {
                state[6] = x;
                state[7] = y;
            }
            case WHITE -> {
                state[8] = x;
                state[9] = y;
            }
        }
    }

    public void setX(Color c, float x) {
        switch (c) {
            case RED -> state[0] = x;
            case GREEN -> state[2] = x;
            case BLUE -> state[4] = x;
            case YELLOW -> state[6] = x;
            case WHITE -> state[8] = x;
        }
    }

    public void setY(Color c, float y) {
        switch (c) {
            case RED -> state[1] = y;
            case GREEN -> state[3] = y;
            case BLUE -> state[5] = y;
            case YELLOW -> state[7] = y;
            case WHITE -> state[9] = y;
        }
    }

    public float getX(Color c) {
        return switch (c) {
            case RED -> state[0];
            case GREEN -> state[2];
            case BLUE -> state[4];
            case YELLOW -> state[6];
            case WHITE -> state[8];
        };
    }

    public float getY(Color c) {
        return switch (c) {
            case RED -> state[1];
            case GREEN -> state[3];
            case BLUE -> state[5];
            case YELLOW -> state[7];
            case WHITE -> state[9];
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

}
