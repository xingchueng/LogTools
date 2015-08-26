package com.meizu.fac.logtools;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by zhangxing on 15-8-14.
 */
public class HelperHandler extends Handler {
    public final static int START_CATCH_LOG_ACTION = 1;
    public final static int STOP_CATCH_LOG_ACTION  = 2;
    public final static int CHANGE_LOG_STATUS      = 3;
    public static ArrayList<String> states = new ArrayList<>();

    private LogFile logFile;
    private Context context;
    StateData stateData;

    public HelperHandler() {
        super();
        logFile = new LogFileImpl(context);
        stateData = new StateData();
    }

    public HelperHandler(Looper looper){
        super(looper);
        stateData = new StateData();
        states.add("STATE_READY");
        states.add("STATE_CATCHING");
        states.add("STATE_DONE");
        states.add("STATE_ERROR");
    }

    public HelperHandler(Looper looper, Context context){
        super(looper);
        this.context = context;
        logFile = new LogFileImpl(context);
        stateData = new StateData();
        states.add("STATE_READY");
        states.add("STATE_CATCHING");
        states.add("STATE_DONE");
        states.add("STATE_ERROR");
    }

    public void startCatchLog(){
        File log = logFile.createLogFile("logtest.txt");
        logFile.getSystemInfo(log);
        logFile.executeCmdResults(log);
    }

    public void changeLogStatus(int arg){
        MainActivity.setText(arg);
        MainActivity.setButtonEnable(arg == states.indexOf("STATE_CATCHING") ? false : true);
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what){
            case START_CATCH_LOG_ACTION:
                ((StateData)msg.obj).setSTate(states.indexOf("STATE_CATCHING"));
                startCatchLog();
                ((StateData)msg.obj).setSTate(states.indexOf("STATE_DONE"));
                break;
            case STOP_CATCH_LOG_ACTION:
                break;
            case CHANGE_LOG_STATUS:
                changeLogStatus(msg.arg1);
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
