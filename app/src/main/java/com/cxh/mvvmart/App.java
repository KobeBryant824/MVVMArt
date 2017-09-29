package com.cxh.mvvmart;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.text.format.DateFormat;

import com.cxh.mvvmart.di.component.AppComponent;
import com.cxh.mvvmart.di.component.DaggerAppComponent;
import com.cxh.mvvmart.di.moduel.AppModule;
import com.cxh.mvvmart.util.FileUtils;
import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.socks.library.KLog;
import com.squareup.leakcanary.LeakCanary;
import com.zhy.autolayout.config.AutoLayoutConifg;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author Hai (haigod7[at]gmail[dot]com)
 *         2017/3/6
 */
public class App extends Application implements Thread.UncaughtExceptionHandler {

    private static App mInstance;
    private static AppComponent mAppComponent;
    private static RestfulApi sRestfulApi;

    public static App getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);

        mInstance = this;

        DiskCacheConfig mainDiskCacheConfig = DiskCacheConfig.newBuilder(this)
                .setBaseDirectoryPath(new File(getExternalCacheDir(), "fresco"))
                .setBaseDirectoryName("fresco_sample")
                .setMaxCacheSize(100 * 1024 * 1024)//100MB
                .build();

        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(this)
                .setMainDiskCacheConfig(mainDiskCacheConfig)
                .build();
        Fresco.initialize(this , config);

        KLog.init(Constant.BUILD, "mtcispdoctor");

        if (!checkDeviceHasNavigationBar(this))
            AutoLayoutConifg.getInstance().useDeviceSize();//拿设备的物理高度(状态栏+导航栏)进行百分比化

        EventBus.builder()
                .throwSubscriberException(Constant.BUILD)//只有在BUILD模式下，会抛出错误异常
                .installDefaultEventBus();

        if (!Constant.BUILD) Thread.currentThread().setUncaughtExceptionHandler(this);
        
        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();

    }

    public static AppComponent getAppComponent() {
        return mAppComponent;
    }

    public static RestfulApi RestfulApi() {
        if (null == sRestfulApi) sRestfulApi = mAppComponent.getRetrofit().create(RestfulApi.class);
        return sRestfulApi;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {

        try {
            PrintStream printStream = new PrintStream(FileUtils.getDownloadDir() + "error.log");

            Class clazz = Class.forName("android.os.Build");
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                printStream.println(field.getName() + " : " + field.get(null));
            }
            String currTime = DateFormat.getDateFormat(getApplicationContext()).format(System.currentTimeMillis());

            printStream.println("TIME:" + currTime);
            printStream.println("==================华丽丽的分隔线================");
            ex.printStackTrace(printStream);
        } catch (Exception e) {
            e.printStackTrace();
        }

        android.os.Process.killProcess(android.os.Process.myPid());
    }

    //获取是否存在NavigationBar
    public static boolean checkDeviceHasNavigationBar(Context context) {
        boolean hasNavigationBar = false;
        Resources rs = context.getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id);
        }
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                hasNavigationBar = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavigationBar = true;
            }
        } catch (Exception e) {

        }
        return hasNavigationBar;
    }
}
