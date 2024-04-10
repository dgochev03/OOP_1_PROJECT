package bg.tu_varna.sit.b3.f22621667.oop1_project.core;

import bg.tu_varna.sit.b3.f22621667.oop1_project.core.contracts.IController;
import bg.tu_varna.sit.b3.f22621667.oop1_project.core.contracts.IEngine;

import java.util.Scanner;

public class Engine implements IEngine {
    private final IController controller;

    public Engine() {
        this.controller = new Controller();
    }

    @Override
    public void Run() {
        while (true) {
            showMenu();
            int selectedOption = readUserInput();

            if (isSelectedOptionValid(selectedOption)) {
                executeSelectedOption(selectedOption);
                pressAnyKeyToContinue();
            } else {
                System.out.println("Not a possible option. Choose again.");
            }
        }
    }

    private static void pressAnyKeyToContinue() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Press any key to continue...");
        scanner.nextLine();
    }

    private void executeSelectedOption(int selectedOption) {

        switch (selectedOption) {
            case 0:
                controller.exit();
                break;

            case 1:
                controller.open();
                break;

            case 2:
                controller.edit();
                break;
            case 3:
                controller.print();
                break;

            case 4:
                controller.close();
                break;

            case 5:
                controller.save();
                break;
            case 6:
                controller.saveAs();
                break;

            case 7:
                controller.help();
                break;
        }
    }

    private boolean isSelectedOptionValid(int selectedOption) {
        return selectedOption >= 0 || selectedOption <= controller.getOptionsCount();
    }

    private int readUserInput() {
        System.out.print("Choose an option: ");
        Scanner scanner = new Scanner(System.in);
        String choice = scanner.nextLine();
        int number = -1;

        try {
            number = Integer.parseInt(choice);
        } catch (NumberFormatException ignored) {
            return number;
        }

        return number;
    }

    private void showMenu() {
        StringBuilder menuBuilder = new StringBuilder();
        menuBuilder.append("==== Menu ====\n");

        menuBuilder.append("1. Open file\n");
        menuBuilder.append("2. Edit cell\n");
        menuBuilder.append("3. Print table\n");

        menuBuilder.append("4. Close\n");

        menuBuilder.append("5. Save\n");
        menuBuilder.append("6. Save as\n");

        menuBuilder.append("7. Help\n");

        menuBuilder.append("0. Exit\n");
        System.out.print(menuBuilder);
    }
}
