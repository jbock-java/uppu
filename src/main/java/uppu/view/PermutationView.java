package uppu.view;

import uppu.model.Quadruple;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.KeyStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferStrategy;

public class PermutationView extends JFrame {

    private static final int BALL_SIZE = 40;

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
        view.pack();
        view.setVisible(true);
        view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        view.getRootPane().registerKeyboardAction(
                e -> view.dispatchEvent(new WindowEvent(view, WindowEvent.WINDOW_CLOSING)),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_IN_FOCUSED_WINDOW);
        view.canvas.createBufferStrategy(3);
        return view;
    }

    public void show(Quadruple quadruple, float x, float y) {
        BufferStrategy bufferStrategy = canvas.getBufferStrategy();
        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
        g.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        g.setPaint(Color.RED);
        g.fill(new Ellipse2D.Float(quadruple.getRx() + x, quadruple.getRy() + y, BALL_SIZE, BALL_SIZE));
        g.setPaint(Color.GREEN);
        g.fill(new Ellipse2D.Float(quadruple.getGx() + x, quadruple.getGy() + y, BALL_SIZE, BALL_SIZE));
        g.setPaint(Color.BLUE);
        g.fill(new Ellipse2D.Float(quadruple.getBx() + x, quadruple.getBy() + y, BALL_SIZE, BALL_SIZE));
        g.setPaint(Color.YELLOW);
        g.fill(new Ellipse2D.Float(quadruple.getYx() + x, quadruple.getYy() + y, BALL_SIZE, BALL_SIZE));
        bufferStrategy.show();
        g.dispose();
        Toolkit.getDefaultToolkit().sync();
    }

    private void createElements() {
        canvas.setSize(400, 400);
        canvas.setVisible(true);
        canvas.setFocusable(false);
        canvas.setBackground(Color.DARK_GRAY);
        getContentPane().add(canvas);
    }
}
