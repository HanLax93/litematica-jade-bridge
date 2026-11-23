package dev.litematicajade.bridge;

import java.util.Comparator;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.state.properties.Property;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.theme.IThemeHelper;
import snownee.jade.api.ui.JadeUI;

public enum ProjectionInfoProvider implements IBlockComponentProvider {
    INSTANCE;

    @Override public Identifier getUid() { return BridgeOptions.id("projection_info"); }
    @Override public boolean isRequired() { return true; }
    @Override public int getDefaultPriority() { return -10000; }

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
        if (!(accessor instanceof ProjectionAccessor projection)) return;
        var theme = IThemeHelper.get();
        if (config.get(BridgeOptions.COMPLETION)) {
            var status = projection.projection().status();
            var statusText = Component.translatable(status.translationKey());
            tooltip.add(Component.translatable("litematica_jade_bridge.completion", switch (status) {
                case CORRECT -> theme.success(statusText);
                case MISSING, UNLOADED -> theme.warning(statusText);
                case WRONG_BLOCK, WRONG_STATE -> theme.danger(statusText);
            }));
        }

        if (config.get(BridgeOptions.BLOCK_ID)) {
            tooltip.add(theme.info(BuiltInRegistries.BLOCK.getKey(accessor.getBlock()).toString()));
        }
        if (config.get(BridgeOptions.MATERIAL)) {
            var material = projection.material();
            tooltip.add(Component.translatable("litematica_jade_bridge.material",
                    material.isEmpty() ? Component.translatable("litematica_jade_bridge.no_material")
                            : Component.translatable("litematica_jade_bridge.item_count", material.getHoverName(), material.getCount())));
        }
        if (config.get(BridgeOptions.POSITION)) {
            var pos = accessor.getPosition();
            tooltip.add(Component.translatable("litematica_jade_bridge.position", pos.getX(), pos.getY(), pos.getZ()));
        }
        if (config.get(BridgeOptions.ACTUAL_BLOCK)) {
            tooltip.add(Component.translatable("litematica_jade_bridge.actual_block",
                    projection.projection().actualLoaded() ? projection.projection().actualState().getBlock().getName()
                            : Component.translatable(BuildStatus.UNLOADED.translationKey())));
        }
        if (config.get(BridgeOptions.PROPERTIES)) {
            accessor.getBlockState().getProperties().stream().sorted(Comparator.comparing(Property::getName))
                    .forEach(property -> addProperty(tooltip, accessor, property));
        }
    }

    private static <T extends Comparable<T>> void addProperty(ITooltip tooltip, BlockAccessor accessor, Property<T> property) {
        String value = property.getName(accessor.getBlockState().getValue(property));
        tooltip.add(Component.translatable("litematica_jade_bridge.property",
                translated("property", property.getName()), translated("value", value)));
    }

    private static Component translated(String group, String value) {
        String key = "litematica_jade_bridge." + group + "." + value;
        return JadeUI.hasTranslation(key) ? Component.translatable(key) : Component.literal(value);
    }
}
