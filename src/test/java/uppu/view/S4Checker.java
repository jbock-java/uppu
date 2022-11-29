package uppu.view;

import io.parmigiano.Permutation;
import uppu.engine.Animation;
import uppu.model.BiCommand;

import java.util.ArrayList;
import java.util.List;

import static uppu.model.BiCommand.command;

class S4Checker {

    private final PermutationView view = PermutationView.create();

    public static void main(String[] args) {
        new S4Checker().run();
    }

    private void run() {
        view.setLocationRelativeTo(null);
        List<Permutation> permutations = Permutation.symmetricGroup(4).stream()
                .filter(p -> p.order() == 3)
                .toList();
        List<BiCommand> commands = new ArrayList<>();
        commands.add(BiCommand.showState());
        commands.add(BiCommand.wait(20));
        for (Permutation p : permutations) {
            commands.addAll(commands(p));
        }
        Animation.create(view, 4, 66).startAnimation(commands);
    }

    private List<BiCommand> commands(
            Permutation a) {
        return List.of(
                BiCommand.showState(),
                BiCommand.wait(20),
                command(a));
    }
}