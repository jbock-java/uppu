package uppu.input;

import io.parmigiano.Permutation;
import uppu.model.BiCommand;
import uppu.model.Command;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static uppu.model.Command.command;

public class Input {

    public static List<BiCommand> commands(Permutation a, Permutation b) {
        return commands(List.of(a, b));
    }

    public static List<BiCommand> commands(List<Permutation> input) {
        Permutation undo = product(input).invert().normalize();
        List<Command> abCommands = new ArrayList<>(input.size() * 2 + 1);
        abCommands.add(Command.showState());
        abCommands.add(Command.wait(80));
        for (int i = input.size() - 1; i >= 0; i--) {
            abCommands.add(command(input.get(i)));
            if (i > 0) {
                abCommands.add(Command.wait(15));
            }
        }
        List<Command> undoCommands = List.of(
                Command.showState(),
                Command.wait(80),
                command(undo));
        return List.of(
                new BiCommand(
                        input.stream().map(Permutation::toString).collect(Collectors.joining(".")),
                        abCommands),
                new BiCommand(
                        undo.toString(),
                        undoCommands));
    }

    private static Permutation product(List<Permutation> permutations) {
        Permutation result = Permutation.identity();
        for (Permutation p : permutations) {
            result = result.compose(p);
        }
        return result;
    }
}
