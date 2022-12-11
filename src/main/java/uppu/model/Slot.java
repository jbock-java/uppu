package uppu.model;

import java.util.List;

public final class Slot {

    public static final float SCALE = 2.0f;
    public static final int MAX = (int) (200 * SCALE);

    private final List<Point> slots;

    private Slot(List<? extends Point> slots) {
        this.slots = List.copyOf(slots);
    }

    Point forIndex(int i) {
        return slots.get(i);
    }

    static Slot slots(int n) {
        return new Slot(getSlots(n).stream().map(Point::scale).toList());
    }

    private static List<Point> getSlots(int n) {
        switch (n) {
            case 4:
                return List.of(
                        new Point(90.06664f, 0.0f, 1.0f),
                        new Point(180.13327f, 156.0f, 1.0f),
                        new Point(0f, 156.0f, 1.0f),
                        new Point(90.06664f, 104.0f, 1.5f));
            case 5:
                return List.of(
                        new Point(100.0f, 0.0f, 0.0f),
                        new Point(195.10565162951536f, 69.09830056250524f, 0.0f),
                        new Point(158.77852522924732f, 180.90169943749473f, 0.0f),
                        new Point(41.2214747707527f, 180.90169943749476f, 0.0f),
                        new Point(4.894348370484636f, 69.09830056250527f, 0.0f));
        }
        throw new IllegalArgumentException("" + n);
    }

    public int getNumSlots() {
        return slots.size();
    }

    public static void main(String[] args) {
        for (Point p : getSlots(4)) {
            System.out.println(p.x() - 13.933358006418416f);
        }
    }
}
