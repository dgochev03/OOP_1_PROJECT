package bg.tu_varna.sit.b3.f22621667.oop1_project;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Open implements MenuOption {
    private Open instance;
    private FileWriter writer;
    private Table table = new Table();
    private File file;
    private Scanner scanner;

    public Open() {
    }

    public void testExecute(String fileName) {
        processFileName(fileName);
    }

    public void editTable() {

    }

    @Override
    public void execute() {
        scanner = new Scanner(System.in);
        System.out.print("Enter the filename: ");
        String filename = scanner.nextLine();
        processFileName(filename);
    }

    private void processFileName(String filename) {
        file = new File(filename);

        if (file.exists()) {
            openExistingFile(scanner);
        } else {
            createNewFile(scanner);
        }
    }

    private void openExistingFile(Scanner scanner) {
        try {
            int maxValuesCount = 0;
            int currentMaxValuesCount = 0;

            scanner = new Scanner(file);

            String input;
            while (scanner.hasNextLine()) {
                input = scanner.nextLine();
                String[] cellContents = input.split(",");
                currentMaxValuesCount = cellContents.length;
                maxValuesCount = Math.max(maxValuesCount, currentMaxValuesCount);
            }

            scanner.close();
            scanner = new Scanner(file);

            int rowCount = 0;

            while (scanner.hasNextLine()) {
                input = scanner.nextLine();
                String[] cellContents = input.split(",");
                currentMaxValuesCount = cellContents.length;
                maxValuesCount = Math.max(maxValuesCount, currentMaxValuesCount);

                List<Cell> row = new ArrayList<>();
                for (int i = 0; i < cellContents.length; i++) {
                    String cellContent = cellContents[i];
                    Cell newCell = new Cell(cellContent.trim());
                    row.add(newCell);
                }

                int emptyCellsToAdd = maxValuesCount - row.size();
                for (int i = 0; i < emptyCellsToAdd; i++) {
                    Cell emptyCell = Cell.Empty;
                    row.add(emptyCell);
                }

                table.addRow(row);
                rowCount++;
            }

            //System.out.println("Table:");
            //table.printTable();

        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
    }

    private void createNewFile(Scanner scanner) {
        try {
            file.createNewFile();
            writer = new FileWriter(file, true);

            System.out.println("Enter table data (one row per line, cells separated by commas):");
            while (true) {
                System.out.print("Enter row (leave empty to finish): ");
                String input = scanner.nextLine();
                if (input.isEmpty()) {
                    break;
                }
                writer.write(input + "\n");
                writer.flush();

                String[] cellContents = input.split(",");
                List<Cell> row = new ArrayList<>();
                for (String cellContent : cellContents) {
                    row.add(new Cell(cellContent.trim()));
                }
                table.addRow(row);
            }

            System.out.println("\nTable:");
            table.printTable();

        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    System.out.println("Failed to close writer: " + e.getMessage());
                }
            }
        }
    }

    public void save() {
        if (table == null || file == null) {
            System.out.println("Table will be initialized.");
            return;
        }

        try (FileWriter writer = new FileWriter(file, false)) {
            for (List<Cell> row : table.getCells()) {
                for (int i = 0; i < row.size(); i++) {
                    writer.write(row.get(i).getContent());
                    if (i < row.size() - 1) {
                        writer.write(",");
                    }
                }
                writer.write("\n");
            }
            System.out.println("Table saved successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred while saving the table: " + e.getMessage());
        }
    }

    public void close() {
        if (table == null || file == null) {
            return;
        }

        if (file.exists()) {
            table = null;
            System.out.println("File discarded successfully.");
        } else {
            System.out.println("Failed to discard the file or file does not exist.");
        }
    }
}

