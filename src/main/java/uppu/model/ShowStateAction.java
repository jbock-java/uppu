package uppu.model;

import java.awt.Graphics2D;

public class ShowStateAction extends Action {

    private final Quadruple quadruple;

    private ShowStateAction(String title, Quadruple quadruple) {
        super(title);
        this.quadruple = quadruple;
    }

    static ShowStateAction create(String title, Quadruple quadruple) {
        return new ShowStateAction(title, quadruple);
    }

    @Override
    public boolean move() {
        return false;
    }

    @Override
    public void show(Graphics2D g) {
        show(g, quadruple);
    }

    @Override
    String type() {
        return "SHOW";
    }

    @Override
    public void init() {
    }

    @Override
    public boolean isShowState() {
        return true;
    }
}
