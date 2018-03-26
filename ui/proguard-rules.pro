-renamesourcefileattribute SourceFile
-keepattributes SourceFile,LineNumberTable

-keepnames class com.mercadolibre.android.ui.drawee.state.State
-keepnames class com.mercadolibre.android.ui.drawee.StateDraweeView$OnLevelListener
-keepnames class com.mercadolibre.android.ui.drawee.StateDraweeView$OnEnabledListener

# Fonts
-keep class com.mercadolibre.android.ui.font.Font { *; }
-keepclassmembers,allowoptimization enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# Fresco
-keep,allowobfuscation @interface com.facebook.common.internal.DoNotStrip

# Do not strip any method/class that is annotated with @DoNotStrip
-keep @com.facebook.common.internal.DoNotStrip class *
-keepclassmembers class * {
    @com.facebook.common.internal.DoNotStrip *;
}

# Keep native methods
-keepclassmembers class * {
    native <methods>;
}

-dontwarn okio.**
-dontwarn com.squareup.okhttp.**
-dontwarn okhttp3.**
-dontwarn javax.annotation.**
-dontwarn com.android.volley.toolbox.**
-dontwarn edu.umd.cs.findbugs.annotations.SuppressFBWarnings

-dontnote org.apache.commons.lang3.**

-dontwarn com.facebook.**
-dontnote com.facebook.**

-keep class com.mercadolibre.android.ui.activities.LayoutUtilsActivity { *; }
-keep class com.mercadolibre.android.ui.activities.MLImageTestActivity { *; }
-dontnote com.mercadolibre.android.ui.activities.LayoutUtilsActivity
-dontnote com.mercadolibre.android.ui.activities.MLImageTestActivity

# Notes of android sdk can be safely ignored
-dontnote android.net.http.*
-dontnote org.apache.commons.codec.**
-dontnote org.apache.http.**