package com.mercadolibre.android.ui.example;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.stetho.Stetho;


public class SampleApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Stetho.InitializerBuilder stethoBuilder = Stetho.newInitializerBuilder(this);
        stethoBuilder.enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this)); // WebKit inspector.
        stethoBuilder.enableDumpapp(Stetho.defaultDumperPluginsProvider(this)); // Dumpapp integration.
        Stetho.initialize(stethoBuilder.build());

        Fresco.initialize(this);
    }
}
