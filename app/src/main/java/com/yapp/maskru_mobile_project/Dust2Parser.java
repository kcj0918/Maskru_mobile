package com.yapp.maskru_mobile_project;

/**
 * Created by user on 2017-02-25.
 */

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;

public class Dust2Parser {
    public enum TagType {NONE, DUST}

    public Dust2Parser() {
    }

    public ArrayList<String> parse(String xml) {

        ArrayList<String> resultList1 = new ArrayList();
        String dbo = null;

        TagType tagType = TagType.NONE;

        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(new StringReader(xml));

            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.END_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        if (parser.getName().equals("CAISTEP")) {
                            tagType = TagType.DUST;
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (parser.getName().equals("CAISTEP")) {
                            resultList1.add(dbo);
                        }
                        break;
                    case XmlPullParser.TEXT:
                        switch (tagType) {
                            case DUST:
                                dbo = parser.getText();
                                break;
                        }
                        tagType = TagType.NONE;
                        break;
                }
                eventType = parser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultList1;
    }
}