package com.meizu.fac.logtools;

/**
 * Created by zhangxing on 15-8-15.
 */
public class Commands {
    private static String[] cmds = new String[]{
            "/system/bin/getprop",
            "/system/bin/ps",
            "/system/bin/logcat -v threadtime -d",
            "/system/bin/logcat -v threadtime -b radio -d",
            "/system/bin/logcat -v threadtime -b events -d",
            "/system/bin/dmesg",
            "/system/bin/dumpsys power",
            "/system/bin/dumpsys alarm",
            "/system/bin/dumpsys battery",
            "/system/bin/dumpsys batteryinfo",
            "/system/bin/dumpsys cpuinfo",
            "/system/bin/dumpsys meminfo",
            "/system/bin/dumpsys netpolicy",
            "/system/bin/dumpsys netstats --full --uid",
            "/system/bin/dumpsys SurfaceFlinger",
            "/system/bin/dumpsys wifi",
            "/system/bin/ps -t",
            "/system/bin/cat /sys/devices/platform/soc-audio.0/reg_program",
            "/system/bin/cat /sys/fs/pstore/console-ramoops",
            "/system/bin/cat /sys/fs/pstore/dmesg-ramoops-0",
            "/system/bin/cat /sys/fs/pstore/dmesg-ramoops-1",
            "/system/bin/cat /sys/fs/pstore/ftrace-ramoops"
    };


    public static String[] getCommands(){
        return cmds;
    }
}
