package bg.tu_varna.sit.b3.f22621667.oop1_project.core;

import bg.tu_varna.sit.b3.f22621667.oop1_project.core.contracts.IController;
import bg.tu_varna.sit.b3.f22621667.oop1_project.models.Cell;
import bg.tu_varna.sit.b3.f22621667.oop1_project.models.FileUtility;
import bg.tu_varna.sit.b3.f22621667.oop1_project.models.Table;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Controller implements IController {
    private Table table;
    private Map<String, Runnable> options;
    private FileUtility fileUtility;

    public Controller() {
        initializeOptions();
    }

    public int getOptionsCount() {
        if (options == null) {
            return 0;
        }

        return options.size();
    }

    private void initializeOptions() {
        if (options == null) {
            options = new HashMap<>();
        }

        options.put("open", this::open);
        options.put("edit", this::edit);
        options.put("print", this::print);
        options.put("close", this::close);
        options.put("save", this::save);
        options.put("saveas", this::saveAs);
        options.put("help", this::help);
        options.put("exit", this::exit);
    }

    public void open() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the filename: ");
        String filename = scanner.nextLine();

        if (filename.isEmpty()) {
            System.out.println("File name can not be empty.");
            return;
        }

        if (!filename.endsWith(".txt")) {
            System.out.println("Invalid file format. File name must end with '.txt'.");
            return;
        }

        if (table == null) {
            table = new Table();
        }

        fileUtility = new FileUtility(filename, table);
        if (fileUtility.exists()) {
            fileUtility.openExistingFile();
        } else {
            fileUtility.createNewFile();
        }
    }

    public void edit() {
        if (table == null) {
            showEmptyTableMessage();
            return;
        }

        Scanner editCommandScanner = new Scanner(System.in);
        System.out.println("Enter with the following format R<N>C<M> = <VALUE>:");
        System.out.print("Please enter command: ");
        String inputCommand = editCommandScanner.nextLine();

        String[] parts = inputCommand.split("=");
        if (parts.length != 2) {
            System.out.println("Invalid edit command.");
            System.out.println("FORMAT: R<N>C<M> = <VALUE>");
            return;
        }

        String cellReference = parts[0].trim();
        String newValue = parts[1].trim();

        if (!cellReference.startsWith("R") || cellReference.length() < 4 || !cellReference.contains("C")) {
            System.out.println("Invalid cell reference format");
            return;
        }

        Cell oldCell = table.getCell(cellReference);

        if (oldCell == null) {
            System.out.println("Cell coordinates not found");
            return;
        }

        String oldValue = oldCell.getContent();
        table.setCellValue(cellReference, newValue);

        String message = String.format(
                "Cell at %s with old value %s is edited with new value %s",
                cellReference,
                oldValue,
                oldCell.getContent());
        System.out.println(message);
    }

    public void print() {
        if (table == null) {
            showEmptyTableMessage();
            return;
        }

        table.printTable();
    }

    public void close() {
        if (table == null) {
            showEmptyTableMessage();
            return;
        }

        table.clear();
        table = null;
        fileUtility = null;
    }

    public void save() {
        if (table == null) {
            showEmptyTableMessage();
            return;
        }

        fileUtility.save();
    }

    public void saveAs() {
        if (table == null) {
            showEmptyTableMessage();
            return;
        }

        Scanner fileNameScanner = new Scanner(System.in);
        System.out.print("Enter file name to save the table: ");
        String saveAsFilePath = fileNameScanner.nextLine();

        if (!saveAsFilePath.endsWith(".txt")) {
            System.out.println("Invalid file format. File name must end with '.txt'.");
            return;
        }

        FileUtility saveAsFileUtility = new FileUtility(saveAsFilePath, table);
        saveAsFileUtility.save();
    }

    public void help() {
        System.out.println("The following commands are supported:");
        System.out.println("open \t\t\t\topens file or crete it if it does not exits");
        System.out.println("edit \t\t\t\tedits the value of the cell at specified row and column R<N>C<M> = <VALUE>");
        System.out.println("print\t\t\t\tprints the table to the console");
        System.out.println("close\t\t\t\tcloses currently opened file");
        System.out.println("save\t\t\t\tsaves the currently open file");
        System.out.println("saveas \t\t\t\tsaves the currently open file in a specified directory and name");
        System.out.println("help\t\t\t\tprints this information");
        System.out.println("exit\t\t\t\texits the program");
    }

    public void exit() {
        System.exit(0);
    }

    private void showEmptyTableMessage() {
        System.out.println("You need FIRST to open a file to do this operation");
    }
}
