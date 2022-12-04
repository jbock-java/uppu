package uppu.products;

import io.parmigiano.Permutation;
import uppu.model.BiCommand;
import uppu.model.Command;

import java.util.List;

import static uppu.model.BiCommand.command;

public record Product(Permutation a, Permutation b) {

    public Product halfInvert() {
        return new Product(a, b.invert());
    }

    public BiCommand commands() {
        List<Command> commands = List.of(
                command(Permutation.identity()),
                BiCommand.wait(80),
                command(b),
                BiCommand.wait(15),
                command(a),
                BiCommand.wait(80),
                command(a.compose(b).invert()));
        return new BiCommand(a + " " + b, commands);
    }
}
