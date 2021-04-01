package com.ibis.notes.config

import android.app.Application
import com.google.firebase.FirebaseApp
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.ibis.base.AppCoroutineDispatchers
import org.rekhta.data.AppInitializer
import timber.log.LogcatTree
import timber.log.Timber
import javax.inject.Inject

class LoggerInitializer @Inject constructor() : AppInitializer {
    override fun init(application: Application) {
        Timber.plant(LogcatTree())
    }
}

class FirebaseInitializer @Inject constructor(
    private val dispatchers: AppCoroutineDispatchers
) : AppInitializer {
    override fun init(application: Application) {
        GlobalScope.launch(dispatchers.io) {
            Firebase.initialize(application)
            FirebaseApp.initializeApp(application)
        }
    }
}
