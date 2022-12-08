package uppu.parse;

final class ParsedHome implements Parsed {

    private static final ParsedHome INSTANCE = new ParsedHome();

    private ParsedHome() {
    }

    static ParsedHome parsedHome() {
        return INSTANCE;
    }

    @Override
    public String toString() {
        return ".";
    }

    @Override
    public boolean isDot() {
        return false;
    }
    
    @Override
    public boolean isHome() {
        return true;
    }
}
