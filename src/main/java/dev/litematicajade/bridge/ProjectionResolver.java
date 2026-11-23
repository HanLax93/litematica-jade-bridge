package dev.litematicajade.bridge;

import fi.dy.masa.litematica.config.Configs;
import fi.dy.masa.litematica.util.RayTraceUtils;
import fi.dy.masa.litematica.world.SchematicWorldHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.phys.HitResult;
import snownee.jade.api.config.IWailaConfig;

public final class ProjectionResolver {
    // Written once by Jade's getTarget and consumed synchronously by its ray trace callback.
    private static ProjectionTarget current;

    private ProjectionResolver() {}

    public static ProjectionTarget consume(HitResult hit) {
        ProjectionTarget target = current;
        current = null;
        return target != null && target.hit() == hit ? target : null;
    }

    public static HitResult resolve(HitResult realTarget) {
        current = null;
        Minecraft mc = Minecraft.getInstance();
        var camera = mc.getCameraEntity();
        var schematic = SchematicWorldHandler.getSchematicWorld();
        if (mc.level == null || mc.player == null || camera == null || schematic == null
                || !BridgeOptions.get(BridgeOptions.ENABLED)
                || !IWailaConfig.get().general().getDisplayBlocks()) {
            return realTarget;
        }

        double reach = mc.player.blockInteractionRange() + IWailaConfig.get().general().getExtendedReach();
        var trace = Configs.InfoOverlays.INFO_OVERLAYS_TARGET_FLUIDS.getBooleanValue()
                ? RayTraceUtils.getSchematicWorldTraceWrapperIfClosest(mc.level, camera, reach)
                : RayTraceUtils.getSchematicWorldTraceWrapperIfClosestNoFluids(mc.level, camera, reach);
        if (trace == null || trace.getHitType() != RayTraceUtils.RayTraceWrapper.HitType.SCHEMATIC_BLOCK) {
            return realTarget;
        }

        var hit = trace.getBlockHitResult();
        var state = schematic.getBlockState(hit.getBlockPos());
        if (state.isAir() || (state.getBlock() instanceof LiquidBlock
                ? !Configs.Visuals.ENABLE_SCHEMATIC_FLUIDS.getBooleanValue()
                : !Configs.Visuals.ENABLE_SCHEMATIC_BLOCKS.getBooleanValue())) {
            return realTarget;
        }

        var eye = camera.getEyePosition(1.0F);
        double realDistance = realTarget == null || realTarget.getType() == HitResult.Type.MISS
                ? Double.POSITIVE_INFINITY : eye.distanceToSqr(realTarget.getLocation());
        if (!TargetPolicy.isProjectionClosest(eye.distanceToSqr(hit.getLocation()), realDistance)) {
            return realTarget;
        }

        var pos = hit.getBlockPos();
        current = new ProjectionTarget(schematic, hit, state, mc.level.getBlockState(pos), mc.level.isLoaded(pos));
        return hit;
    }
}
