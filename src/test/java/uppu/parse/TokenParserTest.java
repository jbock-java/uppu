package uppu.parse;

import io.parmigiano.Permutation;
import org.junit.jupiter.api.Test;

import static io.jbock.util.Either.left;
import static io.jbock.util.Either.right;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static uppu.parse.TokenParser.parseToken;

class TokenParserTest {

    @Test
    void testParseToken() {
        assertEquals(right(Permutation.create(1, 2, 3)), parseToken("(1 2 3)"));
        assertEquals(right(Permutation.create(0, 1)), parseToken("(1 0)"));
        assertEquals(left("Max index is 3 but found: 4"), parseToken("(4 0)"));
        assertEquals(left("Nesting parentheses is not allowed"), parseToken("((4 0))"));
    }
}