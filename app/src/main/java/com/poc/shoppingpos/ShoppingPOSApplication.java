package com.poc.shoppingpos;

import com.poc.shoppingpos.db.AppDatabase;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

public class ShoppingPOSApplication extends MultiDexApplication {

    private AppExecutors mAppExecutors;
    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
        mAppExecutors = new AppExecutors();
    }

    public AppDatabase getDatabase() {
        return AppDatabase.getInstance(this, mAppExecutors);
    }

    public DataRepository getRepository() {
        return DataRepository.getInstance(getDatabase());
    }
}
