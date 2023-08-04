package com.example.gitrepo;

import android.app.Application;



public class MyApplication extends Application {
    private AppDatabase appDatabase;

    public AppDatabase getAppDatabase() {
        return appDatabase;
    }

    public void setAppDatabase(AppDatabase appDatabase) {
        this.appDatabase = appDatabase;
    }
}
