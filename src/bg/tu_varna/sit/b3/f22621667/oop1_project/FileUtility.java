package bg.tu_varna.sit.b3.f22621667.oop1_project;

import java.io.File;

public class FileUtility {
    private Table table;
    private File file;

    public FileUtility(String fileName, Table table) {
        file = new File(fileName);
        this.table = table;
    }

    public boolean exists() {
        if (file == null) {
            return false;
        }

        return file.exists();
    }

    public void openExistingFile() {

    }

    public void createNewFile() {

    }

    public void save() {

    }
}
