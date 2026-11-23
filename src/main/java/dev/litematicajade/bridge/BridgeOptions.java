package dev.litematicajade.bridge;

import java.util.List;
import net.minecraft.resources.Identifier;
import snownee.jade.api.config.IWailaConfig;

public final class BridgeOptions {
    public static final String MOD_ID = "litematica_jade_bridge";
    public static final Identifier ENABLED = id("enabled");
    public static final Identifier HIDE_NATIVE_HUD = id("hide_native_hud");
    public static final Identifier BLOCK_ID = id("block_id");
    // New keys intentionally give 0.1.0 installations the new compact defaults once.
    public static final Identifier COMPLETION = id("show_completion");
    public static final Identifier PROPERTIES = id("show_properties");
    public static final Identifier POSITION = id("show_position");
    public static final Identifier ACTUAL_BLOCK = id("show_actual_block");
    public static final Identifier MATERIAL = id("material");

    public static final List<Option> OPTIONS = List.of(
            new Option(ENABLED, true), new Option(HIDE_NATIVE_HUD, true),
            new Option(BLOCK_ID, true), new Option(MATERIAL, true),
            new Option(COMPLETION, false), new Option(POSITION, false),
            new Option(ACTUAL_BLOCK, false), new Option(PROPERTIES, false));

    public record Option(Identifier key, boolean defaultValue) {
        public String translationKey() {
            return "config.jade.plugin_" + MOD_ID + "." + key.getPath();
        }
    }

    private BridgeOptions() {}

    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }

    public static boolean get(Identifier key) {
        return IWailaConfig.get().plugin().get(key);
    }

    public static boolean suppressNativeHud() {
        var general = IWailaConfig.get().general();
        return get(ENABLED) && get(HIDE_NATIVE_HUD)
                && general.shouldDisplayTooltip() && general.getDisplayBlocks()
                && snownee.jade.overlay.OverlayRenderer.shouldShow();
    }
}
