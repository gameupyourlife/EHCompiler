package org.example.semantic;

public class typeCheckResult {
    private final boolean valid;
    private final Object type;

    public typeCheckResult(boolean valid, Object type) {
        this.valid = valid;
        this.type = type;
    }

    public boolean isValid() {
        return valid;
    }

    public Object getType() {
        return type;
    }

    @Override
    public String toString() {
        return "TypeCheckResult{" +
                "valid=" + valid +
                ", type=" + type +
                '}';
    }
}