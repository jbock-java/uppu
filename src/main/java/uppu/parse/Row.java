package uppu.parse;

import io.parmigiano.Permutation;

import java.util.List;

public sealed interface Row permits HomeRow, ExplicitRow {

    List<Permutation> permutations(Permutation current);
    
    String toString(Permutation current);
}
