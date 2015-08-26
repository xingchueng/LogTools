package com.meizu.fac.logtools.utils;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

public class XmlPull
{
    private RunInfo runInfo = new RunInfo();
    private String s = new String();
    private ArrayList<String> cmds;
    private ArrayList<String> infos;

    public RunInfo parse(InputStream input)
            throws XmlPullParserException, IOException
    {
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        XmlPullParser xpp = factory.newPullParser();
        xpp.setInput(input,"UTF-8" );

        int eventType = xpp.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            if(eventType == XmlPullParser.START_DOCUMENT) {
                cmds = new ArrayList<>();
                infos = new ArrayList<>();
            } else if(eventType == XmlPullParser.START_TAG) {
                if(xpp.getName().equals("commands") || xpp.getName().equals("infos")){
                    eventType = xpp.next();
                }else if(xpp.getName().equals("command")){
                    eventType = xpp.next();
                    s = xpp.getText();
                }else if(xpp.getName().equals("info")){
                    eventType = xpp.next();
                    s = xpp.getText();
                }

            } else if(eventType == XmlPullParser.END_TAG) {
                if(xpp.getName().equals("command")){
                    cmds.add(s);
                }else if(xpp.getName().equals("info")){
                    infos.add(s);
                }
            }

            eventType = xpp.next();
        }

        runInfo.setCmdArray(cmds);
        runInfo.setInfoArray(infos);
        return runInfo;
    }


}