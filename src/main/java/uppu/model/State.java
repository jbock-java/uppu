package uppu.model;

import io.parmigiano.Permutation;
import uppu.engine.Mover;

import java.util.ArrayList;
import java.util.List;

public final class State {

    private final Quadruple quadruple;

    private State(Quadruple quadruple) {
        this.quadruple = quadruple;
    }

    public static State create() {
        return new State(Quadruple.create());
    }

    public State offset(int x, int y) {
        return new State(quadruple.offset(x, y));
    }

    public List<Action> getActions(List<Command> commands) {
        List<Color> state = List.of(Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW);
        for (int i = 0; i < state.size(); i++) {
            quadruple.set(state.get(i), Slot.forIndex(i).getX(), Slot.forIndex(i).getY());
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
        Color[] newColors = new Color[4];
        for (int i = 0; i < state.size(); i++) {
            Color color = state.get(i);
            int j = p.apply(i);
            if (j != i) {
                Slot sourceSlot = Slot.forIndex(i);
                Slot targetSlot = Slot.forIndex(j);
                movers.add(Mover.create(color, quadruple, sourceSlot, targetSlot));
            }
            newColors[j] = color;
        }
        return new ActionWithState(MoveAction.create(quadruple, movers), List.of(newColors));
    }
}
