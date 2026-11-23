package dev.litematicajade.bridge.mixin;

import java.util.Comparator;
import dev.litematicajade.bridge.gui.BridgeSettingsScreen;
import dev.litematicajade.bridge.gui.MainMenuPlacement;
import fi.dy.masa.litematica.gui.GuiMainMenu;
import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.gui.button.ButtonBase;
import fi.dy.masa.malilib.gui.button.ButtonGeneric;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = GuiMainMenu.class, remap = false, priority = 900)
public abstract class LitematicaMainMenuMixin extends GuiBase {
    @Inject(method = "initGui", at = @At("TAIL"))
    private void bridge$addSettingsButton(CallbackInfo ci) {
        var buttons = ((GuiBaseAccessor) this).bridge$getButtons();
        var column = buttons.stream().filter(button -> button.getY() == 30 && button.getX() > 12)
                .min(Comparator.comparingInt(ButtonBase::getX));
        if (column.isEmpty()) return;
        var configButton = column.get();
        var occupied = buttons.stream().map(button -> new MainMenuPlacement.Bounds(
                button.getX(), button.getY(), button.getWidth(), button.getHeight())).toList();
        MainMenuPlacement.findSlot(configButton.getX(), configButton.getWidth(), getScreenHeight(), occupied)
                .ifPresent(bounds -> addButton(new ButtonGeneric(bounds.x(), bounds.y(), bounds.width(), bounds.height(),
                        Component.translatable("litematica_jade_bridge.gui.menu").getString()),
                        (button, mouseButton) -> GuiBase.openGui(new BridgeSettingsScreen(this))));
    }
}
