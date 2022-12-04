package uppu.view;

import uppu.model.BiAction;
import uppu.model.Slot;

import javax.swing.AbstractListModel;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.util.List;
import java.util.function.Consumer;

public class PermutationView extends JFrame {

    private static final int WIDTH = (int) (300 * Slot.SCALE);
    private static final int HEIGHT = (int) (300 * Slot.SCALE);

    private final Canvas canvas = new Canvas() {
        @Override
        public void paint(Graphics g) {
        }
    };

    private final JList<BiAction> panel = new JList<>();

    private PermutationView() {
        super("uppu");
    }

    public static PermutationView create() {
        PermutationView view = new PermutationView();
        view.setSize(WIDTH + 100, HEIGHT);
        view.createElements();
        view.pack();
        view.setVisible(true);
        view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        view.getRootPane().registerKeyboardAction(
                e -> view.dispatchEvent(new WindowEvent(view, WindowEvent.WINDOW_CLOSING)),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_IN_FOCUSED_WINDOW);
        view.canvas.createBufferStrategy(4);
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
        getContentPane().setBackground(Color.DARK_GRAY);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(canvas, BorderLayout.WEST);
        panel.setBackground(Color.DARK_GRAY);
        panel.setForeground(Color.WHITE);
        panel.setModel(createListModel(List.of()));
        panel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPanel = new JScrollPane(panel);
        scrollPanel.setSize(100, HEIGHT);
        getContentPane().add(scrollPanel, BorderLayout.EAST);
    }

    private static ListModel<BiAction> createListModel(List<BiAction> actions) {
        return new AbstractListModel<>() {
            public int getSize() {
                return actions.size();
            }

            public BiAction getElementAt(int i) {
                return actions.get(i);
            }
        };
    }

    public void setActions(List<BiAction> actions) {
        panel.setModel(createListModel(actions));
    }

    public void setOnActionSelected(Consumer<BiAction> consumer) {
        panel.addListSelectionListener(e -> {
            if (e.getValueIsAdjusting()) {
                return;
            }
            if (panel.getSelectionModel().isSelectedIndex(e.getFirstIndex())) {
                consumer.accept(panel.getModel().getElementAt(e.getFirstIndex()));
            } else {
                consumer.accept(panel.getModel().getElementAt(e.getLastIndex()));
            } 
        });
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
