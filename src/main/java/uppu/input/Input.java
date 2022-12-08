package uppu.input;

import io.parmigiano.Permutation;
import uppu.model.BiCommand;
import uppu.model.Command;
import uppu.parse.Row;

import java.util.ArrayList;
import java.util.List;

import static uppu.model.Command.command;

public class Input {

    public static BiCommand singleCommand(
            Row row,
            Permutation current) {
        List<Permutation> input = row.permutations(current);
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
                row.toString(current),
                abCommands);
    }
}
