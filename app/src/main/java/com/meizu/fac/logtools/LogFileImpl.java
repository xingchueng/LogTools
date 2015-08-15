package com.meizu.fac.logtools;

import android.util.Log;

import java.io.DataOutputStream;
import java.io.File;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by zhangxing on 15-8-14.
 */
public class LogFileImpl implements LogFile {
    private String PARENTDIR = "/sdcard/Android/Log_for_logtools";
    private final String TAG = "logtools.LogFileImpl";
    private final boolean DEBUG = true;
    @Override
    public void executeCmdResults() {

    }

    @Override
    public void readSystemInfo() {

    }
/**/
    @Override
    public void readBufferLogFiles() {

    }

    @Override
    public String createLogFile() {
        String path = createLogDir();
        log(path);
        File logFile = new File(path+ "/mainlog.txt");
        if(logFile.exists()){
            return logFile.getName();
        }
        return null;
    }
    /*create the directory for logs.
    * /sdcard/Log/2015-08-14
    * */
    public String createLogDir(){
        String[] month = new String[]{"JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER", "UNDECIMBER"};
        File parentDir = new File(PARENTDIR);
        Calendar calendar = Calendar.getInstance();
        String logPath = calendar.get(Calendar.YEAR) + "_" + month[calendar.get(Calendar.MONTH)] + "_" + calendar.get(Calendar.DAY_OF_MONTH)
                        + "__" + calendar.get(Calendar.HOUR_OF_DAY) + "_" + calendar.get(Calendar.MINUTE) + "_" + calendar.get(Calendar.SECOND);

        File logDir = new File(PARENTDIR + "/" + logPath);
        if(!parentDir.exists()){
            parentDir.mkdirs();
            log("create parent dir of logs :"+ parentDir.exists());
        }

        if(logDir.mkdir()){
                log("create dir for logs :" + logDir.exists());
            }


        return logDir.getPath();
    }

    public void execCommand(String command){
        Process process = null;
        DataOutputStream os = null;
        try {
            process = Runtime.getRuntime().exec("sh");
            os = new DataOutputStream(process.getOutputStream());
            os.write(command.getBytes());
            os.writeBytes("\n");
            os.flush();
            os.writeBytes("exit\n");
            os.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void log(String s){
        if(DEBUG)
            Log.d(TAG, s);
    }
}
