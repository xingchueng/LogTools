package com.meizu.fac.logtools;

/**
 * Created by zhangxing on 15-8-15.
 */
public class SystemInfo {
    private static String[] infoFiles = new String[]{
            "/proc/version",
            "/proc/cpuinfo",
            "/proc/meminfo",
            "/proc/last_kmsg",
            "/proc/reset_reason",
    };

    public static String[] getInfoFiles(){
        return infoFiles;
    }
}
