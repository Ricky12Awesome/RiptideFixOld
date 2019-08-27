package com.github.ricky12awesome.riptidefix;

import net.fabricmc.api.ClientModInitializer;

import java.util.logging.Logger;

public class RiptideFixClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        Logger.getLogger("RiptideFix").info("Initializing...");
    }
}
