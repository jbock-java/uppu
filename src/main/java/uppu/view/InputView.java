package uppu.view;

import uppu.model.BiAction;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class InputView extends JFrame {

    private static final int HEIGHT_TEXTAREA = 600;
    private final JTextArea textArea = new JTextArea();
    private final JButton saveButton = new JButton("Save");

    private InputView() throws HeadlessException {
    }

    public static InputView create(JFrame parent) {
        InputView view = new InputView();
        view.createElements();
        view.setVisible(true);
        view.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        view.getRootPane().registerKeyboardAction(
                e -> view.dispatchEvent(new WindowEvent(view, WindowEvent.WINDOW_CLOSING)),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_IN_FOCUSED_WINDOW);
        view.setLocationRelativeTo(parent);
        return view;
    }

    private void createElements() {
        JScrollPane pane = new JScrollPane(textArea);
        pane.setSize(PermutationView.WIDTH_PANEL * 2, HEIGHT_TEXTAREA);
        getContentPane().add(pane, BorderLayout.CENTER);
        textArea.setBackground(Color.DARK_GRAY);
        textArea.setForeground(Color.WHITE);
        textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 16));
        textArea.setCaretColor(Color.WHITE);
        setSize(PermutationView.WIDTH_PANEL * 2, HEIGHT_TEXTAREA + 12);
        getContentPane().setBackground(Color.DARK_GRAY);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.DARK_GRAY);
        buttonPanel.add(saveButton);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
    }

    public void setContent(List<BiAction> actions) {
        for (BiAction action : actions) {
            textArea.append(action.toString());
            textArea.append(System.lineSeparator());
        }
    }

    public void setOnSave(Consumer<List<String>> consumer) {
        saveButton.addActionListener(e -> {
            String text = textArea.getText();
            String[] lines = text.split("\\R", -1);
            consumer.accept(Stream.of(lines).filter(line -> !line.isEmpty()).toList());
        });
    }
}
