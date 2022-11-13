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
    private static final Color WILD_WATERMELON = new Color(252, 108, 133);
    private static final Color PANTONE_GREEN = new Color(152, 251, 152);
    private static final Color NCS_YELLOW = new Color(255, 211, 0);
    private static final Color ROBIN_EGG_BLUE = new Color(0, 204, 204);

    private final Canvas canvas = new Canvas() {
        @Override
        public void paint(Graphics g) {
        }
    };

    private final Ellipse2D.Float floatRed = new Ellipse2D.Float(0, 0, BALL_SIZE, BALL_SIZE);
    private final Ellipse2D.Float floatGreen = new Ellipse2D.Float(0, 0, BALL_SIZE, BALL_SIZE);
    private final Ellipse2D.Float floatBlue = new Ellipse2D.Float(0, 0, BALL_SIZE, BALL_SIZE);
    private final Ellipse2D.Float floatYellow = new Ellipse2D.Float(0, 0, BALL_SIZE, BALL_SIZE);

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
        g.setPaint(WILD_WATERMELON);
        floatRed.x = quadruple.getRx() + quadruple.getOffsetX();
        floatRed.y = quadruple.getRy() + quadruple.getOffsetY();
        g.fill(floatRed);
        g.setPaint(PANTONE_GREEN);
        floatGreen.x = quadruple.getGx() + quadruple.getOffsetX();
        floatGreen.y = quadruple.getGy() + quadruple.getOffsetY();
        g.fill(floatGreen);
        g.setPaint(ROBIN_EGG_BLUE);
        floatBlue.x = quadruple.getBx() + quadruple.getOffsetX();
        floatBlue.y = quadruple.getBy() + quadruple.getOffsetY();
        g.fill(floatBlue);
        g.setPaint(NCS_YELLOW);
        floatYellow.x = quadruple.getYx() + quadruple.getOffsetX();
        floatYellow.y = quadruple.getYy() + quadruple.getOffsetY();
        g.fill(floatYellow);
    }

    private void createElements() {
        canvas.setSize(WIDTH, HEIGHT);
        canvas.setVisible(true);
        canvas.setFocusable(false);
        canvas.setBackground(Color.DARK_GRAY);
        getContentPane().add(canvas);
    }
}
