package dev.litematicajade.bridge.mixin;

import dev.litematicajade.bridge.ProjectionResolver;
import net.minecraft.world.phys.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import snownee.jade.overlay.RayTracing;

@Mixin(value = RayTracing.class, remap = false)
public abstract class JadeRayTracingMixin {
    @Inject(method = "getTarget", at = @At("RETURN"), cancellable = true)
    private void bridge$resolveProjection(CallbackInfoReturnable<HitResult> cir) {
        cir.setReturnValue(ProjectionResolver.resolve(cir.getReturnValue()));
    }
}
