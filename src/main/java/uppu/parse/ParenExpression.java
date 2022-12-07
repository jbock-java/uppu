package uppu.parse;

record ParenExpression(String token) implements Expression {

    static ParenExpression paren(String token) {
        return new ParenExpression(token);
    }

    @Override
    public String toString() {
        return token;
    }
}
