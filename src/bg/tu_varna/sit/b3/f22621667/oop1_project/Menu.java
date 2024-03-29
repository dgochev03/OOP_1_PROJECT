package bg.tu_varna.sit.b3.f22621667.oop1_project;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Menu {
    private ArrayList<MenuOption> optionsArrayList = new ArrayList<>();

    public Menu() {
        optionsArrayList.add(new Exit());  // 0

        optionsArrayList.add(new Open()); // 1
        optionsArrayList.add(new Close()); // 2


        optionsArrayList.add(new Save()); // 3
        optionsArrayList.add(new SaveAs()); // 4

        optionsArrayList.add(new Help());  // 5
    }

    public void display() throws IOException {
        StringBuilder menu = new StringBuilder();
        menu.append("1. Open file\n");
        menu.append("2. Close file\n");
        menu.append("3. Save\n");
        menu.append("4. Save as\n");
        menu.append("5. Help\n");
        menu.append("0. Exit\n");
        System.out.println(menu);

        System.out.print("Choose an option: ");
        Scanner scanner = new Scanner(System.in);
        String choice = scanner.nextLine();
        int number = -1;

        try {
            number = Integer.parseInt(choice);
        } catch (NumberFormatException ignored) {
        }

        boolean isNumberCorrect = number > 0;
        boolean isInBounds = number < optionsArrayList.size() - 1;

        if (isNumberCorrect && isInBounds) {
            MenuOption chosenOption = optionsArrayList.get(number);
            chosenOption.execute();
        } else {
            System.out.println("Not a possible option. Choose again.");
            display();
        }
    }
}