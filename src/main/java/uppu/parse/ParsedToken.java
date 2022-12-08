package uppu.parse;

import io.parmigiano.Permutation;

record ParsedToken(Permutation permutation) implements Parsed {

    @Override
    public String toString() {
        return permutation.toString();
    }

    @Override
    public boolean isDot() {
        return false;
    }
    
    @Override
    public boolean isHome() {
        return false;
    }
}
