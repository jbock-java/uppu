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

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public Slot nextSlot() {
        return add(1);
    }

    public Slot add(int n) {
        return values()[(ordinal() + n) % 4];
    }
}
