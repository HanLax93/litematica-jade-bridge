package dev.litematicajade.bridge.gui;

import java.util.List;
import dev.litematicajade.bridge.BridgeOptions;
import fi.dy.masa.malilib.config.options.ConfigBoolean;
import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.gui.GuiConfigsBase;
import fi.dy.masa.malilib.gui.button.ButtonGeneric;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import snownee.jade.api.config.IWailaConfig;

/** MaLiLib supplies scrolling, search, boolean controls and per-option reset buttons. */
public final class BridgeSettingsScreen extends GuiConfigsBase {
    private final List<ConfigBoolean> options;

    public BridgeSettingsScreen(Screen parent) {
        super(10, 50, BridgeOptions.MOD_ID, parent, "litematica_jade_bridge.gui.title");
        setParent(parent);
        options = BridgeOptions.OPTIONS.stream().map(option -> {
            var config = new ConfigBoolean(option.key().getPath(), option.defaultValue());
            String name = Component.translatable(option.translationKey()).getString();
            config.setPrettyName(name);
            config.setTranslatedName(name);
            config.setBooleanValue(BridgeOptions.get(option.key()));
            return config;
        }).toList();
    }

    @Override
    public void initGui() {
        super.initGui();
        addLabel(10, 30, -1, 12, 0xFFAAAAAA,
                Component.translatable("litematica_jade_bridge.gui.hint").getString());
        addButton(new ButtonGeneric(10, getScreenHeight() - 26, 100, 20,
                Component.translatable("gui.back").getString()), (button, mouseButton) -> GuiBase.openGui(getParent()));
    }

    @Override
    public List<ConfigOptionWrapper> getConfigs() {
        return options == null ? List.of() : ConfigOptionWrapper.createFor(options);
    }

    @Override
    protected int getConfigWidth() { return 90; }

    @Override
    protected void buildConfigSwitcher() {
        // This is the bridge's dedicated screen; the parent is Litematica's main menu.
    }

    @Override
    protected void onSettingsChanged() {
        var config = IWailaConfig.get();
        for (int i = 0; i < options.size(); i++) {
            config.plugin().set(BridgeOptions.OPTIONS.get(i).key(), options.get(i).getBooleanValue());
        }
        config.save();
    }
}
