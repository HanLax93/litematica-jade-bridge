package dev.litematicajade.bridge;

import net.minecraft.world.level.block.Block;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;
import snownee.jade.api.config.ConfigIcon;

@WailaPlugin
public final class LitematicaJadePlugin implements IWailaPlugin {
    @Override
    public void registerClient(IWailaClientRegistration registration) {
        for (var option : BridgeOptions.OPTIONS) {
            registration.addConfig(option.key(), ConfigIcon.none(), option.defaultValue());
            registration.markAsClientFeature(option.key());
        }
        registration.registerBlockComponent(ProjectionInfoProvider.INSTANCE, Block.class);
        registration.registerAccessorHandler(ProjectionAccessor.class, new ProjectionHandler());
        // Run after the core's hidden-block transformation, preserving our distinct accessor type.
        registration.addRayTraceCallback(10000, (hit, accessor, original) -> {
            var target = ProjectionResolver.consume(hit);
            if (target == null) return accessor;
            return new ProjectionAccessor(target, accessor.getPlayer(), registration.isShowDetailsPressed());
        });
    }
}
