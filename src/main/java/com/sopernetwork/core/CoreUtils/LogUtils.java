package com.sopernetwork.core.CoreUtils;


import com.sopernetwork.core.Core;

import java.util.logging.Logger;

public class LogUtils {
    public static Logger GetLogger() {
        return Core.getInstance().getCoreLogger();
    }
}
