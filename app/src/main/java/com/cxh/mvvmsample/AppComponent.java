package com.cxh.mvvmsample;

import javax.inject.Singleton;

import dagger.Component;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * @author Hai (haigod7[at]gmail[dot]com)
 *         2017/3/6
 */
@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    App getApplication();

    Retrofit getRetrofit();

    OkHttpClient getOkHttpClient();
}
