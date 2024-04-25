package bg.tu_varna.sit.b3.f22621667.oop1_project.core;

import bg.tu_varna.sit.b3.f22621667.oop1_project.core.contracts.IController;
import bg.tu_varna.sit.b3.f22621667.oop1_project.core.contracts.IEngine;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Engine implements IEngine {
    private final IController controller;
    private final Map<String, Runnable> options;

    public Engine() {
        this.controller = new Controller();
        this.options = initializeOptions();
    }

    private Map<String, Runnable> initializeOptions() {
        Map<String, Runnable> options = new HashMap<>();

        options.put("open", () -> { });
        options.put("edit", () -> { });
        options.put("print", controller::print);
        options.put("close", controller::close);
        options.put("save", controller::save);
        options.put("saveas", () -> { });
        options.put("help", controller::help);
        options.put("exit", controller::exit);

        return options;
    }

    public void run() {
        while (true) {
            String selectedOption = readUserInput();
            String[] parts = selectedOption.split(" ");

            if (parts.length >= 1 && options.containsKey(parts[0])) {
                switch (parts[0]) {
                    case "open":
                        if (parts.length >= 2) {
                            controller.open(parts[1]);
                        } else {
                            System.out.println("Please provide a file name.");
                        }
                        break;
                    case "saveas":
                        if (parts.length >= 2) {
                            controller.saveAs(parts[1]);
                        } else {
                            System.out.println("Please provide a file path.");
                        }
                        break;
                    case "edit":
                        if (parts.length >= 2) {
                            controller.edit(parts[1]);
                        } else {
                            System.out.println("Please provide edit information.");
                        }
                        break;
                    default:
                        options.get(parts[0]).run();
                        pressAnyKeyToContinue();
                        break;
                }
            } else {
                System.out.println("Not a possible option. Choose again.");
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
