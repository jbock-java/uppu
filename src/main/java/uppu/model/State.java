package uppu.model;

import io.parmigiano.Permutation;
import uppu.engine.Mover;

import java.util.ArrayList;
import java.util.List;

public final class State {

    private final Quadruple quadruple;
    private final Slot slot;
    private final int n;

    private State(Quadruple quadruple, Slot slot, int n) {
        this.quadruple = quadruple;
        this.slot = slot;
        this.n = n;
    }

    public static State create(int n) {
        return new State(Quadruple.create(n), Slot.slots(n), n);
    }

    public State offset(int x, int y) {
        return new State(quadruple.offset(x, y), slot, n);
    }

    public List<Action> getActions(List<Command> commands) {
        List<Color> state = Color.colors(n);
        for (int i = 0; i < state.size(); i++) {
            quadruple.set(state.get(i), slot.forIndex(i).getX(), slot.forIndex(i).getY());
        }
        List<Action> result = new ArrayList<>(commands.size());
        for (Command command : commands) {
            ActionWithState action = getAction(state, command);
            result.add(action.action);
            state = action.finalState;
        }
        return result;
    }

    private static final class ActionWithState {

        final Action action;
        final List<Color> finalState;

        ActionWithState(Action action, List<Color> finalState) {
            this.action = action;
            this.finalState = finalState;
        }
    }

    private ActionWithState getAction(List<Color> state, Command command) {
        if (command instanceof MoveCommand) {
            return getAction(state, ((MoveCommand) command).permutation());
        }
        if (command instanceof WaitCommand) {
            return new ActionWithState(WaitAction.create(((WaitCommand) command).cycles()), state);
        }
        if (command instanceof ShowStateCommand) {
            return new ActionWithState(ShowStateAction.create(quadruple), state);
        }
        throw new IllegalArgumentException();
    }

    private ActionWithState getAction(List<Color> state, Permutation p) {
        List<Mover> movers = new ArrayList<>();
        Color[] newColors = new Color[state.size()];
        for (int i = 0; i < state.size(); i++) {
            Color color = state.get(i);
            int j = p.apply(i);
            Slot.AbstractSlot sourceSlot = slot.forIndex(i);
            Slot.AbstractSlot targetSlot = slot.forIndex(j);
            movers.add(Mover.create(color, quadruple, sourceSlot, targetSlot));
            newColors[j] = color;
        }
        return new ActionWithState(MoveAction.create(quadruple, movers), List.of(newColors));
    }
}
