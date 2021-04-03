package org.rekhta.data.injection

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import java.util.logging.Level
import java.util.logging.Logger

@InstallIn(SingletonComponent::class)
@Module
class ServiceModule {
    private val headerInterceptor: Interceptor
        get() = Interceptor { chain ->
            val original = chain.request()

            val request = original.newBuilder()
                .header("content-type", "application/json")
                .method(original.method, original.body)
                .build()

            chain.proceed(request)
        }

    @Provides
    fun provideOkHttpClientBuilder(): OkHttpClient {
        val builder = OkHttpClient.Builder().apply {
            readTimeout(60, TimeUnit.SECONDS)
            connectTimeout(60, TimeUnit.SECONDS)

            addInterceptor(headerInterceptor)
            addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            Logger.getLogger(OkHttpClient::class.java.name).level = Level.FINE
        }

        return builder.build()
    }
}
