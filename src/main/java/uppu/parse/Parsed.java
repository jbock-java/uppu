package uppu.parse;

sealed interface Parsed permits ParsedDot, ParsedToken {

    boolean isDot();
}
