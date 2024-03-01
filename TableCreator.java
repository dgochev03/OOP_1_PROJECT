package bg.tu_varna.sit.b3.f22621667.oop1_project;

import java.io.*;
import java.util.Scanner;

public class TableCreator {
    void createTable() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Въведете име на новият файл: ");
            String filename = scanner.nextLine();
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
            System.out.println("Въведете данните за таблицата (за край натиснете '?'): ");
            String line;
            while (!(line = scanner.nextLine()).equals("?")) {
                writer.write(line);
                writer.newLine();
                if (line.equals("?")) {
                    break;
                }
            }
            writer.close();
            scanner.close();
        } catch (IOException e) {
            System.out.println("Грешка при създаване на файла.");
        }
    }
}
