package com.touchpro.controller;

import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
@SuppressWarnings("all")
public class HomePressController {
    private Context mContext;

    public HomePressController(final Context mContext) {
        super();
        this.mContext = mContext;
    }

    public void handleHomePress() {
        try {
            final Intent intent = new Intent("android.intent.action.MAIN");
            intent.setFlags(268435456);
            intent.addCategory("android.intent.category.HOME");
            this.mContext.startActivity(intent);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void openRecentApp() {
        try {
            final Class<?> forName = Class.forName("android.os.ServiceManager");
            final IBinder binder = (IBinder) forName.getMethod("getService", (Class<?>[]) new Class[0]).invoke(forName, "statusbar");
            final Class<?> forName2 = Class.forName(binder.getInterfaceDescriptor());
            final Object invoke = forName2.getClasses()[0].getMethod("asInterface", (Class<?>[]) new Class[0]).invoke(null, binder);
            final Method method = forName2.getMethod("toggleRecentApps", (Class<?>[]) new Class[0]);
            method.setAccessible(true);
            method.invoke(invoke, new Object[0]);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (NoSuchMethodException ex2) {
            ex2.printStackTrace();
        } catch (IllegalAccessException ex3) {
            ex3.printStackTrace();
        } catch (IllegalArgumentException ex4) {
            ex4.printStackTrace();
        } catch (InvocationTargetException ex5) {
            ex5.printStackTrace();
        } catch (RemoteException ex6) {
            ex6.printStackTrace();
        }
    }
}