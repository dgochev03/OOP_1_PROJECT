package bg.tu_varna.sit.b3.f22621667.oop1_project.core.enums;

public enum Commands {
    OPEN,
    EDIT,
    PRINT,
    CLOSE,
    SAVE,
    SAVEAS,
    HELP,
    EXIT;

    public static Commands fromString(String text) {
        for (Commands command : Commands.values()) {
            if (command.name().equalsIgnoreCase(text)) {
                return command;
            }
        }
        return null;
    }
}
