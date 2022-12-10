package uppu.view;

import uppu.model.BiAction;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.text.Document;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
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
        initUndoManager();
    }

    // https://stackoverflow.com/questions/24433089/jtextarea-settext-undomanager
    private void initUndoManager() {
        int mask = Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx();
        UndoManager undoManager = new UndoManager();
        Document doc = textArea.getDocument();
        doc.addUndoableEditListener(e -> undoManager.addEdit(e.getEdit()));
        textArea.getActionMap().put("Undo", new AbstractAction("Undo") {
            @Override
            public void actionPerformed(ActionEvent evt) {
                try {
                    if (undoManager.canUndo()) {
                        undoManager.undo();
                    }
                } catch (CannotUndoException e) {
                    e.printStackTrace();
                }
            }
        });
        textArea.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, mask), "Undo");
        textArea.getActionMap().put("Redo", new AbstractAction("Redo") {
            @Override
            public void actionPerformed(ActionEvent evt) {
                try {
                    if (undoManager.canRedo()) {
                        undoManager.redo();
                    }
                } catch (CannotRedoException e) {
                    e.printStackTrace();
                }
            }
        });
        textArea.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_Y, mask), "Redo");
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
