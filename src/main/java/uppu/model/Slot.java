package uppu.model;

public enum Slot {

    SLOT_0(0, 0),
    SLOT_1(150, 0),
    SLOT_2(150, 150),
    SLOT_3(0, 150);

    public static final int MAX = 150;

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
