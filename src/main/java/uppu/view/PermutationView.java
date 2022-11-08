package uppu.view;

import io.parmigiano.Permutation;

import javax.swing.JFrame;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;

public class PermutationView extends JFrame {

    private final Canvas canvas = new Canvas() {
        @Override
        public void paint(Graphics g) {
        }
    };

    private PermutationView() {
        super("uppu");
    }

    public static PermutationView create() {
        PermutationView view = new PermutationView();
        view.setSize(400, 400);
        view.createElements();
        return view;
    }

    public void showPermutation(Permutation permutation) {
        Graphics g = canvas.getGraphics();
        g.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        g.setColor(Color.RED);
        g.drawString(permutation.toString(), 200, 200);
    }

    private void createElements() {
        canvas.setSize(400, 400);
        canvas.setVisible(true);
        getContentPane().add(canvas);
    }
}
