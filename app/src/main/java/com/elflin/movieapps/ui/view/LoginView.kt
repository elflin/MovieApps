package com.elflin.movieapps.ui.view

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.elflin.movieapps.data.DataStoreManager
import com.elflin.movieapps.viewmodel.LoginViewModel
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginView(
    loginViewModel: LoginViewModel,
    navController: NavController,
    dataStore: DataStoreManager
){
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    var isEmailValid by rememberSaveable { mutableStateOf(true) }
    var isPasswordValid by rememberSaveable { mutableStateOf(true) }

    val context = LocalContext.current

    Scaffold(
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CustomEmailField(
                    value = email,
                    onValueChanged = {email = it} ,
                    text = "Email",
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    isEmailValid = isEmailValid
                )
                CustomPasswordField(
                    value = password,
                    onValueChanged = {password = it} ,
                    text = "Password",
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    isPasswordValid = isPasswordValid
                )

                Button(
                    onClick = {
                        isEmailValid = isValidEmail(email)
                        isPasswordValid = isValidPassword(password)

                        if (isEmailValid && isPasswordValid){
                            loginViewModel.ButtonLogin(
                                email,
                                password,
                                context,
                                navController,
                                dataStore
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 16.dp),
                    enabled = email.isNotBlank()&&password.isNotBlank()
                )
                {
                    Text(text = "Login")
                }

                Button(
                    onClick = {

                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 16.dp),
                )
                {
                    Text(text = "Register")
                }
            }
        }
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun LoginPreview(){
//    LoginView()
}