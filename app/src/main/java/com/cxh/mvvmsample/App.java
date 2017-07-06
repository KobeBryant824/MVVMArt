package com.cxh.mvvmsample;

import android.app.Application;
import android.text.format.DateFormat;

import com.cxh.mvvmsample.di.component.AppComponent;
import com.cxh.mvvmsample.di.component.DaggerAppComponent;
import com.cxh.mvvmsample.di.moduel.AppModule;
import com.cxh.mvvmsample.model.repository.RxCacheClient;
import com.cxh.mvvmsample.util.FileUtils;
import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.socks.library.KLog;
import com.squareup.leakcanary.LeakCanary;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.PrintStream;
import java.lang.reflect.Field;

/**
 * @author Hai (haigod7[at]gmail[dot]com)
 *         2017/3/6
 */
public class App extends Application implements Thread.UncaughtExceptionHandler {

    private static App mInstance;
    private static AppComponent mAppComponent;

    public static App getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
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

        KLog.init(BuildConfig.DEBUG, getString(R.string.app_name));

        EventBus.builder()
                .throwSubscriberException(BuildConfig.DEBUG)//只有在debug模式下，会抛出错误异常
                .installDefaultEventBus();
        
        mAppComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();

//		Thread.currentThread().setUncaughtExceptionHandler(this); // 上线打开

        LeakCanary.install(this);
    }

    public static AppComponent getAppComponent() {
        return mAppComponent;
    }

    public static RxCacheClient getRxCacheClient(){
        return mAppComponent.getRxCacheClient();
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
}
