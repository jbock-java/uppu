package uppu.model;

public final class Label {
    
    private final String text;
    private final int hlStart;
    private final int hlEnd;

    private Label(String text, int hlStart, int hlEnd) {
        this.text = text;
        this.hlStart = hlStart;
        this.hlEnd = hlEnd;
    }

    public static Label create(String text) {
        return new Label(text, 0, 0);
    }

    public Label highlight(int start, int hlEnd) {
        return new Label(text, start, hlEnd);
    }

    public Label highlight(int start) {
        return new Label(text, start, text.length());
    }

    public Label highlight() {
        return new Label(text, 0, text.length());
    }

    public String text() {
        return text;
    }

    public int hlStart() {
        return hlStart;
    }

    public int hlEnd() {
        return hlEnd;
    }

    public boolean isHighlight(int pos) {
        return pos >= hlStart && pos < hlEnd;
    }
}
