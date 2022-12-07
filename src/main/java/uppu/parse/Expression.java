package uppu.parse;

sealed interface Expression permits DotExpression, ParenExpression {
}
