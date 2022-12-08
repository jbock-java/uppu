package uppu.parse;

import io.jbock.util.Either;

import static io.jbock.util.Either.right;
import static uppu.parse.ParsedHome.parsedHome;

final class HomeExpression implements Expression {

    private static final HomeExpression INSTANCE = new HomeExpression();

    private HomeExpression() {
    }

    static HomeExpression homeExpression() {
        return INSTANCE;
    }

    @Override
    public String toString() {
        return "~";
    }

    @Override
    public Either<String, Parsed> parse() {
        return right(parsedHome());
    }
}
