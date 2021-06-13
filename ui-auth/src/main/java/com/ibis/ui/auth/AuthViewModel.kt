package com.ibis.ui.auth

import android.util.Patterns
import com.kafka.ui_common.ReduxViewModel
import com.kafka.ui_common.UiError
import com.kafka.ui_common.viewModelScoped
import dagger.hilt.android.lifecycle.HiltViewModel
import org.ibis.base.InvokeStarted
import org.ibis.base.errorMessageOrNull
import javax.inject.Inject

@HiltViewModel
internal class AuthViewModel @Inject constructor(
    private val loginUser: LoginUser,
    private val registerUser: RegisterUser,
    private val loginUserWithLink: LoginUserWithLink,
    private val observeUser: ObserveUser
) : ReduxViewModel<AuthViewState>(AuthViewState()) {

    init {
        viewModelScoped {
            observeUser.observe().collectAndSetState {
                copy(currentUser = it)
            }
        }

        observeUser(Unit)
    }

    fun login(email: String, password: String) {
        viewModelScoped {
            when {
                email.isValidEmail().not() -> {
                    setState { copy(error = UiError("Please enter valid email")) }
                }
                password.isValidPassword().not() -> {
                    setState { copy(error = UiError("Please enter valid password")) }
                }
                else -> {
                    loginUser(LoginUser.Params(email, password)).collectAndSetState {
                        copy(
                            error = it.errorMessageOrNull()?.let { UiError(it) },
                            isLoading = it is InvokeStarted
                        )
                    }
                }
            }
        }
    }

    fun signUp(email: String, password: String) {
        viewModelScoped {
            when {
                email.isValidEmail().not() -> {
                    setState { copy(error = UiError("Please enter valid email")) }
                }
                password.isValidPassword().not() -> {
                    setState { copy(error = UiError("Please enter valid password")) }
                }
                else -> {
                    registerUser(RegisterUser.Params(email, password)).collectAndSetState {
                        copy(
                            error = it.errorMessageOrNull()?.let { UiError(it) },
                            isLoading = it is InvokeStarted
                        )
                    }
                }
            }
        }
    }

    fun sendLink(email: String) {
        viewModelScoped {
            when {
                email.isValidEmail().not() -> {
                    setState { copy(error = UiError("Please enter valid email")) }
                }
                else -> {
                    loginUserWithLink(LoginUserWithLink.Params(email)).collectAndSetState {
                        copy(
                            error = it.errorMessageOrNull()?.let { UiError(it) },
                            isLoading = it is InvokeStarted
                        )
                    }
                }
            }
        }
    }

    private fun CharSequence.isValidEmail() =
        isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()

    private fun CharSequence.isValidPassword() = length > 4

}
