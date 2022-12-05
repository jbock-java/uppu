package uppu.model;

import io.parmigiano.Permutation;

public sealed interface Command permits MoveCommand, WaitCommand {

    MoveCommand SHOW_STATE = new MoveCommand(Permutation.identity());

    static Command command(Permutation left) {
        return new MoveCommand(left);
    }

    static Command showState() {
        return SHOW_STATE;
    }

     static Command wait(int cycles) {
        return new WaitCommand(cycles);
    }

}
