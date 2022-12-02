package uppu.model;

import java.util.List;

public final class Slot {

    public static final float SCALE = 1.8f;
    public static final int MAX = (int) (200 * SCALE);

    private final List<AbstractSlot> slots;

    private Slot(List<? extends AbstractSlot> slots) {
        this.slots = List.copyOf(slots);
    }

    AbstractSlot forIndex(int i) {
        return slots.get(i);
    }

    public interface AbstractSlot {
        float getX();

        float getY();
    }

    enum Slot5 implements AbstractSlot {

        SLOT_0(100.0f, 0.0f),
        SLOT_1(195.10565162951536f, 69.09830056250524f),
        SLOT_2(158.77852522924732f, 180.90169943749473f),
        SLOT_3(41.2214747707527f, 180.90169943749476f),
        SLOT_4(4.894348370484636f, 69.09830056250527f);

        private final float x;
        private final float y;

        Slot5(float x, float y) {
            this.x = x * SCALE;
            this.y = y * SCALE;
        }

        static List<AbstractSlot> getValues() {
            return List.of(values());
        }

        @Override
        public float getX() {
            return x;
        }

        @Override
        public float getY() {
            return y;
        }
    }

    enum Slot4 implements AbstractSlot {

        SLOT_0(0f, 0f),
        SLOT_1(126f, 0f),
        SLOT_2(0f, 126f),
        SLOT_3(126f, 126f);

        private final float x;
        private final float y;

        Slot4(float x, float y) {
            this.x = x * SCALE;
            this.y = y * SCALE;
        }

        static List<AbstractSlot> getValues() {
            return List.of(values());
        }

        @Override
        public float getX() {
            return x;
        }

        @Override
        public float getY() {
            return y;
        }
    }

    static Slot slots(int n) {
        return new Slot(abstractSlots(n));
    }

    private static List<AbstractSlot> abstractSlots(int n) {
        switch (n) {
            case 4:
                return Slot4.getValues();
            case 5:
                return Slot5.getValues();
        }
        throw new IllegalArgumentException("" + n);
    }
}
