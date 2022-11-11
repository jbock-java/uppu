package uppu.model;

public final class Quadruple {

    private final float[] state = new float[8];

    public static Quadruple create() {
        Quadruple result = new Quadruple();
        result.setR(0, 0);
        result.setG(0, 100);
        result.setB(100, 0);
        result.setY(100, 100);
        return result;
    }

    public void setR(float x, float y) {
        state[0] = x;
        state[1] = y;
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
