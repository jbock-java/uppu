package uppu.parse;

import io.jbock.util.Either;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.jbock.util.Either.right;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static uppu.parse.DotExpression.dot;
import static uppu.parse.LineParser.parseLine;
import static uppu.parse.ParenExpression.paren;

class LineParserTest {

    @Test
    void testParseLine() {
        assertEquals(
                right(List.of()),
                parseLine(""));
        assertEquals(
                right(List.of(paren("1 2"))),
                parseLine("(1 2)"));
        assertEquals(
                right(List.of(dot(), dot(), dot())),
                parseLine(". . ."));
        assertEquals(
                right(List.of(paren(""))),
                parseLine("()"));
        assertEquals(
                right(List.of(paren(" "))),
                parseLine(" ( ) "));
        assertEquals(
                right(List.of(paren("1 2"), dot(), paren("3"))), 
                parseLine("(1 2) . (3)"));
        assertEquals(
                right(List.of(paren("1 2"), paren(" 3 "))),
                parseLine("(1 2) ( 3 )"));
        assertEquals(
                Either.left("Found unexpected character: 1"),
                parseLine("(1 2) 1 (1 2)"));
        assertEquals(
                Either.left("Found unexpected character: 1"),
                parseLine("1"));
    }
}