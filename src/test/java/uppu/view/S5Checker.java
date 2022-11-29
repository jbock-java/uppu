package uppu.view;

import io.parmigiano.Permutation;
import uppu.engine.Animation;
import uppu.model.BiCommand;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static uppu.model.BiCommand.command;

class S5Checker {

    private final PermutationView view = PermutationView.create();

    public static void main(String[] args) {
        new S5Checker().run();
    }

    private void run() {
        view.setLocationRelativeTo(null);
        List<Permutation> permutations = Permutation.symmetricGroup(5).stream()
                .filter(p -> p.order() == 5)
                .toList();
        Map<Set<Permutation>, Permutation> m = new LinkedHashMap<>();
        List<BiCommand> commands = new ArrayList<>();
        commands.add(BiCommand.showState());
        commands.add(BiCommand.wait(20));
        for (Permutation p : permutations) {
            m.putIfAbsent(Set.of(p, p.invert()), p);
        }
        List<Permutation> values = List.copyOf(m.values());
        for (Permutation p : values) {
            commands.addAll(commands(p));
        }
        Animation.create(view, 5, 50).startAnimation(commands);
    }

    private List<BiCommand> commands(
            Permutation a) {
        return List.of(
                BiCommand.showState(),
                BiCommand.wait(20),
                command(a));
    }
}