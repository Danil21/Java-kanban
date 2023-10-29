package enums;

public enum Status {
    NEW("New"),
    IN_PROGRESS("in_Progress"),
    DONE("Done");

    private final String translation;

    Status(String translation) {
        this.translation = translation;
    }

    public String toString() {
        return translation;
    }
}
