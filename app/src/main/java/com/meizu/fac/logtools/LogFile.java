package com.meizu.fac.logtools;

/**
 * Created by zhangxing on 15-8-14.
 */
public interface LogFile {
    public String createLogFile();
    public void readBufferLogFiles();
    public void readSystemInfo();
    public void executeCmdResults();
}
