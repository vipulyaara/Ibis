package org.rekhta.data.analytics

import android.content.Context
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.crashlytics.FirebaseCrashlytics
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import org.ibis.base.CrashLogger
import org.ibis.base.EventInfo
import org.ibis.base.Logger
import org.ibis.base.UserData
import org.rekhta.data.injection.ProcessLifetime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseLogger @Inject constructor(
    @ApplicationContext private val context: Context,
    @ProcessLifetime private val scope: CoroutineScope,
    private val crashLogger: CrashLogger
) : Logger {
    private val firebaseAnalytics by lazy { FirebaseAnalytics.getInstance(context) }
    private val userData = MutableStateFlow(UserData(""))

    override fun log(eventInfo: EventInfo) {
        val (eventName, map) = eventInfo
        firebaseAnalytics.logEvent(eventName, map.asBundle())
    }

    override fun updateUserProperty(update: UserData.() -> UserData) {

    }
}

@Singleton
class FirebaseCrashLogger @Inject constructor() : CrashLogger {
    override fun initialize(userData: UserData) {
        FirebaseCrashlytics.getInstance().setUserId(userData.userId)
    }

    override fun logFatal(throwable: Throwable) {
        FirebaseCrashlytics.getInstance().log(throwable.message.orEmpty())
    }

    override fun logNonFatal(throwable: Throwable) {
        FirebaseCrashlytics.getInstance().recordException(throwable)
    }
}

fun Map<String, String>.asBundle() = Bundle().apply {
    entries.forEach {
        putString(it.key, it.value)
    }
}
