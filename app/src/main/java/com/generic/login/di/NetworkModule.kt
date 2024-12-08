package com.generic.login.di

import com.generic.login.repository.MainApiRepository
import com.generic.login.repository.PhotoRepository
import com.generic.login.utils.BASE_URL
import com.generic.login.utils.PIXABAY_SOURCE
import com.generic.login.utils.PhotoApi
import com.generic.login.utils.PrimaryApi
import com.generic.login.webapi.ApiService
import com.generic.login.webapi.ImageService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
//@DisableInstallInCheck
@Module
object NetworkModule {

    @Provides
    fun providesHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    fun providesOkhttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .callTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
        return okHttpClient.build()
    }

    @Provides
    fun providesConverterFactory(): Converter.Factory {
        return GsonConverterFactory.create()
    }

    @Provides
    @Singleton
    @PrimaryApi
    fun providesRetrofitPrimary(
        //baseUrl: String,
        converterFactory: Converter.Factory,
        client: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(converterFactory)
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    @PhotoApi
    fun providesRetrofitPhoto(
        //baseUrl: String,
        converterFactory: Converter.Factory,
        client: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(PIXABAY_SOURCE)
            .addConverterFactory(converterFactory)
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun providesPrimaryRetrofitService(@PrimaryApi retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun providesPhotoRetrofitService(@PhotoApi retrofit: Retrofit): ImageService {
        return retrofit.create(ImageService::class.java)
    }

    //login + register
    @ExperimentalCoroutinesApi
    @Provides
    @PrimaryApi
    fun providesLoginRepository(
        apiService: ApiService
    ): MainApiRepository {
        return MainApiRepository(apiService)
    }

    //photos
    @ExperimentalCoroutinesApi
    @Provides
    @PhotoApi
    fun providesPhotoRepository(
        imageService: ImageService
    ): PhotoRepository {
        return PhotoRepository(imageService)
    }
}