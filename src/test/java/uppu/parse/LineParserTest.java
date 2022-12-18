package uppu.parse;

import org.junit.jupiter.api.Test;

import java.util.List;

import static io.jbock.util.Either.left;
import static io.jbock.util.Either.right;
import static io.parmigiano.Permutation.create;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static uppu.parse.DotExpression.dot;
import static uppu.parse.ExplicitRow.explicitRow;
import static uppu.parse.LineParser.parse;
import static uppu.parse.LineParser.parseLine;
import static uppu.parse.ParenExpression.parenExpression;

class LineParserTest {

    @Test
    void testParseLine() {
        assertEquals(
                right(List.of()),
                parseLine(""));
        assertEquals(
                right(List.of(parenExpression("1 2"))),
                parseLine("(1 2)"));
        assertEquals(
                right(List.of(dot(), dot(), dot())),
                parseLine(". . ."));
        assertEquals(
                right(List.of(parenExpression(""))),
                parseLine("()"));
        assertEquals(
                right(List.of(parenExpression(" "))),
                parseLine(" ( ) "));
        assertEquals(
                right(List.of(parenExpression("1 2"), dot(), parenExpression("3"))),
                parseLine("(1 2) . (3)"));
        assertEquals(
                right(List.of(parenExpression("1 2"), parenExpression(" 3 "))),
                parseLine("(1 2) ( 3 )"));
        assertEquals(
                left("Found unexpected character: 1"),
                parseLine("(1 2) 1 (1 2)"));
        assertEquals(
                left("Found unexpected character: 1"),
                parseLine("1"));
    }

    @Test
    void unmatched() {
        assertEquals(
                left("Unmatched opening parentheses"),
                parseLine("(1 2"));
        assertEquals(
                left("Too many closing parentheses"),
                parseLine("(1 2))"));
    }

    @Test
    void nesting() {
        assertEquals(
                left("Nesting parentheses is not allowed."),
                parseLine("((1 2))"));
    }

    @Test
    void testParse() {
        assertEquals(
                right(explicitRow(List.of(create(0, 1), create(0, 2), create(0, 3)))),
                parse("(0 1) . (0 2) . (0 3)"));
        assertEquals(
                right(explicitRow(List.of(create(0, 1).compose(2, 3)))),
                parse("(0 1) (2 3)"));
        assertEquals(
                right(explicitRow(List.of(create(0, 1).compose(2, 3), create(0, 2).compose(1, 3)))),
                parse("(0 1) (2 3) . (0 2) (1 3)"));
    }
}