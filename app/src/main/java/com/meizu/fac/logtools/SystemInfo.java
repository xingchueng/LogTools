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
            "/data/anr/traces.txt",
            "/data/ril.log",
            "/data/ril_miss.log",
            "/data/ril_sn.log",
            "/data/battery_soc.txt",
            "/cache/recovery/last_install",
            "/cache/recovery/last_log",
    };

    public static String[] getInfoFiles(){
        return infoFiles;
    }
}
