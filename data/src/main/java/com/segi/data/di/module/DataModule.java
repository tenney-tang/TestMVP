package com.segi.data.di.module;

import android.content.Context;

import com.anupcowkur.reservoir.Reservoir;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.segi.data.api.ApiService;
import com.segi.data.cache.LocalCache;
import com.segi.data.cache.LocalCacheFactory;
import com.segi.data.repository.DataStore;
import com.segi.data.repository.DataStoreRepository;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class DataModule {

    /**
     * 连接超时
     */
    public static final long CONNECT_TIMEOUT = 10;

    /**
     * 本地缓存大小
     */
    private static final long LOCAL_CACHE_SIZE = 3 * 1024 * 1024L;

    /**
     * host
     */
    private String mBaseUrl;

    public final Gson gson = new GsonBuilder().create();

    public DataModule(Context context, String baseUrl) {
        mBaseUrl = baseUrl;
        initReservoir(context);
    }

    /**
     * 提供单例网络请求仓库
     *
     * @param apiService
     * @return
     */
    @Singleton
    @Provides
    public DataStoreRepository provideRepository(ApiService apiService, LocalCache localCache) {
        return new DataStoreRepository(apiService, localCache);
    }

    /**
     * 提供单例Api
     *
     * @param retrofit
     * @return
     */
    @Singleton
    @Provides
    public ApiService provideApi(Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }

    /**
     * 提供单例RetrofitClient
     *
     * @param client
     * @param factory
     * @return
     */
    @Singleton
    @Provides
    public Retrofit provideRetrofit(OkHttpClient client, Converter.Factory factory) {
        return new Retrofit.Builder()
                .baseUrl(mBaseUrl)
                .client(client)
                .addConverterFactory(factory)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    /**
     * 提供单例转换器
     *
     * @return
     */
    @Singleton
    @Provides
    public Converter.Factory provideConverter() {
        return GsonConverterFactory
                .create(gson);
    }

    /**
     * 提供单例OkHttpClient
     *
     * @param interceptor
     * @return
     */
    @Singleton
    @Provides
    public OkHttpClient provideClient(HttpLoggingInterceptor interceptor) {
        //忽略ssl证书验证
        X509TrustManager xtm = new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        };
        SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance("SSL");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            sslContext.init(null, new TrustManager[]{xtm}, new SecureRandom());
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        HostnameVerifier verifier = new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };

        return new OkHttpClient.Builder()
                .sslSocketFactory(sslContext.getSocketFactory(),xtm)
                .hostnameVerifier(verifier)
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .addNetworkInterceptor(new StethoInterceptor())
                .build();
    }

    /**
     * 提供单例日志拦截器
     *
     * @return
     */
    @Singleton
    @Provides
    public HttpLoggingInterceptor provideInterceptor() {
        return new HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.NONE);
    }

    @Provides
    @Singleton
    public DataStore provideDataStore(ApiService apiService, LocalCache localCache) {
        return new DataStoreRepository(apiService, localCache);
    }

    /**
     * 缓存
     *
     * @return
     */
    @Provides
    @Singleton
    public LocalCache provideLocalCache() {
        return new LocalCacheFactory();
    }

    /**
     * 初始化本地缓存
     *
     * @param context
     */
    private void initReservoir(Context context) {
        try {
            Reservoir.init(context, LOCAL_CACHE_SIZE, gson);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
