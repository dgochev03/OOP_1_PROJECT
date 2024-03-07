package bg.tu_varna.sit.b3.f22621667.oop1_project;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Menu {
    private Map<String, MenuOption> options = new HashMap<>();

    public Menu() {
        options.put("0", new Exit());
        options.put("1", new Open());
        options.put("2", new Close());
        options.put("3", new Safe());
        options.put("4", new SaveAs());
        options.put("5", new Help());
    }

    public void display() {
        StringBuilder menu = new StringBuilder();
        menu.append("1. Open file\n");
        menu.append("2. Close file\n");
        menu.append("3. Safe\n");
        menu.append("4. Save as\n");
        menu.append("5. Help\n");
        menu.append("0. Exit\n");
        System.out.println(menu.toString());


        System.out.println("Choose an option:");
        Scanner scanner = new Scanner(System.in);
        String choice = scanner.nextLine();

        if (options.containsKey(choice)) {
            MenuOption chosenOption = options.get(choice);
            chosenOption.execute();
        } else {
            System.out.println("Not a possible option. Choose again.");
            display();
        }
    }
}
