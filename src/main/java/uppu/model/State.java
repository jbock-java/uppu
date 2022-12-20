package uppu.model;

import io.parmigiano.Permutation;
import uppu.engine.Mover;
import uppu.model.Command.MoveCommand;
import uppu.model.Command.WaitCommand;

import java.util.ArrayList;
import java.util.List;

public final class State {

    private final Quadruple quadruple;
    private final List<Point> homePoints;

    private State(Quadruple quadruple, List<Point> homePoints) {
        this.quadruple = quadruple;
        this.homePoints = homePoints;
    }

    public static State create(int n) {
        return new State(Quadruple.create(), HomePoints.homePoints(n));
    }

    public State offset(int x, int y) {
        return new State(quadruple.offset(x, y), homePoints);
    }

    public List<ActionSequence> getActions(List<CommandSequence> sequences) {
        List<Color> state = Color.colors(homePoints.size());
        for (int i = 0; i < state.size(); i++) {
            Point p = homePoints.get(i);
            quadruple.set(state.get(i), p.x(), p.y(), p.z());
        }
        List<ActionSequence> actionSequences = new ArrayList<>();
        for (CommandSequence sequence : sequences) {
            List<Action> actions = new ArrayList<>(sequence.commands().size());
            for (Command command : sequence.commands()) {
                ActionWithState action = getAction(state, command);
                actions.add(action.action);
                state = action.finalState;
            }
            actionSequences.add(new ActionSequence(actions, sequence.title()));
        }
        return actionSequences;
    }

    private record ActionWithState(
            Action action,
            List<Color> finalState) {
    }

    private ActionWithState getAction(
            List<Color> state,
            Command command) {
        if (command instanceof MoveCommand) {
            return getMoveAction(((MoveCommand) command).permutation(), state);
        }
        if (command instanceof WaitCommand) {
            return new ActionWithState(WaitAction.create(((WaitCommand) command).cycles()), state);
        }
        throw new IllegalArgumentException();
    }

    private ActionWithState getMoveAction(
            Permutation p,
            List<Color> state) {
        List<Mover> movers = new ArrayList<>();
        Color[] newColors = new Color[state.size()];
        for (int i = 0; i < state.size(); i++) {
            Color color = state.get(i);
            int j = p.apply(i);
            Point sourceSlot = homePoints.get(i);
            Point targetSlot = homePoints.get(j);
            movers.add(Mover.create(color, quadruple, sourceSlot, targetSlot));
            newColors[j] = color;
        }
        return new ActionWithState(MoveAction.create(this, movers), List.of(newColors));
    }

    public Quadruple quadruple() {
        return quadruple;
    }

    public List<Point> homePoints() {
        return homePoints;
    }
}
