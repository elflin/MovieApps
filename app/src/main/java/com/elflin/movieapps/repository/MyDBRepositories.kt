package com.elflin.movieapps.repository

import com.elflin.movieapps.model.User
import com.elflin.movieapps.service.MyDBService
import java.net.HttpURLConnection

class MyDBRepositories(private val myDBService: MyDBService) {

    suspend fun login(email: String, password: String): String{
        val user = User(email = email, password = password)
        val result = myDBService.login(user)
        if(result.status.toInt() == HttpURLConnection.HTTP_OK){
            return result.data as String
        }
        return result.message
    }

    suspend fun logout(): String{
        val result = myDBService.logout()
        return result.message
    }

    suspend fun register(user: User): String{
        val result = myDBService.register(user)
        if(result.status.toInt() == HttpURLConnection.HTTP_OK){
            return result.data as String
        }
        return result.message
    }

}