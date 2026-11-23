package dev.litematicajade.bridge;

public final class TargetPolicy {
    private TargetPolicy() {}

    /** Equal surfaces favor the schematic so an already placed block can show completion. */
    public static boolean isProjectionClosest(double schematicDistanceSquared, double realDistanceSquared) {
        return Double.isFinite(schematicDistanceSquared) && schematicDistanceSquared >= 0
                && schematicDistanceSquared <= realDistanceSquared + 1.0e-7;
    }
}
