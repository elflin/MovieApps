package com.elflin.movieapps.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.elflin.movieapps.data.DataStoreManager
import com.elflin.movieapps.repository.MyDBContainer
import com.elflin.movieapps.ui.ListScreen
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class LoginViewModel: ViewModel() {

    fun ButtonLogin(
        email: String,
        password: String,
        context: Context,
        navController: NavController,
        dataStore: DataStoreManager
    ){
        viewModelScope.launch {
            val token = MyDBContainer().myDBRepositories.login(email, password)
            if(token.equals("Incorrect Password", true)){
                Toast.makeText(context, token, Toast.LENGTH_LONG).show()
            }else if(token.equals("User not found", true)){
                Toast.makeText(context, token, Toast.LENGTH_LONG).show()
            }else{
                navController.navigate(ListScreen.ListMovie.name){
                    popUpTo(ListScreen.Login.name){inclusive = true}
                }
                dataStore.saveToken(token)

                dataStore.getToken.collect{token->
                    if(token != null){
                        MyDBContainer.ACCESS_TOKEN = token
                    }
                }
            }
        }
    }

    fun ButtonRegister(){

    }

}