package uppu.view;

import org.apache.commons.math3.complex.RootsOfUnity;
import org.junit.jupiter.api.Test;

// https://math.stackexchange.com/questions/1990504/how-to-find-the-coordinates-of-the-vertices-of-a-pentagon-centered-at-the-origin
class PentagonTest {

    @Test
    void quint() {
        RootsOfUnity rou = new RootsOfUnity();
        rou.computeRoots(5);
        int off = 100;
        for (int i = 0; i < 5; i++) {
            double x = (rou.getImaginary(i) * off) + off;
            double y = (rou.getReal(i) * off) + off;
            System.out.println(x + "f, " + (2 * off - y) + "f");
        }
    }

    @Test
    void tetra() {
        RootsOfUnity rou = new RootsOfUnity();
        rou.computeRoots(3);
        int off = 104;
        for (int i = 0; i < 3; i++) {
            double x = (rou.getImaginary(i) * off) + off;
            double y = (rou.getReal(i) * off) + off;
            System.out.println(x + "f, " + (2 * off - y) + "f");
        }
    }
}
