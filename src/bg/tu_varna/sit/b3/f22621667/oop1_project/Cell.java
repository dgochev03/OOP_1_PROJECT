package bg.tu_varna.sit.b3.f22621667.oop1_project;

public class Cell {
    private String content;
    private int row;
    private  int col;
    public static final Cell Empty = new Cell("");

    public Cell(String content) {
        setContent(content);
    }

    public Cell(String content, int row, int col) {
        setContent(content);
        setRow(row);
        setCol(col);
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = normalizeString(content);
    }

    public double getNumericValue() {
        return convertToNumber(this.content);
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    private double convertToNumber(String input) {
        if (input.isEmpty()) {
            return 0.0;
        }

        try {
            return Double.parseDouble(input);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    @Override
    public String toString() {
        return "R" + row +  "C" + col;
    }

    private String normalizeString(String input) {
        StringBuilder normalized = new StringBuilder();

        boolean inQuotes = false;
        boolean escaped = false;

        for (char c : input.toCharArray()) {
            if (escaped) {
                normalized.append(c);
                escaped = false;
            } else if (c == '\\') {
                escaped = true;
            } else if (c == '"') {
                inQuotes = !inQuotes;
            } else {
                if (!inQuotes) {
                    normalized.append(c);
                } else {
                    normalized.append(c);
                }
            }
        }

        return normalized.toString();
    }
}