package org.rekhta.data.injection

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.rekhta.data.api.RekhtaService
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import java.util.logging.Level
import java.util.logging.Logger

@InstallIn(SingletonComponent::class)
@Module
class ServiceModule {
    @Provides
    fun provideService(retrofit: Retrofit): RekhtaService {
        return retrofit.create(RekhtaService::class.java)
    }

    private val headerInterceptor: Interceptor
        get() = Interceptor { chain ->
            val original = chain.request()

            val request = original.newBuilder()
                .header("content-type", "application/json")
                .header("tempToken", tempToken)
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

    @ExperimentalSerializationApi
    @Provides
    fun provideDefaultRetrofitBuilder(okHttpClient: OkHttpClient): Retrofit {
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(Json {
                ignoreUnknownKeys = true
                isLenient = true
            }.asConverterFactory(contentType))
            .build()
    }
}

const val baseUrl = "https://world-api.azureedge.net/api/v5/shayari/"

//const val baseUrl = "https://world.rekhta.org/api/v5/shayari/"
const val tempToken = "2d9ed522-7f35-4180-8c04-b5977e8b8c94"
