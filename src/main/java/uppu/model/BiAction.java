package uppu.model;

import java.util.List;

public final class BiAction {

    private final List<Action> actions;
    private final String title;

    private int current;

    public BiAction(List<Action> actions, String title) {
        this.actions = actions;
        this.title = title;
    }

    public void init() {
        current = 0;
        for (Action a : actions) {
            a.init();
        }
    }

    public Action peekFirst() {
        if (current >= actions.size()) {
            return null;
        }
        return actions.get(current);
    }

    public void increment() {
        current++;
    }

    public String title() {
        return title;
    }

    @Override
    public String toString() {
        return title;
    }
}
