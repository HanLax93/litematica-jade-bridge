package dev.litematicajade.bridge.gui;

import java.util.List;
import java.util.Optional;

public final class MainMenuPlacement {
    private MainMenuPlacement() {}

    public record Bounds(int x, int y, int width, int height) {
        public boolean overlaps(Bounds other) {
            return x < other.x + other.width + 2 && x + width + 2 > other.x
                    && y < other.y + other.height + 2 && y + height + 2 > other.y;
        }
    }

    /** Prefer the spare rows below Configuration, skipping buttons added by other mods. */
    public static Optional<Bounds> findSlot(int x, int width, int screenHeight, List<Bounds> occupied) {
        for (int y = 52; y + 20 <= screenHeight - 30; y += 22) {
            var candidate = new Bounds(x, y, width, 20);
            if (occupied.stream().noneMatch(candidate::overlaps)) return Optional.of(candidate);
        }
        return Optional.empty();
    }
}
