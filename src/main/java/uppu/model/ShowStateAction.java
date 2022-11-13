package uppu.model;

import java.awt.Graphics2D;

public class ShowStateAction extends Action {

    private final Quadruple quadruple;

    private ShowStateAction(Quadruple quadruple) {
        this.quadruple = quadruple;
    }

    static ShowStateAction create(Quadruple quadruple) {
        return new ShowStateAction(quadruple);
    }

    @Override
    public boolean move() {
        return false;
    }

    @Override
    public void show(Graphics2D g, Label label) {
        show(g, quadruple);
        showLabel(g, label);
    }
}