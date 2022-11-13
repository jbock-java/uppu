package uppu.model;

import java.util.List;

public final class Phase {

    private final List<Action> actions;
    private final Label label;

    private Phase(Label label, List<Action> actions) {
        this.label = label;
        this.actions = actions;
    }

    public static Phase create(Label label, List<Action> actions) {
        return new Phase(label, actions);
    }

    public List<Action> actions() {
        return actions;
    }

    public Label label() {
        return label;
    }
}
