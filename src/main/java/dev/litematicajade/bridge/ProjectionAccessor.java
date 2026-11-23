package dev.litematicajade.bridge;

import fi.dy.masa.litematica.materials.MaterialCache;
import fi.dy.masa.litematica.util.ItemUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import snownee.jade.api.Accessor;
import snownee.jade.api.AccessorImpl;
import snownee.jade.api.BlockAccessor;

/** A schematic-only context. Never inherits real-world NBT or a server connection. */
public final class ProjectionAccessor extends AccessorImpl<BlockHitResult> implements BlockAccessor {
    private final ProjectionTarget target;
    private ItemStack picked;
    private ItemStack material;

    public ProjectionAccessor(ProjectionTarget target, Player player, boolean details) {
        super(target.world(), player, null, target::hit, false, details);
        this.target = target;
    }

    public ProjectionTarget projection() { return target; }
    @Override public Block getBlock() { return target.state().getBlock(); }
    @Override public BlockState getBlockState() { return target.state(); }
    @Override public BlockPos getPosition() { return target.hit().getBlockPos(); }
    @Override public Direction getSide() { return target.hit().getDirection(); }
    @Override public BlockEntity getBlockEntity() { return null; }
    @Override public Object getTarget() { return null; }
    @Override public boolean verifyData(CompoundTag data) { return data.isEmpty(); }
    @Override public Class<? extends Accessor<?>> getAccessorType() { return ProjectionAccessor.class; }

    @Override
    public ItemStack getPickedResult() {
        if (picked == null) {
            picked = ItemUtils.getItemForBlock(target.world(), getPosition(), getBlockState(), false).copy();
        }
        return picked;
    }

    public ItemStack material() {
        if (material == null) {
            material = MaterialCache.getInstance()
                    .getRequiredBuildItemForState(getBlockState(), target.world(), getPosition()).copy();
        }
        return material;
    }
}
