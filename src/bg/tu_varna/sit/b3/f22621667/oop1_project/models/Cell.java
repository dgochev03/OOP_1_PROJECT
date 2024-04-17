package bg.tu_varna.sit.b3.f22621667.oop1_project.models;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Cell {
    private String content;
    private int row;
    private int col;
    private boolean isFormula;
    private String rightOperand;
    private String leftOperand;
    private String operator;
    private boolean isCalculated;
    private boolean isString;

    public static final Cell Empty = new Cell("");

    public Cell(String content) {
        checkAndExtract(content);
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

    public boolean isFormula() {
        return isFormula;
    }

    public boolean isCalculated() {
        return isCalculated;
    }

    public void setCalculated() {
        this.isCalculated = true;
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

    public String getRightOperand() {
        return rightOperand;
    }

    public String getLeftOperand() {
        return leftOperand;
    }

    public String getOperator() {
        return operator;
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
        return "R" + row + "C" + col;
    }

    public void checkAndExtract(String input) {
        if (!input.startsWith("=")) {
            setContent(input);
            return;
        }

        String formula = input.substring(1).trim();

        List<String> operands = new ArrayList<>();
        List<String> operators = new ArrayList<>();

        StringBuilder currentOperand = new StringBuilder();
        boolean isOperand = false;
        boolean isStringLiteral = false;
        char lastChar = '\0';

        for (int i = 0; i < formula.length(); i++) {
            char currentChar = formula.charAt(i);

            if (currentChar == '"') {
                isStringLiteral = !isStringLiteral;
            }

            if (Character.isWhitespace(currentChar) && !isStringLiteral) {
                continue;
            }

            if (isOperator(currentChar) && !isStringLiteral) {
                operands.add(currentOperand.toString());
                currentOperand.setLength(0);

                operators.add(String.valueOf(currentChar));
                isOperand = false;
            } else {
                currentOperand.append(currentChar);
                isOperand = true;
            }

            if (i == formula.length() - 1 && isOperand) {
                operands.add(currentOperand.toString());
            }

            lastChar = currentChar;
        }

        if (operands.size() != operators.size() + 1) {
            setContent(input);
            return;
        }

        leftOperand = operands.get(0);
        operator = operators.get(0);
        rightOperand = operands.get(1);

        isFormula = true;
        isCalculated = false;
    }

    private boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
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
                normalized.append(c);
                isString = inQuotes;
            }
        }

        return normalized.toString();
    }

    public boolean checkContentIntegrity() {
        if (this.content.isEmpty() || isFormula) {
            return true;
        }

        String regex = "^[-+]?\\d*\\.?\\d+$";
        boolean isNumber = Pattern.matches(regex, this.content);

        if (!isNumber && isString) {
            return true;
        }

        try {
            Double.parseDouble(this.content);
        } catch (Exception ex) {
            return false;
        }

        return true;
    }
}
