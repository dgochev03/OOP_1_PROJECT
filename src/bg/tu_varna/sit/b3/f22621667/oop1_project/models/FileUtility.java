package bg.tu_varna.sit.b3.f22621667.oop1_project.models;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileUtility {
    private Table table;
    private File file;

    public FileUtility(String fileName, Table table) {
        file = new File(fileName);
        this.table = table;
    }

    public boolean exists() {
        if (file == null) {
            return false;
        }

        return file.exists();
    }

    public void openExistingFile() {
        try {
            int maxValuesCount = 0;
            int currentMaxValuesCount = 0;

            Scanner scanner = new Scanner(file);

            String input;
            while (scanner.hasNextLine()) {
                input = scanner.nextLine();
                String[] cellContents = input.split(",");
                currentMaxValuesCount = cellContents.length;
                maxValuesCount = Math.max(maxValuesCount, currentMaxValuesCount);

                for (int i = 0; i < cellContents.length; i++) {
                    Cell newCell = new Cell(cellContents[i].trim());
                    if (!newCell.checkContentIntegrity()) {
                        cellContents[i] = "\"" + cellContents[i].trim() + "\"";
                    }
                }
                input = String.join(",", cellContents);
                addRowToTable(input, maxValuesCount);
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
    }

    private void addRowToTable(String input, int maxValuesCount) {
        int currentMaxValuesCount;
        String[] cellContents = input.split(",");
        currentMaxValuesCount = cellContents.length;
        maxValuesCount = Math.max(maxValuesCount, currentMaxValuesCount);

        List<Cell> row = new ArrayList<>();
        for (String cellContent : cellContents) {
            Cell newCell = new Cell(cellContent.trim());
            row.add(newCell);
        }

        int emptyCellsToAdd = maxValuesCount - row.size();
        for (int i = 0; i < emptyCellsToAdd; i++) {
            Cell emptyCell = Cell.Empty;
            row.add(emptyCell);
        }

        table.addRow(row);
    }

    public void createNewFile() {
        Scanner scanner = new Scanner(System.in);

        int maxValuesCount = 0;
        int currentMaxValuesCount = 0;

        List<String> readLines = new ArrayList<>();
        System.out.println("Enter table data (one row per line, cells separated by commas):");
        while (true) {
            System.out.print("Enter row (leave empty to finish): ");
            String input = scanner.nextLine();
            if (input.isEmpty()) {
                break;
            }

            readLines.add(input);

            String[] cellContents = input.split(",");
            currentMaxValuesCount = cellContents.length;
            maxValuesCount = Math.max(maxValuesCount, currentMaxValuesCount);
        }

        for (String line : readLines) {
            addRowToTable(line, maxValuesCount);
        }
    }

    public void save() {
        try {
            FileWriter writer = new FileWriter(file.getAbsoluteFile());
            writer.write(table.toString());
            writer.close();

            System.out.println("File saved successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred while saving the file.");
        }
    }
}
