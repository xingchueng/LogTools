package com.meizu.fac.logtools;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.util.Observable;
import java.util.Observer;

public class MainActivity extends Activity {
    private static final String TAG = "logtools";
    private static final boolean DEBUG = true;
    Button mCatchLogButton = null;
    HelperHandler handler  = null;
    HelperHandler statusHandler = null;
    static TextView statusTextView = null;
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
        handler = new HelperHandler(thread.getLooper());
        statusHandler = new HelperHandler(Looper.myLooper());
       // Looper.loop();

    }

    public void initView(){
        final LogFile logFile = new LogFileImpl();
        TextView cmdTextView = (TextView)findViewById(R.id.cmd_textview);
        statusTextView = (TextView)findViewById(R.id.log_status);
        mCatchLogButton = (Button)findViewById(R.id.start_catch_button);
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


    }

    public String getTextContent(){
        StringBuilder builder = new StringBuilder();
        String[] cmds = Commands.getCommands();
        for(String s : cmds){
            builder.append(s + "\n");
        }
        builder.append("\n");
        cmds =  SystemInfo.getInfoFiles();
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
        final String[] STATE_STRING = new String[]{
                "STATE_READY", "STATE_CATCHING","STATE_DONE","STATE_ERROR",
        };
        TextView tv = getStatusTextView();
        tv.setText(STATE_STRING[res]);
    }

    public class CurrentLogStateObserver implements Observer{
        private Observable observable;
        private String data;

        @Override
        public void update(Observable observable, Object data) {
            Log.d("logtools","here");
            if(observable instanceof StateData){
                this.data =((StateData) observable).getState();
                Message msg = statusHandler.obtainMessage();
                msg.what = HelperHandler.CHANGE_LOG_STATUS;
                msg.obj = stateData;
                statusHandler.sendMessage(msg);

            }
        }

        public CurrentLogStateObserver(Observable observable) {
            this.observable = observable;
            observable.addObserver(this);
        }

    }


}
