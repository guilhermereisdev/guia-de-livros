package com.guilhermereisapps.guiadelivros.screens.login

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.guilhermereisapps.guiadelivros.R
import com.guilhermereisapps.guiadelivros.components.EmailInput
import com.guilhermereisapps.guiadelivros.components.PasswordInput
import com.guilhermereisapps.guiadelivros.components.ReaderLogo
import com.guilhermereisapps.guiadelivros.components.SubmitButton

@Preview
@Composable
fun LoginScreen(
    navController: NavController = NavController(LocalContext.current),
    viewModel: LoginViewModel = viewModel(),
) {
    val showLoginForm = rememberSaveable { mutableStateOf(true) }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
            ) {
                ReaderLogo()
                if (showLoginForm.value) {
                    UserForm { email, password ->
                        Log.d("Form", "ReaderLoginScreen: $email $password")
                        // TODO: Fazer o Login no Firebase
                    }
                } else {
                    UserForm(loading = false, isCreateAccount = true) { email, password ->
                        // TODO: Criar a conta no Firebase
                    }
                }
            }
            Row(
                modifier = Modifier.padding(16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Top,
            ) {
                val newUserText =
                    if (showLoginForm.value) "É um novo usuário?" else "Já possui conta?"
                val createAccountText = if (showLoginForm.value) "Crie uma conta" else "Entre"

                Text(text = newUserText)
                Text(
                    text = createAccountText,
                    modifier = Modifier
                        .clickable {
                            showLoginForm.value = !showLoginForm.value
                        }
                        .padding(start = 5.dp),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                )
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun UserForm(
    loading: Boolean = false,
    isCreateAccount: Boolean = false,
    onDone: (String, String) -> Unit = { email, password -> }
) {
    val email = rememberSaveable { mutableStateOf("") }
    val password = rememberSaveable { mutableStateOf("") }
    val passwordVisibility = rememberSaveable { mutableStateOf(false) }
//    val passwordFocusRequest = FocusRequester.Default
    val passwordFocusRequest = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    val valid = remember(email.value, password.value) {
        email.value.trim().isNotEmpty() && password.value.trim().isNotEmpty()
    }
    val modifier = Modifier
//        .background(MaterialTheme.colorScheme.background)
        .padding(top = 16.dp)
        .verticalScroll(rememberScrollState())

    Column(modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        val createOrLoginLabel: String?
        val createOrLoginDescription: String?

        if (isCreateAccount) {
            createOrLoginLabel = stringResource(id = R.string.create_account_label)
            createOrLoginDescription = stringResource(id = R.string.create_account_description)
        } else {
            createOrLoginLabel = stringResource(id = R.string.login_account_label)
            createOrLoginDescription = stringResource(id = R.string.login_account_description)
        }

        Text(text = createOrLoginLabel)
        Text(
            text = createOrLoginDescription,
            textAlign = TextAlign.Center,
        )
        EmailInput(
            emailState = email,
            enabled = !loading,
            onAction = KeyboardActions {
                passwordFocusRequest.requestFocus()
            },
        )
        PasswordInput(
            modifier = Modifier.focusRequester(passwordFocusRequest),
            passwordState = password,
            enabled = !loading,
            passwordVisibility = passwordVisibility,
            onAction = KeyboardActions {
                if (!valid) return@KeyboardActions
                onDone(email.value.trim(), password.value.trim())
                keyboardController?.hide()
            },
        )
        SubmitButton(
            modifier = Modifier
                .padding(top = 16.dp, start = 32.dp, end = 32.dp),
            textId = if (isCreateAccount) "Criar Conta" else "Entrar",
            loading = loading,
            validInputs = valid,
        ) {
            onDone(email.value.trim(), password.value.trim())
            keyboardController?.hide()
        }
    }
}
