package dev.litematicajade.bridge;

import java.util.List;
import dev.litematicajade.bridge.gui.MainMenuPlacement;
import dev.litematicajade.bridge.gui.MainMenuPlacement.Bounds;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MainMenuPlacementTest {
    @Test void leavesConfigurationAndContainerFillerButtonsInPlace() {
        var occupied = List.of(new Bounds(120, 30, 90, 20), new Bounds(120, 52, 90, 20));
        assertEquals(new Bounds(120, 74, 90, 20), MainMenuPlacement.findSlot(120, 90, 240, occupied).orElseThrow());
    }

    @Test void usesFirstFreeRowWhenMultipleAddonsAreInstalled() {
        var occupied = List.of(new Bounds(120, 52, 90, 20), new Bounds(120, 74, 90, 20));
        assertEquals(96, MainMenuPlacement.findSlot(120, 90, 240, occupied).orElseThrow().y());
    }

    @Test void otherColumnsDoNotOccupyOurColumn() {
        assertEquals(52, MainMenuPlacement.findSlot(120, 90, 240, List.of(new Bounds(12, 52, 90, 20))).orElseThrow().y());
    }

    @Test void doesNotPlaceButtonsOverTheBottomToolbar() {
        assertTrue(MainMenuPlacement.findSlot(120, 90, 90, List.of()).isEmpty());
    }
}
