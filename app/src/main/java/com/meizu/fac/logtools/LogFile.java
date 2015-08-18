package com.meizu.fac.logtools;

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
}
