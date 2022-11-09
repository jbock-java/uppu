package uppu.view;

import io.parmigiano.Permutation;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.KeyStroke;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

import static javax.swing.SwingUtilities.invokeLater;

class PermutationViewChecker {

    public static void main(String[] args) throws InterruptedException {
        PermutationView view = PermutationView.create();
        invokeLater(() -> {
            view.pack();
            view.setVisible(true);
            view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            view.getRootPane().registerKeyboardAction(
                    e -> view.dispatchEvent(new WindowEvent(view, WindowEvent.WINDOW_CLOSING)),
                    KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                    JComponent.WHEN_IN_FOCUSED_WINDOW);
        });
        view.setLocationRelativeTo(null);
        Thread.sleep(1000);
        invokeLater(() -> {
            view.showPermutation(Permutation.create(0, 1, 2));
        });
        Thread.sleep(1000);
        invokeLater(() -> {
            view.showPermutation(Permutation.create(0, 1).compose(Permutation.create(2, 3)));
        });
    }

}