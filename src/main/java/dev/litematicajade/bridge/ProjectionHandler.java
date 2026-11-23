package dev.litematicajade.bridge;

import java.util.List;
import java.util.function.Function;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import snownee.jade.api.AccessorClientHandler;
import snownee.jade.api.IJadeProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.JadeIds;
import snownee.jade.api.config.IWailaConfig;
import snownee.jade.api.theme.IThemeHelper;
import snownee.jade.api.ui.Element;
import snownee.jade.api.ui.JadeUI;

/** Uses Jade layout/theme APIs without running real-world block providers. */
public final class ProjectionHandler implements AccessorClientHandler<ProjectionAccessor> {
    @Override public boolean shouldDisplay(ProjectionAccessor accessor) {
        return BridgeOptions.get(BridgeOptions.ENABLED) && IWailaConfig.get().general().getDisplayBlocks();
    }
    @Override public List<IServerDataProvider<ProjectionAccessor>> shouldRequestData(ProjectionAccessor accessor) {
        return List.of();
    }
    @Override public void requestData(ProjectionAccessor accessor, List<IServerDataProvider<ProjectionAccessor>> providers) {}

    @Override
    public Element getIcon(ProjectionAccessor accessor) {
        ItemStack stack = accessor.getPickedResult();
        if (stack.isEmpty()) stack = accessor.material();
        return stack.isEmpty() ? null : JadeUI.item(stack);
    }

    @Override
    public void gatherComponents(ProjectionAccessor accessor, Function<IJadeProvider, ITooltip> tooltipProvider) {
        var tooltip = tooltipProvider.apply(ProjectionInfoProvider.INSTANCE);
        var title = JadeUI.text(IThemeHelper.get().title(accessor.getBlock().getName())).tag(JadeIds.CORE_OBJECT_NAME);
        if (IWailaConfig.get().overlay().getIconMode() == IWailaConfig.IconMode.INLINE && !accessor.getPickedResult().isEmpty()) {
            tooltip.add(JadeUI.smallItem(accessor.getPickedResult()).tag(JadeIds.CORE_ROOT_ICON));
            tooltip.append(title);
        } else {
            tooltip.add(title);
        }
        tooltip.add(IThemeHelper.get().info(Component.translatable("litematica_jade_bridge.projection")));
        ProjectionInfoProvider.INSTANCE.appendTooltip(tooltip, accessor, IWailaConfig.get().plugin());
        String namespace = BuiltInRegistries.BLOCK.getKey(accessor.getBlock()).getNamespace();
        String modName = FabricLoader.getInstance().getModContainer(namespace)
                .map(container -> container.getMetadata().getName()).orElse(namespace);
        tooltip.add(IThemeHelper.get().modNameElement(modName));
    }
}
