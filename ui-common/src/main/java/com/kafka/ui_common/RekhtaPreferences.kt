package com.kafka.ui_common

import kotlinx.coroutines.flow.Flow

interface RekhtaPreferences {

    var useLessData: Boolean
    fun observeUseLessData(): Flow<Boolean>

    var autoAddTitle: Boolean

    enum class Theme {
        LIGHT,
        DARK,
        SYSTEM
    }

    fun setup()
}

val RekhtaPreferences.Theme.isDark
    get() = this == RekhtaPreferences.Theme.DARK
