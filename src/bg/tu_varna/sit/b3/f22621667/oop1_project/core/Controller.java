package bg.tu_varna.sit.b3.f22621667.oop1_project.core;

import bg.tu_varna.sit.b3.f22621667.oop1_project.core.contracts.ControllerOption;
import bg.tu_varna.sit.b3.f22621667.oop1_project.models.Cell;
import bg.tu_varna.sit.b3.f22621667.oop1_project.models.FileUtility;
import bg.tu_varna.sit.b3.f22621667.oop1_project.models.Table;

public class Controller implements ControllerOption {
    private Table table;
    private FileUtility fileUtility;

    @Override
    public void open(String filename) {
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

    @Override
    public void edit(String inputCommand) {
        if (table == null) {
            showEmptyTableMessage();
            return;
        }

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

    @Override
    public void print() {
        if (table == null) {
            showEmptyTableMessage();
            return;
        }

        table.printTable();
    }

    @Override
    public void close() {
        if (table == null) {
            showEmptyTableMessage();
            return;
        }

        table.clear();
        table = null;
        fileUtility = null;
    }

    @Override
    public void save() {
        if (table == null) {
            showEmptyTableMessage();
            return;
        }

        fileUtility.save();
    }

    @Override
    public void saveAs(String saveAsFilePath) {
        if (table == null) {
            showEmptyTableMessage();
            return;
        }

        if (!saveAsFilePath.endsWith(".txt")) {
            System.out.println("Invalid file format. File name must end with '.txt'.");
            return;
        }

        FileUtility saveAsFileUtility = new FileUtility(saveAsFilePath, table);
        saveAsFileUtility.save();
    }

    @Override
    public void help() {
        StringBuilder sb = new StringBuilder();
        sb.append("The following commands are supported:").append("\n");
        sb.append("open <file>                opens <file>").append("\n");
        sb.append("edit R<N>C<M> = <VALUE>    edits the value of the cell at specified row and column").append("\n");
        sb.append("print                      prints the table to the console").append("\n");
        sb.append("close                      closes currently opened file").append("\n");
        sb.append("save                       saves the currently open file").append("\n");
        sb.append("saveas <file>              saves the currently open file in <file>").append("\n");
        sb.append("help                       prints this information").append("\n");
        sb.append("exit                       exits the program").append("\n");
        String help = sb.toString();
        System.out.println(help);
    }

    @Override
    public void exit() {
        System.exit(0);
    }

    private void showEmptyTableMessage() {
        System.out.println("You need FIRST to open a file to do this operation");
    }
}
