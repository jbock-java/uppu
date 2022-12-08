package uppu.parse;

import io.jbock.util.Either;
import io.jbock.util.Eithers;
import io.parmigiano.Permutation;

import java.util.ArrayList;
import java.util.List;

import static io.jbock.util.Either.left;
import static io.jbock.util.Either.right;
import static java.lang.Character.isWhitespace;
import static uppu.parse.DotExpression.dot;
import static uppu.parse.ParenExpression.paren;

public class LineParser {

    public static Either<String, List<Permutation>> parse(String line) {
        return parseLine(line)
                .flatMap(expressions -> expressions.stream().map(Expression::parse).collect(Eithers.firstFailure()))
                .flatMap(LineParser::read);
    }

    private static Either<String, List<Permutation>> read(List<Parsed> expressions) {
        if (expressions.isEmpty()) {
            return right(List.of());
        }
        if (expressions.get(0).isDot()) {
            return left("Found dot at beginning of line");
        }
        List<Permutation> result = new ArrayList<>();
        Permutation current = Permutation.identity();
        boolean dot = false;
        for (Parsed expression : expressions) {
            if (expression.isDot()) {
                if (dot) {
                    return left("Found duplicate dot");
                }
                dot = true;
                continue;
            }
            Permutation permutation = ((ParsedToken) expression).permutation();
            if (dot) {
                result.add(current);
                current = permutation;
            } else {
                current = current.compose(permutation);
            }
            dot = false;
        }
        result.add(current);
        return right(result);
    }

    static Either<String, List<Expression>> parseLine(String line) {
        List<Expression> result = new ArrayList<>();
        int parenCount = 0;
        StringBuilder tokenBuilder = new StringBuilder();
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == '(') {
                parenCount++;
                continue;
            }
            if (c == ')') {
                parenCount--;
                if (parenCount == 0) {
                    result.add(paren(tokenBuilder.toString()));
                    tokenBuilder.setLength(0);
                }
                if (parenCount < 0) {
                    return left("Too many closing parentheses");
                }
                continue;
            }
            if (parenCount == 0) {
                if (isWhitespace(c)) {
                    continue;
                }
                if (c == '.') {
                    result.add(dot());
                    continue;
                }
                return left("Found unexpected character: " + c);
            }
            tokenBuilder.append(c);
        }
        if (parenCount > 0) {
            return left("Unmatched opening parentheses");
        }
        return right(result);
    }
}
