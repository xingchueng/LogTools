package com.meizu.fac.logtools;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import com.meizu.fac.logtools.utils.*;

import org.xmlpull.v1.XmlPullParserException;

public class MainActivity extends Activity {
    private static final String TAG = "logtools";
    private static final boolean DEBUG = true;
    /****************************
    * status of catching action
    ******************************/
    private static final int STATE_READY = 0;
    private static final int STATE_CATCHING = 1;
    private static final int STATE_DONE = 2;
    private static final int STATE_ERROR = 3;
    /*
    * ******************************
    * */
    private static int logStatus = STATE_READY;
    private static Button mCatchLogButton = null;
    HelperHandler handler  = null;
    HelperHandler statusHandler = null;
    private static TextView statusTextView = null;
    private LogFile logFile;
    public static final String[] STATE_STRING = new String[]{
            "STATE_READY", "STATE_CATCHING","STATE_DONE","STATE_ERROR",
    };
    StateData stateData = new StateData();
    CurrentLogStateObserver stateObserver = new CurrentLogStateObserver(stateData);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        //Looper.prepare();
        HandlerThread thread = new HandlerThread("catch_log_thread");
        thread.start();
        handler = new HelperHandler(thread.getLooper(), this);
        statusHandler = new HelperHandler(Looper.myLooper());
       // Looper.loop();

    }

    public void initView(){
        logFile = new LogFileImpl(this);
        TextView cmdTextView = (TextView)findViewById(R.id.cmd_textview);
        statusTextView = (TextView)findViewById(R.id.log_status);
        mCatchLogButton = (Button)findViewById(R.id.start_catch_button);
        if(logStatus != STATE_READY){
            mCatchLogButton.setEnabled(false);
        }
        mCatchLogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message message = handler.obtainMessage();
                message.obj = stateData;
                message.what = HelperHandler.START_CATCH_LOG_ACTION;
                handler.sendMessage(message);
            }
        });

        cmdTextView.setText(getTextContent());
        statusTextView.setText(STATE_STRING[0]);

    }

    public String getTextContent(){
        StringBuilder builder = new StringBuilder();
        ArrayList<String> cmds = logFile.getRunInfo().getcmdArray();
        for(String s : cmds){
            builder.append(s + "\n");
        }
        builder.append("\n");
        cmds =  logFile.getRunInfo().getInfoArray();
        for (String s: cmds){
            builder.append("copy file:" + s + "\n");
        }
        return  builder.toString();
    }

    public static TextView getStatusTextView(){
        return statusTextView;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void log(String s){
        if(DEBUG){
            Log.d(TAG, s);
        }
    }

    public static void setText(int res){

        TextView tv = getStatusTextView();
        tv.setText(STATE_STRING[res] + " : " + LogFileImpl.getDate());
        tv.setTextColor(Color.RED);
    }

    public static void setButtonEnable(boolean b){
        mCatchLogButton.setEnabled(b);
    }

    public class CurrentLogStateObserver implements Observer{
        private Observable observable;
        private int data;

        @Override
        public void update(Observable observable, Object data) {
            //      Log.d("logtools","here");
            if(observable instanceof StateData){
                this.data =((StateData) observable).getState();
                Message msg = statusHandler.obtainMessage();
                msg.what = HelperHandler.CHANGE_LOG_STATUS;
                msg.obj = stateData;
                msg.arg1 = this.data;
                statusHandler.sendMessage(msg);

            }
        }

        public CurrentLogStateObserver(Observable observable) {
            this.observable = observable;
            observable.addObserver(this);
        }

    }


}
