package uppu.model;

import io.parmigiano.Permutation;
import uppu.engine.Mover;

import java.util.ArrayList;
import java.util.List;

public final class State {

    private final Quadruple quadruple;
    private final List<Color> colors;

    private State(Quadruple quadruple, List<Color> colors) {
        this.quadruple = quadruple;
        this.colors = colors;
    }

    public static State create() {
        return new State(Quadruple.create(), List.of(Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW));
    }

    public State offset(int x, int y) {
        return new State(quadruple.offset(x, y), colors);
    }

    public List<Action> getActions(List<Command> permutations) {
        List<Action> result = new ArrayList<>();
        State state = this;
        for (Command p : permutations) {
            ActionWithState action = state.getAction(p);
            result.add(action.action);
            state = new State(quadruple, action.finalState);
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
    
    private ActionWithState getAction(Command command) {
        if (command instanceof MoveCommand) {
            return getAction(((MoveCommand) command).permutation());
        }
        if (command instanceof WaitCommand) {
            return new ActionWithState(WaitAction.create(((WaitCommand) command).cycles()), colors);
        }
        if (command instanceof ShowStateCommand) {
            return new ActionWithState(ShowStateAction.create(quadruple), colors);
        }
        throw new IllegalArgumentException();
    }

    private ActionWithState getAction(Permutation p) {
        List<Mover> movers = new ArrayList<>();
        Color[] newColors = new Color[4];
        for (int i = 0; i < colors.size(); i++) {
            Color color = colors.get(i);
            int j = p.apply(i);
            if (j != i) {
                Slot targetSlot = Slot.forIndex(j);
                movers.add(new Mover(color, quadruple, targetSlot));
            }
            newColors[j] = color;
        }
        return new ActionWithState(MoveAction.create(quadruple, movers), List.of(newColors));
    }

    @Override
    public String toString() {
        return String.join(", ", colors.stream().map(Color::toString).toList())
                + " (" + quadruple.getOffsetX() + " ," + quadruple.getOffsetY() + ")";
    }
}