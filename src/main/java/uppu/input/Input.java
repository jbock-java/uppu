package uppu.input;

import io.parmigiano.Permutation;
import uppu.model.BiCommand;
import uppu.model.Command;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static uppu.model.Command.command;

public class Input {

    public static List<BiCommand> commands(Permutation... permutations) {
        BiCommand command = singleCommand(List.of(permutations));
        return List.of(command, singleCommand(List.of(command.permutation().invert())));
    }

    public static BiCommand singleCommand(List<Permutation> input) {
        List<Command> abCommands = new ArrayList<>(input.size() * 2 + 1);
        abCommands.add(Command.showState());
        abCommands.add(Command.wait(80));
        Permutation product = Permutation.identity();
        for (int i = input.size() - 1; i >= 0; i--) {
            Permutation p = input.get(i);
            Command command = command(p);
            product = p.compose(product);
            abCommands.add(command);
            if (i > 0) {
                abCommands.add(Command.wait(15));
            }
        }
        return new BiCommand(
                product,
                input.stream().map(Permutation::toString).collect(Collectors.joining(" . ")),
                abCommands);
    }
}
