package com.ibis.ui.auth

import com.kafka.ui_common.BaseViewState
import com.kafka.ui_common.UiError
import org.rekhta.data.entities.User

data class AuthViewState(
    val currentUser: User? = null,
    val isLoading: Boolean = false,
    val error: UiError? = null
) : BaseViewState
