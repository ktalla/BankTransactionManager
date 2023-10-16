package inheritanceprojectfiles;

public enum Campus {
    NEW_BRUNSWICK(0),
    NEWARK(1),
    CAMDEN(2),
    UNKNOWN(-1); // Default for invalid codes

    private final int code;

    Campus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static Campus fromCode(int code) {
        for (Campus campus : values()) {
            if (campus.code == code) {
                return campus;
            }
        }
        return UNKNOWN; //invalid codes
    }
}