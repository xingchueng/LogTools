package com.meizu.fac.logtools;

import android.content.Context;
import android.util.Log;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import com.meizu.fac.logtools.utils.*;

import org.xmlpull.v1.XmlPullParserException;

/**
 * Created by zhangxing on 15-8-14.
 */
public class LogFileImpl implements LogFile {
    private String PARENTDIR = "/sdcard/Android/Log_for_logtools";
    private final String TAG = "logtools";
    private final boolean DEBUG = true;
    private File mLogFile = null;
    private RunInfo runInfo;
    private Context context;

    public LogFileImpl(Context context) {
        this.context = context;
        this.runInfo = initRunInfo(context);
    }

    @Override
    public void executeCmdResults(File file) {
        ArrayList<String> cmd = runInfo.getcmdArray();
        for(String s : cmd){
            execCommand(s.replace("\"", ""), file);
        }

    }

    @Override
    public String getLogPath() {
        return mLogFile.getPath();
    }

    @Override
    public String getLogDir() {
        return mLogFile.getParent();
    }

    @Override
    public void getSystemInfo(File file) {
        ArrayList<String> info = runInfo.getInfoArray();
        for(String s : info){
            try {
                File f = new File(s.replace("\"",""));
                BufferedReader bufferedReader = new BufferedReader(new FileReader(f));
                BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));

                String buffer = bufferedReader.readLine();
                writer.write("*************************************\n" + "File Name : " + s + "\n\n");
                while (buffer != null){
                    writer.write(buffer);
                    writer.newLine();
                    writer.flush();
                    buffer = bufferedReader.readLine();
                }
                writer.write("*************************************\n\n\n");
                bufferedReader.close();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
/**/
    @Override
    public void readBufferLogFiles() {

    }

    public File getLogFile(){
        return mLogFile;
    }

    @Override
    public File createLogFile(String fileName) {
        String path = createLogDir();
        File logFile = new File(path + "/" + fileName);
        if(!logFile.exists()){
            try {
                logFile.createNewFile();
            } catch (IOException e) {
                log(e.getMessage());
            }
        }
        mLogFile = logFile;
        return logFile;

    }

    public RunInfo getRunInfo(){
        return runInfo;
    }

    public RunInfo initRunInfo(Context context){
        XmlPull pull = new XmlPull();
        RunInfo runInfo = new RunInfo();
        try {
            InputStream inputStream = context.getAssets().open("commands.xml");
            runInfo = pull.parse(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e){
            e.printStackTrace();
        }
        return runInfo;
    }

    /*create the directory for logs.
    * /sdcard/Log/2015-08-14
    * */
    public String createLogDir(){
        File parentDir = new File(PARENTDIR);
        String logPath = getDate();

        File logDir = new File(PARENTDIR + "/" + logPath);
        if(!parentDir.exists()){
            parentDir.mkdirs();
            log("create parent dir of logs :"+ parentDir.exists());
        }

        if(logDir.mkdir()){
                log("create dir for logs :" + logDir.getPath());
        }

        return logDir.getPath();
    }

    public static  String getDate(){
        String[] month = new String[]{"JAN", "FEB", "MAR", "APR", "MAY", "JUNE", "JULY", "AUG", "SEPT", "OCT", "NOV", "DEC", "UNDECIMBER"};
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR) + "_" + month[calendar.get(Calendar.MONTH)] + "_" + calendar.get(Calendar.DAY_OF_MONTH)
                + "_" + calendar.get(Calendar.HOUR_OF_DAY) + "_" + calendar.get(Calendar.MINUTE) + "_" + calendar.get(Calendar.SECOND);
    }

    public void execCommand(String command, File file){
        Process process = null;
        DataOutputStream os = null;

        try {
            process = Runtime.getRuntime().exec("sh");
            os = new DataOutputStream(process.getOutputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
            os.write(command.getBytes());
            os.writeBytes("\n");
            os.flush();
            os.close();
            String s = bufferedReader.readLine();

            writer.write("*************************************\n" + "cmd : " + command + "\n\n");
            while (s != null){

                writer.write(s);
                writer.newLine();
                s = bufferedReader.readLine();

            }
            writer.write("*************************************\n\n\n");
            writer.flush();
            writer.close();

            bufferedReader.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //process.destroy();
        }
    }

    public void copyFile(File sourceFile, File targetFile){

    }

    public void log(String s){
        if(DEBUG)
            Log.d(TAG, s);
    }
}
