package view.old;

/**
 * Enum representing text formatting options using ANSI escape codes.
 * Can be combined using EnumSet for multiple simultaneous formats.
 */
public enum TextFormat {
    BOLD("\033[1m"),
    ITALIC("\033[3m"),
    UNDERLINE("\033[4m");

    private final String code;

    TextFormat(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
