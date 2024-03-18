package bg.tu_varna.sit.b3.f22621667.oop1_project;

import java.io.IOException;

public class Save implements MenuOption {
    @Override
    public void execute() throws IOException {
        Open open=new Open();
        open.save();
        System.out.println("File is saved successfully");
    }
}
