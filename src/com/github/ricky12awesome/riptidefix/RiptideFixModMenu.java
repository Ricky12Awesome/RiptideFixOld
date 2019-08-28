package com.github.ricky12awesome.riptidefix;

import io.github.prospector.modmenu.api.ModMenuApi;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.gui.entries.BooleanListEntry;
import me.shedaniel.clothconfig2.impl.builders.BooleanToggleBuilder;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.TranslatableText;

import java.util.function.Function;

public class RiptideFixModMenu implements ModMenuApi {

    @Override
    public String getModId() {
        return RiptideFixClient.MOD_ID;
    }

    private String key(String id) {
        return "text." + RiptideFixClient.MOD_ID + "." + id;
    }

    @Override
    public Function<Screen, ? extends Screen> getConfigScreenFactory() {
        return screen -> {
            RiptideFixConfig config = RiptideFixClient.getInstance().getConfig();
            ConfigBuilder builder = ConfigBuilder.create();
            ConfigCategory category = builder.getOrCreateCategory(key("default.title"));
            ConfigEntryBuilder entryBuilder = builder.getEntryBuilder();
            BooleanToggleBuilder isEnabled = entryBuilder.startBooleanToggle(key("default.isEnabled"), config.isEnabled());
            String[] tooltip = new TranslatableText(key("default.isEnabled.tooltip")).asString().split("\n");


            isEnabled.setTooltip(tooltip);
            isEnabled.setSaveConsumer(config::setEnabled);

            builder.setParentScreen(screen);
            builder.setSavingRunnable(RiptideFixClient.getInstance()::saveConfig);

            category.addEntry(isEnabled.buildEntry());

            return builder.build();
        };
    }
}
