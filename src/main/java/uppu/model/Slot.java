package uppu.model;

public enum Slot {

    SLOT_0(100.0f, 0.0f),
    SLOT_1(195.10565162951536f, 69.09830056250524f),
    SLOT_2(158.77852522924732f, 180.90169943749473f),
    SLOT_3(41.2214747707527f, 180.90169943749476f),
    SLOT_4(4.894348370484636f, 69.09830056250527f);

    public static final int MAX = 200;

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
