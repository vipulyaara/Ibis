package org.ibis.base

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

interface Logger {
    fun log(eventInfo: EventInfo)
    fun updateUserProperty(update: UserData.() -> UserData)
}

interface CrashLogger {
    fun initialize(userData: UserData)
    fun logFatal(throwable: Throwable)
    fun logNonFatal(throwable: Throwable)
}

data class UserData(val userId: String)

interface Event

typealias EventInfo = Pair<String, Map<String, String>>

abstract class LoggingInteractor<P> {
    abstract val scope: CoroutineScope
    abstract val logger: Logger
    abstract val event: P

    operator fun invoke(params: suspend P.() -> EventInfo) {
        scope.launch { logger.log(params.invoke(event)) }
    }
}
