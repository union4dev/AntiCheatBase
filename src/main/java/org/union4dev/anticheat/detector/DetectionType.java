package org.union4dev.anticheat.detector;

public enum DetectionType {
    BAD_PACKETS("BadPackets"), MISC("Misc");

    private final String display;

    DetectionType(String display) {
        this.display = display;
    }

    public String display() {
        return display;
    }
}
