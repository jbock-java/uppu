package uppu.model;

public enum Slot {

    SLOT_0(0, 0),
    SLOT_1(100, 0),
    SLOT_2(100, 100),
    SLOT_3(0, 100);

    private final float x;
    private final float y;

    Slot(float x, float y) {
        this.x = x;
        this.y = y;
    }

    static Slot forIndex(int i) {
        return values()[i];
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
