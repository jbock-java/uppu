package uppu.parse;

sealed interface Parsed permits ParsedDot, ParsedHome, ParsedToken {

    boolean isDot();

    boolean isHome();
}
