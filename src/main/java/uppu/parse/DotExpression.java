package uppu.parse;

import io.jbock.util.Either;

import static io.jbock.util.Either.right;
import static uppu.parse.ParsedDot.parsedDot;

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

    @Override
    public Either<String, Parsed> parse() {
        return right(parsedDot());
    }
}
