package com.app.jetpack.di

import com.app.jetpack.data.api.NetworkService
import com.app.jetpack.utils.Constants
import com.app.jetpack.utils.LiveDataCallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class AppModule {

    /**
     * Create the interceptor of retrofit.
     * @return Interceptor
     */
    @Singleton
    @Provides
    fun createInterceptor(): Interceptor {
        return Interceptor { chain ->
            val newRequest = chain.request().newBuilder()
                    .addHeader(Constants.CONTENT_TYPE, Constants.APPLICATION_JSON)
                    .addHeader(Constants.API_KEY_TITLE, Constants.API_KEY_VALUE)
                    .build()
            chain.proceed(newRequest)
        }
    }

    /**
     * Create the client OkHttp for retrofit.
     * @param interceptor Interceptor
     * @return OkHttpClient
     */
    @Singleton
    @Provides
    fun createHttpClient(interceptor: Interceptor): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val builder = OkHttpClient.Builder()
        builder.interceptors().add(interceptor)
        builder.addInterceptor(loggingInterceptor)
        return builder.build()
    }

    /**
     * Create the retrofit builder.
     * @param client OkHttpClient
     * @return Retrofit
     */
    @Singleton
    @Provides
    fun createRetrofitBuilder(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .baseUrl(Constants.SERVER_URL_PROD)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                .client(client)
                .build()
    }

    /**
     * Return the network service of the app.
     * @param retrofit Retrofit
     * @return NetworkService
     */
    @Singleton
    @Provides
    fun createNetworkService(retrofit: Retrofit): NetworkService =
            retrofit.create(NetworkService::class.java)
}