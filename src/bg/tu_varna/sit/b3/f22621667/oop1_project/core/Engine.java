package bg.tu_varna.sit.b3.f22621667.oop1_project.core;

import bg.tu_varna.sit.b3.f22621667.oop1_project.core.contracts.IController;
import bg.tu_varna.sit.b3.f22621667.oop1_project.core.contracts.IEngine;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Engine implements IEngine {
    private final IController controller;
    private final Map<Integer, Runnable> options;

    public Engine() {
        this.controller = new Controller();
        this.options = initializeOptions();
    }

    private Map<Integer, Runnable> initializeOptions() {
        Map<Integer, Runnable> options = new HashMap<>();
        options.put(1, controller::open);
        options.put(2, controller::edit);
        options.put(3, controller::print);
        options.put(4, controller::close);
        options.put(5, controller::save);
        options.put(6, controller::saveAs);
        options.put(7, controller::help);
        options.put(0, controller::exit);
        return options;
    }

    @Override
    public void Run() {
        while (true) {
            showMenu();
            int selectedOption = readUserInput();

            if (options.containsKey(selectedOption)) {
                options.get(selectedOption).run();
                pressAnyKeyToContinue();
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
