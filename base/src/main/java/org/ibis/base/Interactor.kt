package org.ibis.base

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.dropbox.android.external.store4.Store
import com.dropbox.android.external.store4.fresh
import com.dropbox.android.external.store4.get
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import java.util.concurrent.TimeUnit

abstract class Interactor<in P> {
    @FlowPreview
    @ExperimentalCoroutinesApi
    operator fun invoke(params: P, timeoutMs: Long = defaultTimeoutMs): Flow<InvokeStatus> {
        return flow {
            withTimeout(timeoutMs) {
                emit(InvokeStarted)
                doWork(params)
                emit(InvokeSuccess)
            }
        }.catch { t ->
            emit(InvokeError(t))
        }
    }

    suspend fun executeSync(params: P) = doWork(params)

    protected abstract suspend fun doWork(params: P)

    companion object {
        private val defaultTimeoutMs = TimeUnit.MINUTES.toMillis(5)
    }
}

abstract class SuspendInteractor<in P> {
    abstract val scope: CoroutineScope
    operator fun invoke(params: P) {
        scope.launch {
            try {
                doWork(params)
            } catch (e: CancellationException) {
                error("SuspendInteractor cancelled")
            }
        }
    }

    abstract suspend fun doWork(params: P)
}

abstract class PagingInteractor<P : PagingInteractor.Parameters<T>, T : Any> :
    SubjectInteractor<P, PagingData<T>>() {
    interface Parameters<T : Any> {
        val pagingConfig: PagingConfig
    }
}

val PAGING_CONFIG = PagingConfig(
    pageSize = 100,
    prefetchDistance = 40,
    enablePlaceholders = true
)

abstract class ResultInteractor<in P, R> {
    operator fun invoke(params: P): Flow<R> {
        return flow {
            emit(doWork(params))
        }.flowOn(Dispatchers.IO)
    }

    protected abstract suspend fun doWork(params: P): R
}

abstract class SuspendingWorkInteractor<P : Any, T> : SubjectInteractor<P, T>() {
    override suspend fun createObservable(params: P): Flow<T> = flow {
        emit(doWork(params))
    }

    abstract suspend fun doWork(params: P): T
}

abstract class SubjectInteractor<P : Any, T> {
    // Ideally this would be buffer = 0, since we use flatMapLatest below, BUT invoke is not
    // suspending. This means that we can't suspend while flatMapLatest cancels any
    // existing flows. The buffer of 1 means that we can use tryEmit() and buffer the value
    // instead, resulting in mostly the same result.
    private val paramState = MutableSharedFlow<P>(
        replay = 1,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    operator fun invoke(params: P) {
        paramState.tryEmit(params)
    }

    protected abstract suspend fun createObservable(params: P): Flow<T>

    @ExperimentalCoroutinesApi
    fun observe(): Flow<T> = paramState.flatMapLatest {
        createObservable(it)
    }
}

operator fun <T> SubjectInteractor<Unit, T>.invoke() = invoke(Unit)
