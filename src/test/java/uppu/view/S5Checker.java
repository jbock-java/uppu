package uppu.view;

import io.parmigiano.Permutation;
import uppu.engine.Animation;
import uppu.input.Input;
import uppu.model.BiCommand;
import uppu.model.Slot;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
        for (Permutation p : permutations) {
            m.putIfAbsent(Set.of(p, p.invert()), p);
        }
        List<Permutation> values = List.copyOf(m.values());
        for (Permutation p : values) {
            commands.add(commands(p));
        }
        Animation animation = Animation.create(view, 5, (int) (25 * Slot.SCALE));
        animation.startAnimation(commands);
        view.setRunning(animation.togglePause());
        view.validate();
    }

    private BiCommand commands(
            Permutation a) {
        return Input.singleCommand(List.of(a.invert(), a));
    }
}