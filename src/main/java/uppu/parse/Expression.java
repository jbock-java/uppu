package uppu.parse;

import io.jbock.util.Either;

sealed interface Expression permits DotExpression, HomeExpression, ParenExpression {

    Either<String, Parsed> parse();
}
