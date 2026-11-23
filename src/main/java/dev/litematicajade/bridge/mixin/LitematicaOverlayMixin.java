package dev.litematicajade.bridge.mixin;

import dev.litematicajade.bridge.BridgeOptions;
import fi.dy.masa.litematica.render.OverlayRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = OverlayRenderer.class, remap = false)
public abstract class LitematicaOverlayMixin {
    @Inject(method = {"renderBlockInfoLines", "renderBlockInfoOverlay"}, at = @At("HEAD"), cancellable = true)
    private void bridge$hideDuplicateBlockInfo(CallbackInfo ci) {
        if (BridgeOptions.suppressNativeHud()) ci.cancel();
    }
}
