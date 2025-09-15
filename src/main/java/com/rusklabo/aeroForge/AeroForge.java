package com.rusklabo.aeroForge;

import org.bukkit.plugin.java.JavaPlugin;

public final class AeroForge extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        // イベントリスナーを登録
        getServer().getPluginManager().registerEvents(new AirshipDetector(this), this);

        // 設定ファイルを保存
        saveDefaultConfig();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
