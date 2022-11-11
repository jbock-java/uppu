package uppu.model;

public final class Quadruple {

    private final float[] state = new float[8];

    public static Quadruple create() {
        Quadruple result = new Quadruple();
        result.setR(Slot.SLOT_0.getX(), Slot.SLOT_0.getY());
        result.setG(Slot.SLOT_1.getX(), Slot.SLOT_1.getY());
        result.setB(Slot.SLOT_2.getX(), Slot.SLOT_2.getY());
        result.setY(Slot.SLOT_3.getX(), Slot.SLOT_3.getY());
        return result;
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
            case RED -> getRx();
            case BLUE -> getBx();
            case GREEN -> getGx();
            case YELLOW -> getYx();
        };
    }

    public float getY(Color c) {
        return switch (c) {
            case RED -> getRy();
            case BLUE -> getBy();
            case GREEN -> getGy();
            case YELLOW -> getYy();
        };
    }

    public void setR(float x, float y) {
        state[0] = x;
        state[1] = y;
    }

    public void setRx(float x) {
        state[0] = x;
    }

    public void setRy(float y) {
        state[1] = y;
    }

    public void setGx(float x) {
        state[2] = x;
    }

    public void setGy(float y) {
        state[3] = y;
    }

    public void setBx(float x) {
        state[4] = x;
    }

    public void setBy(float y) {
        state[5] = y;
    }

    public void setYx(float x) {
        state[6] = x;
    }

    public void setYy(float y) {
        state[7] = y;
    }

    public void setG(float x, float y) {
        state[2] = x;
        state[3] = y;
    }

    public void setB(float x, float y) {
        state[4] = x;
        state[5] = y;
    }

    public void setY(float x, float y) {
        state[6] = x;
        state[7] = y;
    }

    public float getRx() {
        return state[0];
    }

    public float getRy() {
        return state[1];
    }

    public float getGx() {
        return state[2];
    }

    public float getGy() {
        return state[3];
    }

    public float getBx() {
        return state[4];
    }

    public float getBy() {
        return state[5];
    }

    public float getYx() {
        return state[6];
    }

    public float getYy() {
        return state[7];
    }
}
