package bg.tu_varna.sit.b3.f22621667.oop1_project;

import bg.tu_varna.sit.b3.f22621667.oop1_project.contracts.IController;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class Controller implements IController {
    private Table table;
    private Map<Integer, Runnable> options;
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

        options.put(1, this::open);
        options.put(2, this::edit);
        options.put(3, this::print);
        options.put(4, this::close);
        options.put(5, this::save);
        options.put(6, this::saveAs);
        options.put(7, this::help);
        options.put(0, this::exit);
    }

    public void open() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the filename: ");
        String filename = scanner.nextLine();

        if (filename.isEmpty()) {
            System.out.println("File name can not be empty.");
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

        FileUtility saveAsFileUtility = new FileUtility(saveAsFilePath, table);
        saveAsFileUtility.save();
    }

    public void help() {
        System.out.println("The following commands are supported:");

        System.out.println("open <file>\t\t\t\t\topens <file>");
        System.out.println("edit R<N>C<M> = <VALUE>\t\tedits the value of the cell at specified row and column");
        System.out.println("print\t\t\t\t\t\tprints the table to the console");

        System.out.println("close\t\t\t\t\t\tcloses currently opened file");

        System.out.println("save\t\t\t\t\t\tsaves the currently open file");
        System.out.println("saveas <file>\t\t\t\tsaves the currently open file in <file>");

        System.out.println("help\t\t\t\t\t\tprints this information");

        System.out.println("exit\t\t\t\t\t\texits the program");
    }

    public void exit() {
        System.exit(0);
    }

    private void showEmptyTableMessage() {
        System.out.println("You need FIRST to open a file to do this operation");
    }
}
