package uppu.parse;

import io.jbock.util.Either;

import static uppu.parse.TokenParser.parseContent;

record ParenExpression(String token) implements Expression {

    static ParenExpression paren(String token) {
        return new ParenExpression(token);
    }

    @Override
    public String toString() {
        return token;
    }

    @Override
    public Either<String, Parsed> parse() {
        return parseContent(token).map(ParsedToken::new);
    }
}
