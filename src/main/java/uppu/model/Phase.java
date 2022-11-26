package uppu.model;

import java.util.List;

public final class Phase {

    private final List<Action> actions;

    private Phase(List<Action> actions) {
        this.actions = actions;
    }

    public static Phase create(List<Action> actions) {
        return new Phase(actions);
    }

    public List<Action> actions() {
        return actions;
    }
}
