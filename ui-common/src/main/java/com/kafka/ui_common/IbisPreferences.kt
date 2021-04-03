package com.kafka.ui_common

import kotlinx.coroutines.flow.Flow

interface IbisPreferences {
    var useLessData: Boolean
    fun observeUseLessData(): Flow<Boolean>

    var autoAddTitle: Boolean

    fun setup()
}
