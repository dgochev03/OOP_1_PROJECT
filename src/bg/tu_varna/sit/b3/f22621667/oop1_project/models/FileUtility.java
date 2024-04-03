package bg.tu_varna.sit.b3.f22621667.oop1_project.models;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
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
            }

            scanner.close();
            scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                input = scanner.nextLine();
                getMaxValuesCount(input, maxValuesCount);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
    }

    private void getMaxValuesCount(String input, int maxValuesCount) {
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
            getMaxValuesCount(line, maxValuesCount);
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
