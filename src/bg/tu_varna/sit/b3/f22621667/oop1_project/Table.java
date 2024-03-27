package bg.tu_varna.sit.b3.f22621667.oop1_project;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Table {
    private List<List<Cell>> cells;

    public Table() {
        cells = new ArrayList<>();
    }

    public void addRow(List<Cell> row) {
        cells.add(row);
    }

    public List<List<Cell>> getCells() {
        return cells;
    }

    public void printTable() {
        int maxRowLength = 0;
        for (List<Cell> row : cells) {
            maxRowLength = Math.max(maxRowLength, row.size());
        }

        List<Integer> maxColumnLengths = new ArrayList<>();
        for (int i = 0; i < maxRowLength; i++) {
            int maxColumnLength = 0;
            for (List<Cell> row : cells) {
                if (i < row.size()) {
                    maxColumnLength = Math.max(maxColumnLength, row.get(i).getContent().length());
                }
            }
            maxColumnLengths.add(maxColumnLength);
        }

        StringBuilder tableBuilder = new StringBuilder();
        for (List<Cell> row : cells) {
            for (int i = 0; i < maxRowLength; i++) {
                if (i < row.size()) {
                    Cell cell = row.get(i);
                    String content = cell.getContent();
                    if (content.startsWith("=")) {
                        content = evaluateFormula(content.substring(1));
                    }
                    tableBuilder.append(String.format("%-" + (maxColumnLengths.get(i) + 2) + "s", content)).append("|");
                } else {
                    tableBuilder.append(String.format("%-" + (maxColumnLengths.get(i) + 2) + "s", "")).append("|");
                }
            }
            tableBuilder.append("\n");
        }

        System.out.println(tableBuilder.toString());
    }

    public String getCellValue(String cellReference) {
        String[] coordinates = cellReference.split("C");
        int row = Integer.parseInt(coordinates[0].substring(1)) - 1;
        int column = Integer.parseInt(coordinates[1]) - 1;

        if (row >= 0 && row < cells.size() && column >= 0 && column < cells.get(row).size()) {
            Cell cell = cells.get(row).get(column);
            String content = cell.getContent();
            if (content.startsWith("=")) {
                return evaluateFormula(content.substring(1));
            } else {
                return content;
            }
        } else {
            return "0";
        }
    }

    public String evaluateFormula(String expression) {
        List<String> operands = new ArrayList<>(Arrays.asList(expression.split("[+\\-*/^]")));
        List<String> operators = new ArrayList<>(Arrays.asList(expression.split("[^+\\-*/^]+")));
        operators.removeIf(String::isEmpty);

        for (int i = 0; i < operands.size(); i++) {
            if (operands.get(i).startsWith("R")) {
                operands.set(i, getCellValue(operands.get(i)));
            }
        }

        return evaluateOperations(operands, operators);
    }


    private String evaluateOperations(List<String> operands, List<String> operators) {
        for (int i = 0; i < operators.size(); i++) {
            if (operators.get(i).equals("*") || operators.get(i).equals("/")) {
                double result = operate(Double.parseDouble(operands.remove(i)), Double.parseDouble(operands.remove(i)), operators.remove(i));
                operands.add(i, String.valueOf(result));
                i--;
            } else if (operators.get(i).equals("^")) {
                double result = Math.pow(Double.parseDouble(operands.remove(i)), Double.parseDouble(operands.remove(i)));
                operands.add(i, String.valueOf(result));
                operators.remove(i);
                i--;
            }
        }

        while (!operators.isEmpty()) {
            double result = operate(Double.parseDouble(operands.remove(0)), Double.parseDouble(operands.remove(0)), operators.remove(0));
            operands.add(0, String.valueOf(result));
        }

        return operands.get(0);
    }


    private double operate(double operand1, double operand2, String operator) {
        switch (operator) {
            case "+": return operand1 + operand2;
            case "-": return operand1 - operand2;
            case "*": return operand1 * operand2;
            case "/":
                if (operand2 != 0) {
                    return operand1 / operand2;
                } else {
                    throw new ArithmeticException("Division by zero");
                }
            default: throw new IllegalArgumentException("Invalid operator");
        }
    }

}

