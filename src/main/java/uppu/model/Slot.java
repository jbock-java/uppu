package uppu.model;

import java.util.List;

public final class Slot {

    public static final float SCALE = 2.2f;
    public static final int MAX = (int) (200 * SCALE);

    private final List<Point> slots;

    private Slot(List<? extends Point> slots) {
        this.slots = List.copyOf(slots);
    }

    Point forIndex(int i) {
        return slots.get(i);
    }

    public record Point(float x, float y) {
        Point scale() {
            return new Point(x * SCALE, y * SCALE);
        };
    }

    static Slot slots(int n) {
        return new Slot(getSlots(n).stream().map(Point::scale).toList());
    }

    private static List<Point> getSlots(int n) {
        switch (n) {
            case 4:
                return List.of(
                        new Point(0f, 0f),
                        new Point(126f, 0f),
                        new Point(0f, 126f),
                        new Point(126f, 126f));
            case 5:
                return List.of(
                        new Point(100.0f, 0.0f),
                        new Point(195.10565162951536f, 69.09830056250524f),
                        new Point(158.77852522924732f, 180.90169943749473f),
                        new Point(41.2214747707527f, 180.90169943749476f),
                        new Point(4.894348370484636f, 69.09830056250527f));
        }
        throw new IllegalArgumentException("" + n);
    }

    public int getNumSlots() {
        return slots.size();
    }
}
