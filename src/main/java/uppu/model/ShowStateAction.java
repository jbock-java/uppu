package uppu.model;

import java.awt.Graphics2D;

public class ShowStateAction extends Action {

    private final State state;

    private ShowStateAction(State state) {
        this.state = state;
    }

    static ShowStateAction create(State state) {
        return new ShowStateAction(state);
    }

    @Override
    public boolean move() {
        return false;
    }

    @Override
    public State finalState() {
        return state;
    }

    @Override
    public void show(Graphics2D g, Label label) {
        show(g, state.quadruple());
        showLabel(g, label);
    }
}
