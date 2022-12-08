package uppu.parse;

import io.parmigiano.Permutation;
import org.junit.jupiter.api.Test;

import static io.jbock.util.Either.left;
import static io.jbock.util.Either.right;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static uppu.parse.TokenParser.parseContent;

class TokenParserTest {

    @Test
    void testParseToken() {
        assertEquals(right(Permutation.create(1, 2, 3)), parseContent(" 1 2 3 "));
        assertEquals(right(Permutation.create(0, 1)), parseContent("1 0 "));
        assertEquals(left("Max index is 3 but found: 4"), parseContent(" 4 0"));
        assertEquals(left("Found nesting"), parseContent("(4 0)"));
        assertEquals(right(Permutation.identity()), parseContent(""));
        assertEquals(right(Permutation.identity()), parseContent(" "));
    }
}