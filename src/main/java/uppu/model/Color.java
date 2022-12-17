package uppu.model;

import java.util.Arrays;
import java.util.List;

public enum Color {

    // WILD_WATERMELON
    RED(new java.awt.Color(252, 108, 133).brighter()),

    // PANTONE_GREEN
    GREEN(new java.awt.Color(152, 251, 152)),

    // NCS_YELLOW
//    YELLOW(new java.awt.Color(255, 211, 0)),

    // ROBIN_EGG_BLUE
    BLUE(new java.awt.Color(0, 204, 204).brighter()),

    SILVER(new java.awt.Color(220, 220, 220)),
    ;

    private final java.awt.Color awtColor;
    private final java.awt.Color glowColor;

    Color(java.awt.Color awtColor) {
        this.awtColor = awtColor;
        this.glowColor = awtColor.darker();
    }

    public java.awt.Color awtColor() {
        return awtColor;
    }

    public java.awt.Color glowColor() {
        return glowColor;
    }

    public static List<Color> colors(int n) {
        return Arrays.stream(values()).limit(n).toList();
    }
}
