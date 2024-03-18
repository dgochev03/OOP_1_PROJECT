package bg.tu_varna.sit.b3.f22621667.oop1_project;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SaveAs implements MenuOption{
    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the filename to save to: ");
        String filename = scanner.nextLine();
        File file = new File(filename);
        Table table = new Table();

        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter writer = new FileWriter(file);
            System.out.println("Enter table data (one row per line, cells separated by commas):");

            while (true) {
                System.out.print("Enter row (leave empty to finish): ");
                String input = scanner.nextLine();
                if (input.isEmpty()) {
                    break;
                }
                writer.write(input + "\n");
                String[] cellContents = input.split(",");
                List<Cell> row = new ArrayList<>();
                for (String cellContent : cellContents) {
                    row.add(new Cell(processQuotedString(cellContent.trim())));
                }
                table.addRow(row);
            }
            writer.close();

            System.out.println("\nTable saved. Current table content:");
            table.printTable();
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    private static String processQuotedString(String content) {
        return content.replace("\\\"", "\"");
    }
}
