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
        options.put("open", controller::open);
        options.put("edit", controller::edit);
        options.put("print", controller::print);
        options.put("close", controller::close);
        options.put("save", controller::save);
        options.put("saveas", controller::saveAs);
        options.put("help", controller::help);
        options.put("exit", controller::exit);
        return options;
    }

    @Override
    public void Run() {
        while (true) {
            String selectedOption = readUserInput();

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

    private String readUserInput() {
        System.out.print("> ");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
}
