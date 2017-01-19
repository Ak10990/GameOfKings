package com.android.thoughtworks;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by akanksha on 8/1/17.
 */

public class App extends Application {

    public static App mInstance;
    private static SharedPreferences mPrefs;
    private static Realm realm;

    public static App getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        initSharedPreferences();
        initDatabase();
    }

    private void initSharedPreferences() {
        mPrefs = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
    }

    private void initDatabase() {
        RealmConfiguration realmConfig = new RealmConfiguration.Builder(getApplicationContext())
                .build();
        realm = Realm.getInstance(realmConfig);
    }


    public static SharedPreferences getprefs() {
        return mPrefs;
    }

    public static Realm getRealm() {
        return realm;
    }

}
