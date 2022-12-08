package uppu.parse;

import io.parmigiano.Permutation;

import java.util.List;
import java.util.stream.Collectors;

public record ExplicitRow(List<Permutation> permutations) implements Row {

    public static ExplicitRow explicitRow(List<Permutation> permutations) {
        return new ExplicitRow(permutations);
    }

    @Override
    public List<Permutation> permutations(Permutation current) {
        return permutations;
    }

    @Override
    public String toString(Permutation current) {
        return permutations.stream().map(Permutation::toString).collect(Collectors.joining(" . "));
    }
}
