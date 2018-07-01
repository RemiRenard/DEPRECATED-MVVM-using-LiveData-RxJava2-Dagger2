package com.app.jetpack.di

import android.app.Application
import android.arch.persistence.room.Room
import com.app.jetpack.data.api.NetworkService
import com.app.jetpack.data.db.GithubDb
import com.app.jetpack.data.db.RepoDao
import com.app.jetpack.utils.Constants
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class AppModule {

    /**
     * Create the interceptor of retrofit.
     *
     * @return Interceptor
     */
    @Singleton
    @Provides
    fun createInterceptor(): Interceptor {
        return Interceptor { chain ->
            val newRequest = chain.request().newBuilder()
                    .addHeader(Constants.CONTENT_TYPE, Constants.APPLICATION_JSON)
                    .build()
            chain.proceed(newRequest)
        }
    }

    /**
     * Create the client OkHttp for retrofit.
     *
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
     *
     * @param client OkHttpClient
     * @return Retrofit
     */
    @Singleton
    @Provides
    fun createRetrofitBuilder(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build()
    }

    /**
     * Return the network service of the app.
     *
     * @param retrofit Retrofit
     * @return NetworkService
     */
    @Singleton
    @Provides
    fun createNetworkService(retrofit: Retrofit): NetworkService =
            retrofit.create(NetworkService::class.java)

    /**
     * Return the local database of the app.
     *
     * @param app Application
     * @return GithubDb
     */
    @Singleton
    @Provides
    fun provideDb(app: Application): GithubDb {
        return Room
                .databaseBuilder(app, GithubDb::class.java, "github.db")
                .fallbackToDestructiveMigration()
                .build()
    }

    /**
     * Return the Data Access Object of Repos.
     *
     * @param db GithubDb
     * @return RepoDao
     */
    @Singleton
    @Provides
    fun provideRepoDao(db: GithubDb): RepoDao {
        return db.repoDao()
    }
}
