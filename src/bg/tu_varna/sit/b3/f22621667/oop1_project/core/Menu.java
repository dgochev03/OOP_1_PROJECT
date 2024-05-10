package bg.tu_varna.sit.b3.f22621667.oop1_project.core;

import bg.tu_varna.sit.b3.f22621667.oop1_project.core.contracts.ControllerOption;
import bg.tu_varna.sit.b3.f22621667.oop1_project.core.enums.Commands;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Menu {
    private final ControllerOption controller;
    private final Map<Commands, Runnable> options;

    public Menu() {
        this.controller = new Controller();
        this.options = initializeOptions();
    }

    private Map<Commands, Runnable> initializeOptions() {
        Map<Commands, Runnable> options = new HashMap<>();

        options.put(Commands.OPEN, () -> { });
        options.put(Commands.EDIT, () -> { });
        options.put(Commands.PRINT, controller::print);
        options.put(Commands.CLOSE, controller::close);
        options.put(Commands.SAVE, controller::save);
        options.put(Commands.SAVEAS, () -> { });
        options.put(Commands.HELP, controller::help);
        options.put(Commands.EXIT, controller::exit);

        return options;
    }

    public void run() {
        while (true) {
            String selectedOption = readUserInput();
            String[] parts = selectedOption.split(" ");

            if (parts.length >= 1) {
                Commands command = Commands.fromString(parts[0]);
                if (command != null && options.containsKey(command)) {
                    switch (command) {
                        case OPEN:
                            if (parts.length >= 2) {
                                controller.open(parts[1]);
                            } else {
                                System.out.println("Please provide a file name.");
                            }
                            break;
                        case SAVEAS:
                            if (parts.length >= 2) {
                                controller.saveAs(parts[1]);
                            } else {
                                System.out.println("Please provide a file path.");
                            }
                            break;
                        case EDIT:
                            if (parts.length >= 2) {
                                controller.edit(parts[1]);
                            } else {
                                System.out.println("Please provide edit information.");
                            }
                            break;
                        default:
                            options.get(command).run();
                            pressAnyKeyToContinue();
                            break;
                    }
                } else {
                    System.out.println("Not a possible option. Choose again.");
                }
            } else {
                System.out.println("Invalid input. Try again.");
            }
        }
    }

    private void pressAnyKeyToContinue() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Press any key to continue...");
        scanner.nextLine();
    }

    private String readUserInput() {
        System.out.print("> ");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
}
