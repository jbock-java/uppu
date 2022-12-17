package uppu.model;

import io.parmigiano.Permutation;
import uppu.engine.Mover;

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
        return new State(Quadruple.create(), HomePoint.homePoints(n));
    }

    public State offset(int x, int y) {
        return new State(quadruple.offset(x, y), homePoints);
    }

    public List<BiAction> getActions(List<BiCommand> biCommands) {
        List<Color> state = Color.colors(homePoints.size());
        for (int i = 0; i < state.size(); i++) {
            Point p = homePoints.get(i);
            quadruple.set(state.get(i), p.x(), p.y(), p.z());
        }
        List<BiAction> biActions = new ArrayList<>();
        for (BiCommand biCommand : biCommands) {
            List<Action> result = new ArrayList<>(biCommand.commands().size());
            for (Command command : biCommand.commands()) {
                ActionWithState action = getAction(state, command);
                result.add(action.action);
                state = action.finalState;
            }
            biActions.add(new BiAction(result, biCommand.title()));
        }
        return biActions;
    }

    private record ActionWithState(
            Action action,
            List<Color> finalState) {
    }

    private ActionWithState getAction(
            List<Color> state,
            Command command) {
        if (command instanceof MoveCommand) {
            return getMoveAction(state, ((MoveCommand) command).permutation());
        }
        if (command instanceof WaitCommand) {
            return new ActionWithState(WaitAction.create(((WaitCommand) command).cycles()), state);
        }
        throw new IllegalArgumentException();
    }

    private ActionWithState getMoveAction(
            List<Color> state,
            Permutation p) {
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
