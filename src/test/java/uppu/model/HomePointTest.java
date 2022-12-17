package uppu.model;

import org.junit.jupiter.api.Test;

import java.util.List;

class HomePointTest {

    @Test
    void testSlot() {
        List<Point> slots = HomePoint.homePoints(4);
        Point center = slots.get(3);
        Point p0 = slots.get(0);
        Point p1 = slots.get(1);
        Point p2 = slots.get(2);
        System.out.println(dist(center, p0));
        System.out.println(dist(center, p1));
        System.out.println(dist(center, p2));
    }

    static double dist(Point center, Point slot) {
        double delta_x = center.x() - slot.x();
        double delta_y = center.y() - slot.y();
        return Math.sqrt(delta_x * delta_x + delta_y * delta_y);
    }
}