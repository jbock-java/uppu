package uppu.view;

import uppu.engine.Mover;
import uppu.engine.Movers;
import uppu.model.Color;
import uppu.model.Quadruple;
import uppu.model.Slot;

import javax.swing.SwingUtilities;
import javax.swing.Timer;
import java.util.List;

class PermutationViewChecker {

    private final PermutationView view = PermutationView.create();
    private final Quadruple quadruple = Quadruple.create();
    private Timer timer;

    public static void main(String[] args) {
        new PermutationViewChecker().run();
    }

    private void run() {
        view.setLocationRelativeTo(null);
        SwingUtilities.invokeLater(() -> {
            view.show(quadruple, 50, 50);
            view.show(quadruple, 50, 50);
        });
        setTimeout(() -> startAnimation(Slot.SLOT_1));
    }

    private void startAnimation(Slot slot) {
        Mover m2 = new Mover(Color.RED, quadruple, slot);
        Mover m1 = new Mover(Color.GREEN, quadruple, slot.add(1));
        Mover m3 = new Mover(Color.BLUE, quadruple, slot.add(2));
        Mover m4 = new Mover(Color.YELLOW, quadruple, slot.add(3));
        Movers movers = Movers.create(List.of(m1, m2, m3, m4));
        timer = new Timer(25, __ -> {
            if (!movers.move()) {
                timer.stop();
                setTimeout(() -> startAnimation(slot.nextSlot()));
            }
            view.show(quadruple, 50, 50);
        });
        timer.start();
    }

    private void setTimeout(Runnable task) {
        Timer t = new Timer(1000, ___ -> task.run());
        t.setRepeats(false);
        t.start();
    }
}