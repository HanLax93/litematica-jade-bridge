package dev.litematicajade.bridge;

public enum BuildStatus {
    UNLOADED, MISSING, CORRECT, WRONG_BLOCK, WRONG_STATE;

    public static BuildStatus classify(boolean loaded, boolean air, boolean sameBlock, boolean sameState) {
        if (!loaded) return UNLOADED;
        if (air) return MISSING;
        if (!sameBlock) return WRONG_BLOCK;
        return sameState ? CORRECT : WRONG_STATE;
    }

    public String translationKey() {
        return "litematica_jade_bridge.status." + name().toLowerCase(java.util.Locale.ROOT);
    }
}
