package org.ibis.base

import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.channels.sendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import org.ibis.base.InvokeError
import org.ibis.base.InvokeStarted
import org.ibis.base.InvokeStatus
import org.ibis.base.InvokeSuccess
import java.util.concurrent.atomic.AtomicInteger
import javax.inject.Inject

class ObservableLoadingCounter @Inject constructor() {
    private val count = AtomicInteger()
    private val loadingState = ConflatedBroadcastChannel(count.get())

    val observable: Flow<Boolean>
        get() = loadingState.asFlow().map { it > 0 }

    fun addLoader() {
        loadingState.sendBlocking(count.incrementAndGet())
    }

    fun removeLoader() {
        loadingState.sendBlocking(count.decrementAndGet())
    }

    fun toggleLoader(add: Boolean) {
        if (add) addLoader() else removeLoader()
    }
}

suspend fun ObservableLoadingCounter.collectFrom(statuses: Flow<InvokeStatus>) {
    statuses.collect {
        if (it == InvokeStarted) {
            addLoader()
        } else if (it == InvokeSuccess || it is InvokeError) {
            removeLoader()
        }
    }
}
