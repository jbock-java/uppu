package uppu.model;

import org.junit.jupiter.api.Test;

class SlotTest {

    @Test
    void testSlot() {
        Slot slots = Slot.slots(4);
        Slot.Point center = slots.forIndex(3);
        Slot.Point p0 = slots.forIndex(0);
        Slot.Point p1 = slots.forIndex(1);
        Slot.Point p2 = slots.forIndex(2);
        System.out.println(dist(center, p0));
        System.out.println(dist(center, p1));
        System.out.println(dist(center, p2));
    }

    static double dist(Slot.Point center, Slot.Point slot) {
        double delta_x = center.x() - slot.x();
        double delta_y = center.y() - slot.y();
        return Math.sqrt(delta_x * delta_x + delta_y * delta_y);
    }
}