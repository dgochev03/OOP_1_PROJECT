package bg.tu_varna.sit.b3.f22621667.oop1_project.models;

import java.util.ArrayList;
import java.util.List;

public class Table {
    private int rowCount;
    private int colCount;
    private List<List<Cell>> cells;

    public Table() {
        cells = new ArrayList<>();
    }

    public void addRow(List<Cell> cellList) {
        int r = rowCount++;
        colCount = cellList.size();

        for (int c = 0; c < colCount; c++) {
            // TODO Cell Content Integrity

            Cell currentCell = cellList.get(c);
            currentCell.setRow(r);
            currentCell.setCol(c);

            if (!currentCell.checkContentIntegrity()) {
                System.out.printf(
                        "Error: row %d, col %d, %s is unknown data type%n",
                        r + 1,
                        c + 1,
                        currentCell.getContent());
                clear();
                return;
            }
        }

        cells.add(cellList);
        calculateCells();
    }

    private void calculateCells() {
        for (List<Cell> innerList : cells) {
            for (Cell cell : innerList) {
                if (!cell.isFormula()) {
                    continue;
                }

                if (cell.isCalculated()) {
                    continue;
                }

                String leftOp = cell.getLeftOperand();
                String rightOp = cell.getRightOperand();

                String leftOpNumericValue = getCellValue(leftOp);
                String rightOpNumericValue = getCellValue(rightOp);

                if (leftOpNumericValue.isEmpty() || rightOpNumericValue.isEmpty()) {
                    continue;
                }

                String operator = cell.getOperator();
                List<String> operators = new ArrayList<>();
                operators.add(operator);

                List<String> operands = new ArrayList<>();
                operands.add(leftOpNumericValue);
                operands.add(rightOpNumericValue);

                String result = "ERROR";
                if (!rightOpNumericValue.equals("0.0")) {
                    result = evaluateOperations(operands, operators);
                }

                cell.setContent(result);
                cell.setCalculated();
            }
        }
    }

    public void printTable() {
        int maxRowLength = 0;
        for (List<Cell> row : cells) {
            maxRowLength = Math.max(maxRowLength, row.size());
        }

        List<Integer> maxColumnLengths = new ArrayList<>();
        int maxColumnLength = 0;
        for (int i = 0; i < maxRowLength; i++) {
            for (List<Cell> row : cells) {
                if (i < row.size()) {
                    maxColumnLength = Math.max(
                            maxColumnLength,
                            row.get(i).getContent().length());
                }
            }

            maxColumnLengths.add(maxColumnLength);
            maxColumnLength = 0;
        }

        StringBuilder tableBuilder = new StringBuilder();
        for (List<Cell> row : cells) {
            for (int c = 0; c < maxRowLength; c++) {
                if (c < row.size()) {
                    Cell cell = row.get(c);
                    String content = cell.getContent();

                    tableBuilder
                            .append(
                                    String.format("%" + (maxColumnLengths.get(c)) + "s", content))
                            .append(" |");

                    if (c != row.size() - 1) {
                        tableBuilder.append(" ");
                    }
                } else {
                    tableBuilder
                            .append(
                                    String.format("%" + (maxColumnLengths.get(c) + 1) + "s", ""))
                            .append("|");
                }
            }
            tableBuilder.append("\n");
        }

        System.out.println(tableBuilder);
    }

    public String getCellValue(String cellReference) {
        Cell cell = getCell(cellReference);

        if (cell == null) {
            try {
                return String.valueOf(Double.parseDouble(cellReference));
            } catch (NumberFormatException e) {
                return String.valueOf(0);
            }
        }

        return String.valueOf(cell.getNumericValue());
    }

    public Cell getCell(String cellReference) {
        return checkCellReference(cellReference);
    }

    public void setCellValue(String cellReference, String newValue) {
        Cell cell = checkCellReference(cellReference);
        if (cell == null) {
            return;
        }

        cell.setContent(newValue);
    }

    public void clear() {
        for (List<Cell> innerList : cells) {
            for (Cell cell : innerList) {
                cell = null;
            }

            innerList.clear();
        }

        cells.clear();
    }

    private Cell checkCellReference(String cellReference) {
        String[] coordinates = cellReference.split("C");

        if (coordinates.length != 2) {
            return null;
        }

        int row = Integer.parseInt(coordinates[0].substring(1)) - 1;
        int column = Integer.parseInt(coordinates[1]) - 1;

        if (cells.get(row).isEmpty()) {
            return null;
        }

        boolean columnCheck = column >= 0 && column < cells.get(row).size();

        if (!columnCheck) {
            return null;
        }

        return cells.get(row).get(column);
    }

    private String evaluateOperations(
            List<String> operands,
            List<String> operators) {
        for (int i = 0; i < operators.size(); i++) {
            if (operators.get(i).equals("*") || operators.get(i).equals("/")) {
                double result = operate(
                        Double.parseDouble(operands.remove(i)),
                        Double.parseDouble(operands.remove(i)),
                        operators.remove(i));

                operands.add(i, String.valueOf(result));
                i--;
            } else if (operators.get(i).equals("^")) {

                double result = Math.pow(
                        Double.parseDouble(operands.remove(i)),
                        Double.parseDouble(operands.remove(i)));

                operands.add(i, String.valueOf(result));
                operators.remove(i);
                i--;
            }
        }

        while (!operators.isEmpty()) {
            double result = operate(
                    Double.parseDouble(operands.remove(0)),
                    Double.parseDouble(operands.remove(0)),
                    operators.remove(0));
            operands.add(0, String.valueOf(result));
        }

        return operands.get(0);
    }

    private double operate(double operand1, double operand2, String operator) {
        switch (operator) {
            case "+":
                return operand1 + operand2;
            case "-":
                return operand1 - operand2;
            case "*":
                return operand1 * operand2;
            case "/":
                if (operand2 != 0) {
                    return operand1 / operand2;
                } else {
                    //throw new ArithmeticException("Division by zero");
                    System.out.println("Division by zero");
                    return 0;
                }
            default:
                //throw new IllegalArgumentException("Invalid operator");
                System.out.println("Invalid operator");
                return 0;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (List<Cell> innerList : cells) {
            for (int i = 0; i < innerList.size(); i++) {
                Cell cell = innerList.get(i);
                sb.append(cell.getContent());
                if (i < innerList.size() - 1) {
                    sb.append(", ");
                }
            }
            sb.append("\n");
        }

        return sb.toString();
    }
}

