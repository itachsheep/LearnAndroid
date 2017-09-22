package com.skyworthauto.speak.cmd;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Xml;
import android.view.InflateException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.HashMap;

public class CmdProviderInflater {
    private static final String PACKAGE_NAME = CmdProviderInflater.class.getPackage().getName();

    private static final Class<?>[] CTOR_SIGNATURE = new Class[]{Context.class, AttributeSet.class};
    private static final HashMap<String, Constructor<?>> sConstructorMap =
            new HashMap<String, Constructor<?>>();

    public static BaseCmdProvider inflate(Context context, int resId) {
        XmlPullParser parser = context.getResources().getXml(resId);
        AttributeSet attrs = Xml.asAttributeSet(parser);

        try {

            int type;
            while ((type = parser.next()) != parser.START_TAG && type != parser.END_DOCUMENT) {
                ;
            }

            if (type != parser.START_TAG) {
                throw new XmlPullParserException("No start tag found");
            }

            if (!parser.getName().equals("CmdProvider")) {
                throw new XmlPullParserException("Unexpected start tag: found " + parser.getName()
                        + ", expected: CmdProvider");
            }

            BaseCmdProvider cmdProvider = new BaseCmdProvider(context, attrs);


            Object args[] = new Object[]{context, attrs};

            for (type = parser.next(); type != XmlPullParser.END_DOCUMENT; type = parser.next()) {
                if (type != XmlPullParser.START_TAG) {
                    continue;
                }
                CmdSpeakable cmd = newCmd(context, parser.getName(), args);
                cmdProvider.addChild(cmd);
            }

            return cmdProvider;
        } catch (XmlPullParserException e) {
            throw new InflateException(e);
        } catch (IOException e) {
            throw new InflateException(parser.getPositionDescription(), e);
        }
    }

    private static CmdSpeakable newCmd(Context context, String tagName, Object[] args) {
        String name = PACKAGE_NAME + "." + tagName;
        Constructor<?> constructor = sConstructorMap.get(name);
        try {
            if (constructor == null) {
                Class<?> clazz = context.getClassLoader().loadClass(name);
                constructor = clazz.getConstructor(CTOR_SIGNATURE);
                sConstructorMap.put(name, constructor);
            }
            return (CmdSpeakable) constructor.newInstance(args);
        } catch (NoSuchMethodException e) {
            throw new InflateException("Error inflating class " + name, e);
        } catch (ClassNotFoundException e) {
            throw new InflateException("No such class: " + name, e);
        } catch (Exception e) {
            throw new InflateException("While create instance of" + name, e);
        }
    }
}
