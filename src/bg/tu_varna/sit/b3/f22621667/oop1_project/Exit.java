package bg.tu_varna.sit.b3.f22621667.oop1_project;

public class Exit implements MenuOption{
    @Override
    public void execute() {
        System.out.println("Exiting the program...");
        System.exit(0);
    }
}
