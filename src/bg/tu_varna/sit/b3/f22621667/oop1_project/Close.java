package bg.tu_varna.sit.b3.f22621667.oop1_project;

public class Close implements MenuOption{
    @Override
    public void execute() {
        Open open = new Open();
        open.close();
    }
}
