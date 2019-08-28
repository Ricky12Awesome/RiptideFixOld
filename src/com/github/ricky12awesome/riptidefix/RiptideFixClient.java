package com.github.ricky12awesome.riptidefix;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class RiptideFixClient implements ClientModInitializer {
    public static final String MOD_ID = "riptide_fix";
    private static RiptideFixClient instance;
    private final Gson gson;
    private Path configPath;
    private RiptideFixConfig config;

    public RiptideFixClient() {
        gson = new GsonBuilder().setPrettyPrinting().create();
    }

    public static RiptideFixClient getInstance() {
        return instance;
    }

    @Override
    public void onInitializeClient() {
        configPath = FabricLoader.getInstance().getConfigDirectory().toPath().resolve(MOD_ID + ".json");
        instance = this;

        if (Files.notExists(configPath)) {
            saveConfig();
        }

        loadConfig();
    }

    public void saveConfig() {
        try (final BufferedWriter writer = Files.newBufferedWriter(configPath)) {
            gson.toJson(config != null ? config : new RiptideFixConfig(), writer);
        } catch (IOException ignore) {
        }
    }

    public void loadConfig() {
        try (final BufferedReader reader = Files.newBufferedReader(configPath)) {
            config = gson.fromJson(reader, RiptideFixConfig.class);
        } catch (IOException ignore) {
        }
    }

    public RiptideFixConfig getConfig() {
        return config;
    }
}
