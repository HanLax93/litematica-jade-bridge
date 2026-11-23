package dev.litematicajade.bridge.mixin;

import java.util.List;
import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.gui.button.ButtonBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = GuiBase.class, remap = false)
public interface GuiBaseAccessor {
    @Accessor("buttons")
    List<ButtonBase> bridge$getButtons();
}
