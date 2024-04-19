package bg.tu_varna.sit.b3.f22621667.oop1_project.models;

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

    public void setRow(int row) {
        this.row = row;
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

         StringBuilder leftOperandBuilder = new StringBuilder();
        StringBuilder rightOperandBuilder = new StringBuilder();
        String operator = null;
        boolean isStringLiteral = false;

        for (char currentChar : formula.toCharArray()) {
           if (currentChar == '"') {
               isStringLiteral = !isStringLiteral;
           }

           if (Character.isWhitespace(currentChar) && !isStringLiteral) {
               continue;
           }

           if (isOperator(currentChar) && !isStringLiteral) {
               operator = String.valueOf(currentChar);
           } else {
              if (operator == null) {
                  leftOperandBuilder.append(currentChar);
              } else {
                 rightOperandBuilder.append(currentChar);
             }
           }
        }

        if (operator == null || leftOperandBuilder.length() == 0 || rightOperandBuilder.length() == 0) {
            setContent(input);
            return;
        }

        leftOperand = leftOperandBuilder.toString();
        rightOperand = rightOperandBuilder.toString();
        this.operator = operator;

        isFormula = true;
        isCalculated = false;
    }

    private boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '^';
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

        boolean isNumber = isNumeric(this.content);

        if (!isNumber && isString) {
            return true;
        }

        try {
            Double.parseDouble(this.content);
        } catch (NumberFormatException ex) {
            return false;
        }

        return true;
    }

    private boolean isNumeric(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        boolean decimalPointFound = false;
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) {
                if (c == '.' || c == ',') {
                    if (decimalPointFound) {
                        return false;
                    }
                    decimalPointFound = true;
                } else if (c != '-') {
                    return false;
                }
            }
        }
        return true;
    }
}
