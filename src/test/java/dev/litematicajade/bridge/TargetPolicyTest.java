package dev.litematicajade.bridge;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TargetPolicyTest {
    @Test void projectionInEmptySkyIsSelectable() {
        assertTrue(TargetPolicy.isProjectionClosest(16, Double.POSITIVE_INFINITY));
    }
    @Test void realBlockOrEntityInFrontOccludesProjection() {
        assertFalse(TargetPolicy.isProjectionClosest(16, 9));
        assertTrue(TargetPolicy.isProjectionClosest(9, 16));
    }
    @Test void coincidentSurfacesAllowCompletionInspection() {
        assertTrue(TargetPolicy.isProjectionClosest(16, 16));
        assertTrue(TargetPolicy.isProjectionClosest(16.00000001, 16));
    }
    @Test void malformedDistancesAreNeverSelected() {
        assertFalse(TargetPolicy.isProjectionClosest(Double.NaN, 16));
        assertFalse(TargetPolicy.isProjectionClosest(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
        assertFalse(TargetPolicy.isProjectionClosest(-1, 16));
    }
}
