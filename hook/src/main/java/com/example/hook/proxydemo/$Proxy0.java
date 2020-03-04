package com.example.hook.proxydemo;// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.UndeclaredThrowableException;

public final class $Proxy0 extends Proxy implements ILogin {

    public $Proxy0(InvocationHandler invocationhandler) {
        super(invocationhandler);
    }

    public final boolean equals(Object obj) {
        try {
            return ((Boolean) super.h.invoke(this, m1, new Object[]{
                    obj
            })).booleanValue();
        } catch (Error _ex) {
        } catch (Throwable throwable) {
            throw new UndeclaredThrowableException(throwable);
        }
        return false;
    }

    public final String toString() {
        try {
            return (String) super.h.invoke(this, m2, null);
        } catch (Error _ex) {
        } catch (Throwable throwable) {
            throw new UndeclaredThrowableException(throwable);
        }
        return "";
    }

    public final void userLogin() {
        try {
            super.h.invoke(this, m3, null);
            return;
        } catch (Error _ex) {
        } catch (Throwable throwable) {
            throw new UndeclaredThrowableException(throwable);
        }
    }

    public final void userRegister() {
        try {
            super.h.invoke(this, m4, null);
            return;
        } catch (Error _ex) {
        } catch (Throwable throwable) {
            throw new UndeclaredThrowableException(throwable);
        }
    }

    public final int hashCode() {
        try {
            return ((Integer) super.h.invoke(this, m0, null)).intValue();
        } catch (Error _ex) {
        } catch (Throwable throwable) {
            throw new UndeclaredThrowableException(throwable);
        }
        return 0;
    }

    private static Method m1;
    private static Method m2;
    private static Method m3;
    private static Method m4;
    private static Method m0;

    /**
     * 最下面是通过反射拿到类中的几个方法，
     * 作为参数传递到InvocationHandler.invoke方法中，
     * 即调用动态代理对象的任何方法，最终都是走到InvocationHandler.invoke法中
     * （所以在invoke方法中写日志需要判断下，是否是调用代理对象指定的方法走到这里）
     *
     */
    static {
        try {
            m1 = Class.forName("java.lang.Object").getMethod("equals", new Class[]{
                    Class.forName("java.lang.Object")
            });
            m2 = Class.forName("java.lang.Object").getMethod("toString", new Class[0]);
            m3 = Class.forName("com.example.hook.proxydemo.ILogin").getMethod("userLogin", new Class[0]);
            m4 = Class.forName("com.example.hook.proxydemo.ILogin").getMethod("userRegister", new Class[0]);
            m0 = Class.forName("java.lang.Object").getMethod("hashCode", new Class[0]);
        } catch (NoSuchMethodException nosuchmethodexception) {
            throw new NoSuchMethodError(nosuchmethodexception.getMessage());
        } catch (ClassNotFoundException classnotfoundexception) {
            throw new NoClassDefFoundError(classnotfoundexception.getMessage());
        }
    }
}
