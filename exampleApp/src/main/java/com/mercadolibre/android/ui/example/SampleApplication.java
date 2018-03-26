package com.mercadolibre.android.ui.example;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.stetho.Stetho;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class SampleApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );

        Stetho.InitializerBuilder stethoBuilder = Stetho.newInitializerBuilder(this);
        stethoBuilder.enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this)); // WebKit inspector.
        stethoBuilder.enableDumpapp(Stetho.defaultDumperPluginsProvider(this)); // Dumpapp integration.
        Stetho.initialize(stethoBuilder.build());

        Fresco.initialize(this);
    }
}
