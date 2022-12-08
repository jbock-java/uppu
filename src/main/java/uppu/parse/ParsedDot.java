package uppu.parse;

final class ParsedDot implements Parsed {

    private static final ParsedDot INSTANCE = new ParsedDot();

    private ParsedDot() {
    }

    static ParsedDot parsedDot() {
        return INSTANCE;
    }

    @Override
    public String toString() {
        return ".";
    }

    @Override
    public boolean isDot() {
        return true;
    }
}
