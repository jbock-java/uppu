package uppu.parse;

import io.parmigiano.Permutation;

import java.util.List;

public final class HomeRow implements Row {

    private static final HomeRow INSTANCE = new HomeRow();

    private HomeRow() {
    }

    static HomeRow homeRow() {
        return INSTANCE;
    }

    @Override
    public List<Permutation> permutations(Permutation current) {
        return List.of(current.invert());
    }

    @Override
    public String toString(Permutation current) {
        return "~";
    }
}
