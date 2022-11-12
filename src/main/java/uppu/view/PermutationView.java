package uppu.view;

import uppu.model.Quadruple;
import uppu.model.State;

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
import java.util.List;

public class PermutationView extends JFrame {

    private static final int BALL_SIZE = 40;
    private static final int WIDTH = 600;
    private static final int HEIGHT = 400;

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
        view.setSize(WIDTH, HEIGHT);
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
    public void show(List<State> states) {
        BufferStrategy bufferStrategy = canvas.getBufferStrategy();
        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
        for (State state : states) {
            show(g, state.quadruple());
        }
        bufferStrategy.show();
        g.dispose();
        Toolkit.getDefaultToolkit().sync();
    }

    public void show(Graphics2D g, Quadruple quadruple) {
        g.clearRect(quadruple.getOffsetX(), quadruple.getOffsetY(), quadruple.getWidth(), quadruple.getHeight());
        g.setPaint(Color.RED);
        g.fill(new Ellipse2D.Float(quadruple.getRx() + quadruple.getOffsetX(), quadruple.getRy() + quadruple.getOffsetY(), BALL_SIZE, BALL_SIZE));
        g.setPaint(Color.GREEN);
        g.fill(new Ellipse2D.Float(quadruple.getGx() + quadruple.getOffsetX(), quadruple.getGy() + quadruple.getOffsetY(), BALL_SIZE, BALL_SIZE));
        g.setPaint(Color.BLUE);
        g.fill(new Ellipse2D.Float(quadruple.getBx() + quadruple.getOffsetX(), quadruple.getBy() + quadruple.getOffsetY(), BALL_SIZE, BALL_SIZE));
        g.setPaint(Color.YELLOW);
        g.fill(new Ellipse2D.Float(quadruple.getYx() + quadruple.getOffsetX(), quadruple.getYy() + quadruple.getOffsetY(), BALL_SIZE, BALL_SIZE));
    }

    private void createElements() {
        canvas.setSize(WIDTH, HEIGHT);
        canvas.setVisible(true);
        canvas.setFocusable(false);
        canvas.setBackground(Color.DARK_GRAY);
        getContentPane().add(canvas);
    }
}
