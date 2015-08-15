package com.meizu.fac.logtools;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/**
 * Created by zhangxing on 15-8-14.
 */
public class HelperHandler extends Handler {
    public final static int START_CATCH_LOG_ACTION = 1;
    public final static int STOP_CATCH_LOG_ACTION  = 2;

    public HelperHandler() {
        super();
    }

    public HelperHandler(Looper looper) {
        super(looper);
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what){
            case START_CATCH_LOG_ACTION:
                break;
            case STOP_CATCH_LOG_ACTION:
                break;

            default:
                break;
        }
        super.handleMessage(msg);
    }
}
