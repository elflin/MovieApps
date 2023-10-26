package com.elflin.movieapps.repository

import com.elflin.movieapps.service.MovieDBServices
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AuthInterceptor(private val bearerToken: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val originalRequest = chain.request()
        val request = originalRequest.newBuilder()
            .header("Authorization", "Bearer $bearerToken")
            .build()
        return chain.proceed(request)
    }
}
class MovieDBContainer {

    companion object{
        val BASE_IMG = "https://image.tmdb.org/t/p/w500"
    }

    private val BASE_URL = "https://api.themoviedb.org/3/movie/"
    private val ACCESS_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJiZDJjNzcxM2ZkZjA5MTIxN2IzMWE5OTRkM2E4ZmI5YSIsInN1YiI6IjYyODQ3ZWY1Y2QyMDQ2MDBhOGI1ZTNiMiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.Kmxeqnk0KdRenlw8FBD40PV84JuP7n244AJb63WzYtg"

    private val client = OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor(ACCESS_TOKEN))
        .build()

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(client)
        .build()

    private val retrofitService: MovieDBServices by lazy{
        retrofit.create(MovieDBServices::class.java)
    }

    val movieDBRepositories: MovieDBRepositories by lazy{
        MovieDBRepositories(retrofitService)
    }
}
