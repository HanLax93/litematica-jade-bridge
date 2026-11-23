package dev.litematicajade.bridge;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BuildStatusTest {
    @Test void unloadedChunkIsNotReportedAsMissing() {
        assertEquals(BuildStatus.UNLOADED, BuildStatus.classify(false, true, false, false));
    }
    @Test void airIsMissing() {
        assertEquals(BuildStatus.MISSING, BuildStatus.classify(true, true, false, false));
    }
    @Test void exactStateIsComplete() {
        assertEquals(BuildStatus.CORRECT, BuildStatus.classify(true, false, true, true));
    }
    @Test void orientationOrWaterloggingMismatchIsDistinctFromWrongMaterial() {
        assertEquals(BuildStatus.WRONG_STATE, BuildStatus.classify(true, false, true, false));
        assertEquals(BuildStatus.WRONG_BLOCK, BuildStatus.classify(true, false, false, false));
    }
}
