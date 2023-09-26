package com.elflin.movieapps.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.elflin.movieapps.R

@Composable
fun ProfileView(){

    var name by rememberSaveable { mutableStateOf("") }
    var phone by rememberSaveable { mutableStateOf("") }
    var age by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_profile),
            contentDescription = "Profile Picture",
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .border(2.dp, Color.Gray, CircleShape)
        )
        CustomTextField(
            value = name,
            onValueChanged = {name = it} ,
            text = "Name",
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        )
        CustomTextField(
            value = phone,
            onValueChanged = {phone = it} ,
            text = "Phone",
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Phone,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        )
        CustomTextField(
            value = age,
            onValueChanged = {age = it} ,
            text = "Age",
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        )
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
            isEmailValid = false
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
            isPasswordValid = false
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(
    value: String,
    onValueChanged: (String) -> Unit,
    text: String,
    keyboardOptions: KeyboardOptions,
    modifier: Modifier = Modifier
){
    OutlinedTextField(
        value = value,
        onValueChange = onValueChanged,
        label = { Text(text = text)},
        keyboardOptions = keyboardOptions,
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomEmailField(
    value: String,
    onValueChanged: (String) -> Unit,
    text: String,
    keyboardOptions: KeyboardOptions,
    modifier: Modifier = Modifier,
    isEmailValid: Boolean
){
    OutlinedTextField(
        value = value,
        onValueChange = onValueChanged,
        label = { Text(text = text)},
        keyboardOptions = keyboardOptions,
        modifier = modifier,
        isError = !isEmailValid
    )

    if (!isEmailValid){
        Text(
            text = "Invalid email format",
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, bottom = 4.dp),
            Color.Red
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomPasswordField(
    value: String,
    onValueChanged: (String) -> Unit,
    text: String,
    keyboardOptions: KeyboardOptions,
    modifier: Modifier = Modifier,
    isPasswordValid: Boolean
){

    var isPasswordVisible by remember { mutableStateOf(false)}

    OutlinedTextField(
        value = value,
        onValueChange = onValueChanged,
        label = { Text(text = text)},
        keyboardOptions = keyboardOptions,
        modifier = modifier,
        isError = !isPasswordValid,
        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            IconButton(
                onClick = { isPasswordVisible = !isPasswordVisible }
            ) {
                Icon(
                    imageVector = if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                    contentDescription = if (isPasswordVisible) "Hide password" else "Show password"
                )
            }
        },
    )

    if (!isPasswordValid){
        Text(
            text = "Password must be 8 characters long and contain uppercase, lowercase, number, and special character",
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, bottom = 4.dp),
            Color.Red
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProfilePreview(){
    ProfileView()
}