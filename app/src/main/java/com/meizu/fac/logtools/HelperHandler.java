package com.meizu.fac.logtools;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import java.io.File;

/**
 * Created by zhangxing on 15-8-14.
 */
public class HelperHandler extends Handler {
    public final static int START_CATCH_LOG_ACTION = 1;
    public final static int STOP_CATCH_LOG_ACTION  = 2;
    public final static int CHANGE_LOG_STATUS      = 3;
    public final static String READY = "READY_STATE";
    public final static String DONE  = "DOWN_STATE";
    public final static String ERROR = "ERROR_STATE";
    public final static String CATCHING = "IS CATCHING";
    private LogFile logFile;
    StateData stateData;

    public HelperHandler() {
        super();
        logFile = new LogFileImpl();
        stateData = new StateData();
    }

    public HelperHandler(Looper looper) {
        super(looper);
        logFile = new LogFileImpl();
        stateData = new StateData();
    }

    public void startCatchLog(){
        File log = logFile.createLogFile("logtest.txt");
        logFile.getSystemInfo(log);
        logFile.executeCmdResults(log);
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what){
            case START_CATCH_LOG_ACTION:
                startCatchLog();
                ((StateData)msg.obj).setSTate(DONE);
                break;
            case STOP_CATCH_LOG_ACTION:
                break;
            case CHANGE_LOG_STATUS:
                MainActivity.setText(2);
                break;
            default:
                break;
        }
        super.handleMessage(msg);
    }
    public void log(String s){
        Log.d("logtools",s);
    }
}
