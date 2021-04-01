package com.kafka.ui_common

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.launch
import org.ibis.base.delayFlow
import org.threeten.bp.Duration
import javax.inject.Inject

data class UiError(val message: String)
fun UiError(t: Throwable): UiError = UiError(t.message ?: "Error occurred: $t")

class SnackbarManager @Inject constructor() {
    // We want a maximum of 3 errors queued
    private val pendingErrors = MutableSharedFlow<UiError>(extraBufferCapacity = 3)
    private val removeErrorSignal = MutableSharedFlow<Unit>(extraBufferCapacity = 1)

    fun launchInScope(
        scope: CoroutineScope,
        onErrorVisibilityChanged: (UiError, Boolean) -> Unit
    ) {
        scope.launch {
            pendingErrors.collect { error ->
                // Set the error
                onErrorVisibilityChanged(error, true)

                merge(
                    delayFlow(Duration.ofSeconds(6).toMillis(), Unit),
                    removeErrorSignal
                ).firstOrNull()

                // Now remove the error
                onErrorVisibilityChanged(error, false)
                // Delay to allow the current error to disappear
                delay(200)
            }
        }
    }

    suspend fun sendError(error: UiError) {
        pendingErrors.emit(error)
    }

    suspend fun removeCurrentError() {
        removeErrorSignal.emit(Unit)
    }
}
