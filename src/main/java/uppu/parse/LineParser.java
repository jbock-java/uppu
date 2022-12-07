package uppu.parse;

import io.jbock.util.Either;

import java.util.ArrayList;
import java.util.List;

import static io.jbock.util.Either.left;
import static io.jbock.util.Either.right;
import static java.lang.Character.isWhitespace;
import static uppu.parse.DotExpression.dot;
import static uppu.parse.ParenExpression.paren;

public class LineParser {

    public static Either<String, List<Expression>> parseLine(String line) {
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
        return right(result);
    }
}
