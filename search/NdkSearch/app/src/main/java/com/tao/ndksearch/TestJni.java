package com.tao.ndksearch;

public class TestJni {

    public static native int funStatic(int a, int b);
    public native int fun(int a, int b);
    public native float fun(float a, float b);
    public native JavaObject createObject();
    public native JavaObject createObjectAndSet();
    public native int getField();
    public native int getStaticField();
    public native int invokeMethod();
    public native int invokeStaticMethod();
}
