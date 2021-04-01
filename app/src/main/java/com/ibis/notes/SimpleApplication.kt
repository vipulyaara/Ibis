package com.ibis.notes

import android.app.Application
import com.ibis.notes.config.AppInitializers
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

/**
 * Authored by vipulkumar on 18/09/17.
 */

@HiltAndroidApp
class SimpleApplication : Application() {
    @Inject
    lateinit var initializers: AppInitializers

    override fun onCreate() {
        super.onCreate()
        initializers.init(this)
    }
}
