package com.meizu.fac.logtools.utils;

import java.util.ArrayList;

/**
 * Created by zhangxing on 15-8-20.
 */
public class RunInfo {
    private ArrayList<String> cmdArray;
    private ArrayList<String> infoArray;

    public RunInfo(){
        cmdArray = new ArrayList<>();
        infoArray = new ArrayList<>();
    }
    public ArrayList<String> getcmdArray(){
        return cmdArray;
    }

    public ArrayList<String> getInfoArray(){
        return infoArray;
    }
    public void setCmdArray(ArrayList<String> s){
        this.cmdArray = s;
    }

    public void setInfoArray(ArrayList<String> s){
        this.infoArray = s;
    }
}
