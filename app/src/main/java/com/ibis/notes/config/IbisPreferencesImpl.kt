package com.ibis.notes.config

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.kafka.ui_common.RekhtaPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import org.rekhta.data.injection.ProcessLifetime
import javax.inject.Inject
import javax.inject.Named

class IbisPreferencesImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    @ProcessLifetime private val processScope: CoroutineScope,
    @Named("app") private val sharedPreferences: SharedPreferences,
) : RekhtaPreferences {
    private val defaultLanguageValue = 1

    private val preferenceKeyChangedFlow = MutableSharedFlow<String>(extraBufferCapacity = 1)

    private val listener = SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
        preferenceKeyChangedFlow.tryEmit(key)
    }

    companion object {
        const val KEY_AUTO_ADD_TITLE = "pref_auto_add_title"
        const val KEY_DATA_SAVER = "pref_data_saver"
    }

    override fun setup() {
        sharedPreferences.registerOnSharedPreferenceChangeListener(listener)
    }

    override var autoAddTitle: Boolean
        get() = sharedPreferences.getBoolean(KEY_AUTO_ADD_TITLE, false)
        set(value) = sharedPreferences.edit {
            putBoolean(KEY_AUTO_ADD_TITLE, value)
        }

    override var useLessData: Boolean
        get() = sharedPreferences.getBoolean(KEY_DATA_SAVER, false)
        set(value) = sharedPreferences.edit {
            putBoolean(KEY_DATA_SAVER, value)
        }

    override fun observeUseLessData(): Flow<Boolean> {
        return preferenceKeyChangedFlow
            // Emit on start so that we always send the initial value
            .onStart { emit(KEY_DATA_SAVER) }
            .filter { it == KEY_DATA_SAVER }
            .map { useLessData }
            .distinctUntilChanged()
    }
}
