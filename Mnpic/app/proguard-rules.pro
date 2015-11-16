# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in C:\android\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}-optimizationpasses 5
-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontpreverify
-verbose
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class com.android.vending.licensing.ILicensingService

-keepclasseswithmembernames class * {
    native <methods>;
}

-keepclasseswithmembernames class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembernames class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}


#pulltorefresh
-dontwarn com.handmark.pulltorefresh.library.**
-keep class com.handmark.pulltorefresh.library.** { *;}
-dontwarn com.handmark.pulltorefresh.library.extras.**
-keep class com.handmark.pulltorefresh.library.extras.** { *;}
-dontwarn com.handmark.pulltorefresh.library.internal.**
-keep class com.handmark.pulltorefresh.library.internal.** { *;}
#okhttp
-dontwarn com.squareup.okhttp.**
-keep class com.squareup.okhttp.** { *;}
-dontwarn okio.**
# Gson uses generic type information stored in a class file when working with fields. Proguard
-keepattributes Signature
-keepattributes *Annotation*
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }
-keep class com.renkun.mnpic.module.** { *; }
#photoview
-dontwarn uk.co.senab.photoview.**
-keep class uk.co.senab.photoview.** { *;}
#bd
-libraryjars libs/android_api_3.6.9.3.jar
-libraryjars libs/BDAutoUpdate_APPX_SDK_20150826.jar
-libraryjars libs/need_lib.jar
-libraryjars libs/patchupdate.jar

-dontwarn com.baidu.**
-keep class com.baidu.** { *;}
-dontwarn com.baidu.android.bba.common.**
-keep class com.baidu.android.bba.common.** { *;}
-dontwarn com.baidu.appsearch.patchupdate.**
-keep class com.baidu.appsearch.patchupdate.** { *;}




