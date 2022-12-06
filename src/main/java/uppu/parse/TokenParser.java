package uppu.parse;

import io.jbock.util.Either;
import io.parmigiano.Permutation;

import java.util.regex.Pattern;

public final class TokenParser {

    private static final Pattern WHITESPACE = Pattern.compile("\\s+");

    public static Either<String, Permutation> parseToken(String token) {
        return parseTrimmed(token.trim());
    }

    private static Either<String, Permutation> parseTrimmed(String token) {
        if (!token.startsWith("(")) {
            return Either.left("Opening parenthesis not found.");
        }
        if (!token.endsWith(")")) {
            return Either.left("Closing parenthesis not found.");
        }
        return parseContent(token.substring(1, token.length() - 1).trim());
    }

    private static Either<String, Permutation> parseContent(String content) {
        String[] tokens = WHITESPACE.split(content);
        int[] input = new int[tokens.length];
        if (tokens.length == 0) {
            return Either.right(Permutation.identity());
        }
        if (tokens[0].startsWith("(")) {
                return Either.left("Nesting parentheses is not allowed");
        }
        for (int i = 0; i < tokens.length; i++) {
            String token = tokens[i];
            switch (token) {
                case "0":
                    input[i] = 0;
                    break;
                case "1":
                    input[i] = 1;
                    break;
                case "2":
                    input[i] = 2;
                    break;
                case "3":
                    input[i] = 3;
                    break;
                case "4":
                case "5":
                case "6":
                case "7":
                case "8":
                case "9":
                    return Either.left("Max index is 3 but found: " + token);
                case "(":
                    return Either.left("Nesting parentheses is not allowed");
                case ")":
                    return Either.left("Found extra closing parenthesis");
                default:
                    return Either.left("Unknown token: " + token);
            }
        }
        return Either.right(Permutation.create(input));
    }

    private TokenParser() {
    }
}