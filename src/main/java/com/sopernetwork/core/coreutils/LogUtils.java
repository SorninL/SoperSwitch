package com.sopernetwork.core.coreutils;


import com.sopernetwork.core.Core;

import java.util.logging.Logger;

public class LogUtils {
    public static Logger GetLogger() {
        return Core.getInstance().getCoreLogger();
    }
}
