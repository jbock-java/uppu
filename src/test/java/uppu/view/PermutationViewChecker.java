package uppu.view;

import uppu.model.Quadruple;

import javax.swing.Timer;

class PermutationViewChecker {

    public static void main(String[] args) throws InterruptedException {
        PermutationView view = PermutationView.create();
        view.setLocationRelativeTo(null);
        int[] pos = {50, 50};
        Quadruple quadruple = Quadruple.create();
        Thread.sleep(1000);
        Timer timer = new Timer(25, __ -> {
            pos[0]++;
            pos[1]++;
            view.show(quadruple, pos[0], pos[1]);
        });
        timer.start();
    }

}