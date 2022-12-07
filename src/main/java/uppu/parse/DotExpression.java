package uppu.parse;

final class DotExpression implements Expression {

    private static final DotExpression INSTANCE = new DotExpression();

    private DotExpression() {
    }

    static DotExpression dot() {
        return INSTANCE;
    }

    @Override
    public String toString() {
        return ".";
    }
}
