package uppu.view;

import uppu.model.Slot;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.KeyStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;

public class PermutationView extends JFrame {

    private static final int WIDTH = (int) (300 * Slot.SCALE);
    private static final int HEIGHT = (int) (300 * Slot.SCALE);

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

    public BufferStrategy getBufferStrategy() {
        return canvas.getBufferStrategy();
    }

    private void createElements() {
        canvas.setSize(WIDTH, HEIGHT);
        canvas.setVisible(true);
        canvas.setFocusable(false);
        canvas.setBackground(Color.DARK_GRAY);
        getContentPane().add(canvas);
    }

    public void setOnRightArrow(Runnable onRight) {
        getRootPane().registerKeyboardAction(
                e -> onRight.run(),
                KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0),
                JComponent.WHEN_IN_FOCUSED_WINDOW);
    }

    public void setOnLeftArrow(Runnable onLeft) {
        getRootPane().registerKeyboardAction(
                e -> onLeft.run(),
                KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0),
                JComponent.WHEN_IN_FOCUSED_WINDOW);
    }

    public void setOnSpace(Runnable onSpace) {
        getRootPane().registerKeyboardAction(
                e -> onSpace.run(),
                KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0),
                JComponent.WHEN_IN_FOCUSED_WINDOW);
    }
}
