package com.sopernetwork.core.coreutils;

import com.sopernetwork.core.Core;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigUtils {
    public static FileConfiguration GetGlobalConfig() {
        return Core.getInstance().GetGlobalConfig();
    }

    public static FileConfiguration GetLangConfig() {
        return Core.getInstance().GetLangConfig();
    }
}
