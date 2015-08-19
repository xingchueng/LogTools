package com.meizu.fac.logtools;

import android.util.Log;

import java.util.ArrayList;
import java.util.Observable;

/**
 * Created by zhangxing on 15-8-19.
 */
public class StateData extends Observable{
    private String state;
    private boolean changed;

    public StateData() {
    }

    public void stateChanged(){
        setChanged();
        notifyObservers();
        Log.d("logtools", "invoked stateChanged()");
    }

    public void setSTate(String state){
        this.state = state;
        stateChanged();
    }

    public String getState(){
        return state;
    }


}
