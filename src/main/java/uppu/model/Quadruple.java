package uppu.model;

public final class Quadruple {

    private final int offsetX;
    private final int offsetY;
    private final float[] state;

    private Quadruple(int offsetX, int offsetY, float[] state) {
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.state = state;
    }

    public static Quadruple create() {
        return new Quadruple(0, 0, new float[8]);
    }

    public Quadruple offset(int x, int y) {
        return new Quadruple(x, y, state);
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
        }
    }

    public void setX(Color c, float x) {
        switch (c) {
            case RED -> state[0] = x;
            case GREEN -> state[2] = x;
            case BLUE -> state[4] = x;
            case YELLOW -> state[6] = x;
        }
    }

    public void setY(Color c, float y) {
        switch (c) {
            case RED -> state[1] = y;
            case GREEN -> state[3] = y;
            case BLUE -> state[5] = y;
            case YELLOW -> state[7] = y;
        }
    }

    public float getX(Color c) {
        return switch (c) {
            case RED -> state[0];
            case GREEN -> state[2];
            case BLUE -> state[4];
            case YELLOW -> state[6];
        };
    }

    public float getY(Color c) {
        return switch (c) {
            case RED -> state[1];
            case GREEN -> state[3];
            case BLUE -> state[5];
            case YELLOW -> state[7];
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
}
