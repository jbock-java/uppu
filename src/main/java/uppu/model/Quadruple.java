package uppu.model;

public final class Quadruple {

    private final int offsetX;
    private final int offsetY;
    private final float[] state;

    public Quadruple(int offsetX, int offsetY, float[] state) {
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.state = state;
    }

    public static Quadruple create() {
        Quadruple result = new Quadruple(0, 0, new float[8]);
        result.setR(Slot.SLOT_0.getX(), Slot.SLOT_0.getY());
        result.setG(Slot.SLOT_1.getX(), Slot.SLOT_1.getY());
        result.setB(Slot.SLOT_2.getX(), Slot.SLOT_2.getY());
        result.setY(Slot.SLOT_3.getX(), Slot.SLOT_3.getY());
        return result;
    }

    public Quadruple offset(int x, int y) {
        return new Quadruple(x, y, state);
    }

    public void set(Color c, float x, float y) {
        switch (c) {
            case RED -> setR(x, y);
            case BLUE -> setB(x, y);
            case GREEN -> setG(x, y);
            case YELLOW -> setY(x, y);
        }
    }

    public void setX(Color c, float x) {
        switch (c) {
            case RED -> setRx(x);
            case BLUE -> setBx(x);
            case GREEN -> setGx(x);
            case YELLOW -> setYx(x);
        }
    }

    public void setY(Color c, float y) {
        switch (c) {
            case RED -> setRy(y);
            case BLUE -> setBy(y);
            case GREEN -> setGy(y);
            case YELLOW -> setYy(y);
        }
    }

    public float getX(Color c) {
        return switch (c) {
            case RED -> state[0];
            case BLUE -> getBx();
            case GREEN -> state[2];
            case YELLOW -> getYx();
        };
    }

    public float getY(Color c) {
        return switch (c) {
            case RED -> state[1];
            case BLUE -> getBy();
            case GREEN -> state[3];
            case YELLOW -> getYy();
        };
    }

    public void setR(float x, float y) {
        state[0] = x;
        state[1] = y;
    }

    private void setRx(float x) {
        state[0] = x;
    }

    private void setRy(float y) {
        state[1] = y;
    }

    void setGx(float x) {
        state[2] = x;
    }

    void setGy(float y) {
        state[3] = y;
    }

    private void setBx(float x) {
        state[4] = x;
    }

    private void setBy(float y) {
        state[5] = y;
    }

    private void setYx(float x) {
        state[6] = x;
    }

    private void setYy(float y) {
        state[7] = y;
    }

    private void setG(float x, float y) {
        state[2] = x;
        state[3] = y;
    }

    private void setB(float x, float y) {
        state[4] = x;
        state[5] = y;
    }

    private void setY(float x, float y) {
        state[6] = x;
        state[7] = y;
    }

    private float getBx() {
        return state[4];
    }

    private float getBy() {
        return state[5];
    }

    private float getYx() {
        return state[6];
    }

    private float getYy() {
        return state[7];
    }

    public int getWidth() {
        return 150;
    }

    public int getHeight() {
        return 150;
    }

    public int getOffsetX() {
        return offsetX;
    }

    public int getOffsetY() {
        return offsetY;
    }
}
