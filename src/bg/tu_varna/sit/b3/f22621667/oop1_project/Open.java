package bg.tu_varna.sit.b3.f22621667.oop1_project;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Open implements MenuOption {
    private FileWriter writer;
    private Table table = new Table();
    private File file;

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the filename: ");
        String filename = scanner.nextLine();
        file = new File(filename);

        if (file.exists()) {
            openExistingFile(scanner);
        } else {
            createNewFile(scanner);
        }
    }

    private void openExistingFile(Scanner scanner) {
        try {
            scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String input = scanner.nextLine();
                String[] cellContents = input.split(",");
                List<Cell> row = new ArrayList<>();
                for (String cellContent : cellContents) {
                    row.add(new Cell(cellContent.trim()));
                }
                table.addRow(row);
            }
            System.out.println("Table:");
            table.printTable();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
    }

    private void createNewFile(Scanner scanner) {
        try {
            file.createNewFile();
            writer = new FileWriter(file, true); // Append mode

            System.out.println("Enter table data (one row per line, cells separated by commas):");
            while (true) {
                System.out.print("Enter row (leave empty to finish): ");
                String input = scanner.nextLine();
                if (input.isEmpty()) {
                    break;
                }
                writer.write(input + "\n");
                writer.flush(); // Flush after each line to ensure data is written

                String[] cellContents = input.split(",");
                List<Cell> row = new ArrayList<>();
                for (String cellContent : cellContents) {
                    row.add(new Cell(processQuotedString(cellContent.trim())));
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

    private static String processQuotedString(String content) {
        return content.replace("\\\"", "\"");
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
        if (file.exists() && file.delete()) {
            System.out.println("File discarded successfully.");
        } else {
            System.out.println("Failed to discard the file or file does not exist.");
        }
    }

}
