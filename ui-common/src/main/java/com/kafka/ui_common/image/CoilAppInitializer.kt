package com.kafka.ui_common.image

import android.app.Application
import android.content.Context
import coil.Coil
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import coil.util.CoilUtils
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.rekhta.data.AppInitializer
import org.rekhta.data.injection.ProcessLifetime
import java.util.logging.Level
import java.util.logging.Logger
import javax.inject.Inject

class CoilAppInitializer @OptIn(ExperimentalCoilApi::class)
@Inject constructor(
    private val okHttpClient: OkHttpClient,
    @ApplicationContext private val context: Context,
    @ProcessLifetime private val processScope: CoroutineScope
) : AppInitializer {
    override fun init(application: Application) {
        processScope.launch {
            val coilOkHttpClient = okHttpClient.newBuilder()
                .cache(CoilUtils.createDefaultCache(context))
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.NONE
                })
                .build()

            Logger.getLogger(OkHttpClient::class.java.name).level = Level.OFF
            Coil.setImageLoader {
                ImageLoader.Builder(application)
                    .okHttpClient(coilOkHttpClient)
                    .build()
            }
        }
    }
}
