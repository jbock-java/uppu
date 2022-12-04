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
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.util.List;
import java.util.function.Consumer;

public class PermutationView extends JFrame {

    private static final int WIDTH_CANVAS = (int) (300 * Slot.SCALE);
    private static final int HEIGHT = (int) (300 * Slot.SCALE);
    public static final int WIDTH_PANEL = 140;

    private final Canvas canvas = new Canvas() {
        @Override
        public void paint(Graphics g) {
        }
    };

    private final JList<BiAction> actions = new JList<>();

    private PermutationView() {
        super("");
    }

    public static PermutationView create() {
        PermutationView view = new PermutationView();
        view.setSize(WIDTH_CANVAS + WIDTH_PANEL, HEIGHT);
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
        canvas.setSize(WIDTH_CANVAS, HEIGHT);
        canvas.setVisible(true);
        canvas.setFocusable(false);
        canvas.setBackground(Color.DARK_GRAY);
        getContentPane().setBackground(Color.DARK_GRAY);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(canvas, BorderLayout.WEST);
        actions.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 16));
        actions.setBackground(Color.DARK_GRAY);
        actions.setForeground(Color.WHITE);
        actions.setModel(createListModel(List.of()));
        actions.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPanel = new JScrollPane(actions);
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
        this.actions.setModel(createListModel(actions));
    }

    public void setOnActionSelected(Consumer<BiAction> consumer) {
        actions.addListSelectionListener(e -> {
            if (e.getValueIsAdjusting()) {
                return;
            }
            if (actions.getSelectionModel().isSelectedIndex(e.getFirstIndex())) {
                consumer.accept(actions.getModel().getElementAt(e.getFirstIndex()));
            } else {
                consumer.accept(actions.getModel().getElementAt(e.getLastIndex()));
            }
        });
    }

    public void setSelectedAction(BiAction action) {
        actions.setSelectedValue(action, true);
    }

    public void setOnSpace(Runnable onSpace) {
        getRootPane().registerKeyboardAction(
                e -> onSpace.run(),
                KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0),
                JComponent.WHEN_IN_FOCUSED_WINDOW);
    }
}
