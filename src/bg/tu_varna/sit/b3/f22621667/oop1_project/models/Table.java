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
            Cell currentCell = cellList.get(c);
            currentCell.setRow(r);
            currentCell.setCol(c);

            if (!currentCell.checkContentIntegrity()) {
                System.out.printf(
                        "Error: row %d, col %d, %s is unknown data type",
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
                String operator = cell.getOperator();

                String leftOpNumericValue = getCellValue(leftOp);
                String rightOpNumericValue = getCellValue(rightOp);

                if (leftOpNumericValue.isEmpty() || rightOpNumericValue.isEmpty()) {
                    continue;
                }

                String result;
                double resultDouble = operate(leftOpNumericValue, rightOpNumericValue, operator);
                if (resultDouble == (int) resultDouble) {
                    int resultInt = (int) resultDouble;
                    result = Integer.toString(resultInt);
                }else{
                    result = Double.toString(resultDouble);
                }
                if (rightOpNumericValue.equals("0.0") && operator.equals("/")) {
                    result = "ERROR";
                }

                cell.setContent(result);
                cell.setCalculated();
            }
        }
    }

    public void printTable() {
        List<Integer> maxColumnLengths = new ArrayList<>();
        int maxColumnLength = 0;
        for (int i = 0; i < colCount; i++) {
            for (List<Cell> row : cells) {
                if (i < row.size()) {
                    maxColumnLength = Math.max(maxColumnLength, row.get(i).getContent().length());
                }
            }

            maxColumnLengths.add(maxColumnLength);
            maxColumnLength = 0;
        }

        StringBuilder tableBuilder = new StringBuilder();
        for (List<Cell> row : cells) {
            for (int c = 0; c < colCount; c++) {
                if (c < row.size()) {
                    Cell cell = row.get(c);
                    String content = cell.getContent();
                    tableBuilder.append(String.format("%" + (maxColumnLengths.get(c)) + "s", content)).append(" | ");
                } else {
                    tableBuilder.append(String.format("%" + (maxColumnLengths.get(c) + 1) + "s", "")).append("| ");
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
        try {
            String[] coordinates = cellReference.split("C");

            if (coordinates.length != 2) {
                return null;
            }

            int row = Integer.parseInt(coordinates[0].substring(1)) - 1;
            int column = Integer.parseInt(coordinates[1]) - 1;

            if (row < 0 || row >= rowCount || column < 0 || column >= colCount) {
                return null;
            }

            if (cells.size() <= row || cells.get(row).isEmpty() || column >= cells.get(row).size()) {
                return null;
            }

            return cells.get(row).get(column);
        } catch (NumberFormatException | IndexOutOfBoundsException | NullPointerException e) {
            System.out.println("ERROR: wrong cell coordinate");
            return null;
        }
    }


    private double operate(String leftOperand, String rightOperand, String operator) {
        double operand1 = Double.parseDouble(leftOperand);
        double operand2 = Double.parseDouble(rightOperand);
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
                    return 0;
                }
            case "^":
                return Math.pow(operand1, operand2);
            default:
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
