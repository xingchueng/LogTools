package com.meizu.fac.logtools;

import android.content.Context;

import com.meizu.fac.logtools.utils.RunInfo;
import com.meizu.fac.logtools.utils.XmlPull;

import java.io.File;

/**
 * Created by zhangxing on 15-8-14.
 */
public interface LogFile {
    public File createLogFile(String fileName);
    public void readBufferLogFiles();
    public void getSystemInfo(File file);
    public void executeCmdResults(File file);
    public String getLogDir();
    public String getLogPath();
    public RunInfo initRunInfo(Context context);
    public RunInfo getRunInfo();
}
