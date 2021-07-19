package com.example.silentred;

import android.content.Context;
import android.content.res.AssetManager;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Time;
import java.util.ArrayList;

public class LoadAreasXML {
    final static String KEY_AREA="area";
    final static String KEY_NAME="name";
    final static String KEY_TIME="time";

    public static ArrayList<Area> parseAreas(Context context){
        ArrayList<Area> data = null;
        InputStream in = openAreasFile(context);
        XmlPullParserFactory xmlFactoryObject;
        try {
            xmlFactoryObject = XmlPullParserFactory.newInstance();
            XmlPullParser parser = xmlFactoryObject.newPullParser();

            parser.setInput(in, null);
            int eventType = parser.getEventType();
            Area currentArea = null;
            String inTag = "";
            String strTagText = null;

            while (eventType != XmlPullParser.END_DOCUMENT){
                inTag = parser.getName();
                switch (eventType){
                    case XmlPullParser.START_DOCUMENT:
                        data = new ArrayList<Area>();
                        break;
                    case XmlPullParser.START_TAG:
                        if (inTag.equalsIgnoreCase(KEY_AREA))
                            currentArea = new Area();
                        break;
                    case XmlPullParser.TEXT:
                        strTagText = parser.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        if (inTag.equalsIgnoreCase(KEY_AREA))
                            data.add(currentArea);
                        else if (inTag.equalsIgnoreCase(KEY_NAME))
                            currentArea.setName(strTagText);
                        else if (inTag.equalsIgnoreCase(KEY_TIME))
                            currentArea.setTime(Time.valueOf(strTagText));
                        inTag ="";
                        break;
                }//switch
                eventType = parser.next();
            }//while
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    private static InputStream openAreasFile(Context context){
        AssetManager assetManager = context.getAssets();
        InputStream in =null;
        try {
            in = assetManager.open("areas.xml");
        } catch (IOException e) {e.printStackTrace();}
        return in;
    }

}