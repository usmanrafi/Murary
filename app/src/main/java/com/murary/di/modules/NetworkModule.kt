package com.murary.di.modules

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.murary.network.DeezerGateway
import com.murary.utils.Constants
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
class NetworkModule {

    @Named("BASE_URL")
    @Provides
    fun getBaseUrl(): String {
        return Constants.BASE_URL
    }

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().setLenient().create()

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

    @Provides
    @Singleton
    fun provideOkhttpClient(loggingInterceptor: HttpLoggingInterceptor)
            : OkHttpClient {
        val client = OkHttpClient.Builder()
        client.addInterceptor(loggingInterceptor)
        client.connectTimeout(Constants.CONNECTION_TIMEOUT, TimeUnit.SECONDS)
        return client.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        gson: Gson, okHttpClient: OkHttpClient,
        @Named("BASE_URL") baseUrl: String
    ):
            Retrofit {

        return Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideGateway(retrofit: Retrofit): DeezerGateway {
        return retrofit.create(DeezerGateway::class.java)
    }
}
