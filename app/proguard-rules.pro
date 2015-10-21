# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /usr/local/opt/android-sdk/tools/proguard/proguard-android.txt
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
#}

-repackageclasses

# for android-support
-keep class android.support.** { *; }

# for Butter Knife (see http://jakewharton.github.io/butterknife/#proguard)
-keep class butterknife.** { *; }
-keep class **$$ViewBinder { *; }
-dontwarn butterknife.internal.**
-keepclasseswithmembernames class * { @butterknife.* <fields>; }
-keepclasseswithmembernames class * { @butterknife.* <methods>; }

# for OkHttp
-dontwarn com.squareup.okhttp.**

# for Twitter4J
-keep class twitter4j.** { *; }
-dontwarn twitter4j.*
-dontwarn twitter4j.management.**

-keep class org.yaml.snakeyaml.** { public protected private *; }
-dontwarn org.yaml.snakeyaml.**

# for EventBus
-keepclassmembers class ** { public void onEvent*(**); }
