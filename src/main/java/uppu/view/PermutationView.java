package uppu.view;

import uppu.model.ActionSequence;
import uppu.model.HomePoints;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSplitPane;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.IntConsumer;

public class PermutationView extends JFrame {

    private static final int WIDTH_CANVAS = (int) (280 * HomePoints.SCALE);
    private static final int HEIGHT = (int) (300 * HomePoints.SCALE);
    public static final int WIDTH_PANEL = 500;
    public static final int HEIGHT_SLIDER = 12;
    public static final int INITIAL_SPEED = 16;
    public static final int HEIGHT_BUTTON_PANE = 20;

    private final Canvas canvas = new Canvas() {
        @Override
        public void paint(Graphics g) {
        }
    };

    private final JList<ActionSequence> actions = new JList<>();
    private final JPanel sidePanel = new JPanel();
    private final JPanel buttonPanel = new JPanel();
    private final JSplitPane splitPane = new JSplitPane();
    private final JScrollPane scrollPanel = new JScrollPane(actions);
    private final JSlider slider = new JSlider(SwingConstants.HORIZONTAL, 0, 32, INITIAL_SPEED);
    private final JButton pauseButton = new JButton("Pause");
    private final JButton editButton = new JButton("Edit");

    private PermutationView() {
        super("");
    }

    public static PermutationView create() {
        PermutationView view = new PermutationView();
        view.setSize(WIDTH_CANVAS + WIDTH_PANEL, HEIGHT + HEIGHT_SLIDER);
        view.createElements();
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
        slider.setBackground(Color.DARK_GRAY);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(slider, BorderLayout.SOUTH);
        getContentPane().add(splitPane, BorderLayout.CENTER);
        splitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setLeftComponent(canvas);
        splitPane.setRightComponent(sidePanel);
        splitPane.setEnabled(false);
        actions.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 16));
        actions.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        scrollPanel.setSize(WIDTH_PANEL, HEIGHT - HEIGHT_BUTTON_PANE);
        sidePanel.setSize(WIDTH_PANEL, HEIGHT);
        sidePanel.setLayout(new BorderLayout());
        sidePanel.add(scrollPanel, BorderLayout.CENTER);
        buttonPanel.setSize(WIDTH_PANEL, HEIGHT_BUTTON_PANE);
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(pauseButton);
        buttonPanel.add(editButton);
        sidePanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Color
        getContentPane().setBackground(Color.DARK_GRAY);
        actions.setForeground(Color.WHITE);
        buttonPanel.setBackground(Color.DARK_GRAY);
        actions.setBackground(Color.DARK_GRAY);
        sidePanel.setBackground(Color.DARK_GRAY);
    }

    public void setActions(List<ActionSequence> actions) {
        this.actions.setListData(actions.toArray(new ActionSequence[0]));
    }

    public void setOnActionSelected(Consumer<ActionSequence> consumer) {
        actions.addListSelectionListener(e -> {
            if (e.getValueIsAdjusting()) {
                return;
            }
            int index;
            if (actions.getSelectionModel().isSelectedIndex(e.getFirstIndex())) {
                index = e.getFirstIndex();
            } else {
                index = e.getLastIndex();
            }
            if (index < actions.getModel().getSize()) {
                consumer.accept(actions.getModel().getElementAt(index));
            }
        });
    }

    public void setSelectedAction(ActionSequence action) {
        actions.setSelectedValue(action, true);
    }

    public void setOnSliderMoved(IntConsumer onMoved) {
        slider.addChangeListener(e -> {
            if (slider.getValueIsAdjusting()) {
                return;
            }
            onMoved.accept(slider.getValue());
        });
    }

    public void setOnEditButtonClicked(Runnable onClick) {
        editButton.addActionListener(e -> onClick.run());
    }

    public void setOnPauseButtonClicked(Runnable onClick) {
        pauseButton.addActionListener(e -> onClick.run());
    }

    public void setRunning(boolean running) {
        pauseButton.setForeground(running ? Color.BLACK : Color.MAGENTA);
    }
}
