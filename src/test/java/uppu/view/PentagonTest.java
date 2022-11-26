package uppu.view;

import org.apache.commons.math3.complex.RootsOfUnity;
import org.junit.jupiter.api.Test;

// https://math.stackexchange.com/questions/1990504/how-to-find-the-coordinates-of-the-vertices-of-a-pentagon-centered-at-the-origin
class PentagonTest {

    @Test
    void constructPoints() {
        RootsOfUnity rou = new RootsOfUnity();
        rou.computeRoots(5);
        int off = 100;
        for (int i = 0; i < 5; i++) {
            double x = (rou.getImaginary(i) * 100) + off;
            double y = (rou.getReal(i) * 100) + off;
            System.out.println(x + "f, " + (200 - y) + "f");
        }
    }
}
