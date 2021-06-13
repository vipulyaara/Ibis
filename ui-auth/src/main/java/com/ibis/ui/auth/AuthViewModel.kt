package com.ibis.ui.auth

import android.util.Patterns
import com.kafka.ui_common.ReduxViewModel
import com.kafka.ui_common.UiError
import com.kafka.ui_common.viewModelScoped
import com.notes.domain.interactors.LoginUser
import com.notes.domain.interactors.LoginUserWithLink
import com.notes.domain.interactors.RegisterUser
import com.notes.domain.observers.ObserveUser
import dagger.hilt.android.lifecycle.HiltViewModel
import org.ibis.base.InvokeStarted
import org.ibis.base.InvokeStatus
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
            validateEmail(email) {
                validatePassword(password) {
                    loginUser(LoginUser.Params(email, password)).collectAndSetState {
                        update(it)
                    }
                }
            }
        }
    }

    fun signUp(email: String, password: String) {
        viewModelScoped {
            validateEmail(email) {
                validatePassword(password) {
                    registerUser(RegisterUser.Params(email, password)).collectAndSetState {
                        update(it)
                    }
                }
            }
        }
    }

    fun sendLink(email: String) {
        viewModelScoped {
            validateEmail(email) {
                loginUserWithLink(LoginUserWithLink.Params(email)).collectAndSetState {
                    update(it)
                }
            }
        }
    }

    private fun AuthViewState.update(invokeStatus: InvokeStatus) = copy(
        error = invokeStatus.errorMessageOrNull()?.let { UiError(it) },
        isLoading = invokeStatus is InvokeStarted
    )

    private suspend fun validateEmail(email: String, onValidate: suspend () -> Unit) {
        if (!email.isValidEmail()) {
            setState { copy(error = UiError("Please enter valid email")) }
        } else {
            onValidate()
        }
    }

    private suspend fun validatePassword(password: String, onValidate: suspend () -> Unit) {
        if (!password.isValidPassword()) {
            setState { copy(error = UiError("Please enter valid password")) }
        } else {
            onValidate()
        }
    }

    private fun CharSequence.isValidEmail() =
        isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()

    private fun CharSequence.isValidPassword() = length > 4

}
