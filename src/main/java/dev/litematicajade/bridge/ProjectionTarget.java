package dev.litematicajade.bridge;

import fi.dy.masa.litematica.world.WorldSchematic;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public record ProjectionTarget(WorldSchematic world, BlockHitResult hit, BlockState state,
                               BlockState actualState, boolean actualLoaded) {
    public BuildStatus status() {
        return BuildStatus.classify(actualLoaded, actualState.isAir(),
                actualState.is(state.getBlock()), actualState.equals(state));
    }
}
