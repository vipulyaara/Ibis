package com.ibis.ui.auth

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.accompanist.insets.ExperimentalAnimatedInsets
import com.google.accompanist.insets.imePadding
import com.google.accompanist.insets.navigationBarsPadding
import com.kafka.ui_common.*
import com.kafka.ui_common.theme.iconPrimary
import com.kafka.ui_common.theme.textSecondary

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@ExperimentalAnimatedInsets
@Composable
fun LoginScreen(navController: NavHostController) {
    val authViewModel: AuthViewModel = hiltViewModel()
    val authViewState by authViewModel.state.collectAsState()

    if (authViewState.currentUser != null) {
        navController.navigateTo(Screen.NotesList) {
            popUpTo(navController.graph.startDestinationRoute.orEmpty())
        }
    }

    var authType by rememberMutableState { AuthType.Login }
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        modifier = Modifier.navigationBarsPadding(),
        scaffoldState = scaffoldState,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val keyboard = LocalSoftwareKeyboardController.current

            LoginByEmail(
                authType = authType,
                setAuthType = { authType = it },
                login = { email, password ->
                    keyboard?.hide()
                    when (authType) {
                        AuthType.Login -> authViewModel.login(email, password)
                        AuthType.SignUp -> authViewModel.signUp(email, password)
                        AuthType.SendLink -> authViewModel.sendLink(email)
                    }
                },
                modifier = Modifier.imePadding()
            )
        }
    }

    FullScreenProgressBar(show = authViewState.isLoading)
    ErrorSnackbar(snackbarError = authViewState.error, scaffoldState = scaffoldState)
}

@ExperimentalComposeUiApi
@Composable
private fun LoginByEmail(
    modifier: Modifier = Modifier,
    onFocusChanged: (FocusState) -> Unit = {},
    login: (String, String) -> Unit,
    authType: AuthType,
    setAuthType: (AuthType) -> Unit
) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.fillMaxHeight(0.15f))

        val vectorResource = ImageVector.vectorResource(id = R.drawable.ic_ibis)
        val logo = rememberVectorPainter(image = vectorResource)

        Image(
            modifier = Modifier
                .requiredHeightIn(36.dp, 136.dp),
            contentDescription = null,
            painter = logo,
            colorFilter = ColorFilter.tint(MaterialTheme.colors.iconPrimary)
        )

        Spacer(modifier = Modifier.fillMaxHeight(0.15f))

        var username by rememberSavableMutableState(init = { "" })
        var password by rememberSavableMutableState(init = { "" })

        LoginTextField(
            modifier = Modifier,
            loginTextField = LoginTextField.Username,
            text = username,
            setSearchText = { username = it },
            onFocusChanged = onFocusChanged
        )

        if (authType != AuthType.SendLink) {
            Spacer(modifier = Modifier.height(12.dp))

            LoginTextField(
                loginTextField = LoginTextField.Password,
                text = password,
                setSearchText = { password = it },
                onFocusChanged = onFocusChanged
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        val isButtonEnabled = username.isNotEmpty()
                && (if (authType != AuthType.SendLink) password.isNotEmpty() else true)
        Button(
            enabled = isButtonEnabled,
            shape = RoundedCornerShape(8.dp),
            onClick = { login(username, password) }) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterVertically)
                    .padding(vertical = 8.dp),
                text = authType.buttonText,
                style = MaterialTheme.typography.subtitle1.alignCenter()
            )
        }

        if (authType == AuthType.Login) {
            Text(
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(top = 20.dp),
                text = "Forgot password?",
                textDecoration = TextDecoration.Underline,
                style = MaterialTheme.typography.caption
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        val switchAuthTypeText = if (authType.isLogin) "Sign up" else "Login"
        Text(
            modifier = Modifier
                .padding(20.dp)
                .clickable {
                    if (authType.isLogin) {
                        setAuthType(AuthType.SignUp)
                    } else {
                        setAuthType(AuthType.Login)
                    }
                },
            text = switchAuthTypeText,
            style = MaterialTheme.typography.subtitle1,
            color = MaterialTheme.colors.secondary
        )

        if (authType != AuthType.SendLink) {
            Text(
                modifier = Modifier
                    .padding(20.dp)
                    .clickable {
                        setAuthType(AuthType.SendLink)
                    },
                text = "Get a link in email",
                style = MaterialTheme.typography.subtitle1,
                color = MaterialTheme.colors.secondary
            )
        }
    }
}

@Composable
private fun LoginTextField(
    modifier: Modifier = Modifier,
    loginTextField: LoginTextField,
    text: String,
    setSearchText: (String) -> Unit,
    onFocusChanged: (FocusState) -> Unit
) {
    TextField(
        modifier = modifier
            .fillMaxWidth()
            .onFocusChanged(onFocusChanged),
        value = text,
        placeholder = {
            Text(
                text = loginTextField.hint,
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.textSecondary
            )
        },
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.None,
            autoCorrect = false,
            keyboardType = loginTextField.keyboardType,
            imeAction = loginTextField.imeAction
        ),
        visualTransformation = loginTextField.visualTransformation,
        onValueChange = { setSearchText(it) },
        textStyle = MaterialTheme.typography.body1,
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = MaterialTheme.colors.primary,
            unfocusedIndicatorColor = MaterialTheme.colors.background,
            focusedLabelColor = MaterialTheme.colors.secondary,
            unfocusedLabelColor = MaterialTheme.colors.textSecondary,
            backgroundColor = MaterialTheme.colors.surface.copy(alpha = 0.2f),
        ),
        shape = RoundedCornerShape(8.dp)
    )
}


private enum class LoginTextField(
    val hint: String,
    val keyboardType: KeyboardType,
    val visualTransformation: VisualTransformation,
    val imeAction: ImeAction
) {
    Username("Email", KeyboardType.Email, VisualTransformation.None, ImeAction.Next),
    Password("Password", KeyboardType.Password, PasswordVisualTransformation(), ImeAction.Go)
}

enum class AuthType(val buttonText: String) { Login("Login"), SignUp("Sign up"), SendLink("Send link") }

val AuthType.isLogin
    get() = this == AuthType.Login
