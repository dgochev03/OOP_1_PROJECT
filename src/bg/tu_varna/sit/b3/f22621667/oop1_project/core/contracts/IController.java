package bg.tu_varna.sit.b3.f22621667.oop1_project.core.contracts;

public interface IController {
    void open(String filename);
    void edit(String inputCommand1);
    void print();
    void close();
    void save();
    void saveAs(String saveAsFilePath);
    void help();
    void exit();
}
