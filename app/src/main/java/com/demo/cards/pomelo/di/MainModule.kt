package com.demo.cards.pomelo.di

import com.demo.cards.pomelo.BuildConfig.API_BASE_URL
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.demo.cards.pomelo.data.remote.UserTokenService
import com.demo.cards.pomelo.data.repositories.UserTokenRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

object MainModule {
    private const val MAIN = "main"

    fun initModule(): Module {
        val mainModule = module {
            single(named(MAIN)) { provideMoshi() }
            single(named(MAIN)) { provideOkHttpClient() }
            single(named(MAIN)) {
                provideRetrofit(API_BASE_URL, get(named(MAIN)), get(named(MAIN)))
            }
            single { get<Retrofit>(named(MAIN)).create(UserTokenService::class.java) }
            single { UserTokenRepository(get()) }
        }
        return mainModule
    }

    private fun provideMoshi(): Moshi? {
        val builder = Moshi.Builder()
        builder.add(KotlinJsonAdapterFactory())
        return builder.build()
    }

    private fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val okHttpBuilder = OkHttpClient.Builder()
            .connectTimeout(DEFAULT_CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
            .readTimeout(DEFAULT_READ_TIMEOUT, TimeUnit.MILLISECONDS)
            .writeTimeout(DEFAULT_WRITE_TIMEOUT, TimeUnit.MILLISECONDS)
            .addInterceptor(
                Interceptor { chain ->
                    val request = chain.request().newBuilder()
                        .addHeader("Content-Type", "application/json")
                        .build()
                    chain.proceed(request)
                }
            )
            .addInterceptor(loggingInterceptor)

        return okHttpBuilder.build()
    }

    private fun provideRetrofit(
        baseUrl: String,
        okHttpClient: OkHttpClient,
        moshi: Moshi
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()

    private const val DEFAULT_CONNECT_TIMEOUT = 15000L
    private const val DEFAULT_READ_TIMEOUT = 15000L
    private const val DEFAULT_WRITE_TIMEOUT = 15000L
}
