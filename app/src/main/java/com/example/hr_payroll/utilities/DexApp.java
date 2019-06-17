package com.example.hr_payroll.utilities;

import android.app.Application;

import androidx.multidex.MultiDex;


public class DexApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);

    }
}
