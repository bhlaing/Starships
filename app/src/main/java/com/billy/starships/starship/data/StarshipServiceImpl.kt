package com.billy.starships.starship.data

import com.billy.starships.starship.data.StarshipServiceImpl.Companion.BASE_URL
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class StarshipServiceImpl(private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO) :
    StarshipService {
    companion object{
         const val BASE_URL = "https://swapi.dev/"
    }

    override suspend fun getStarships() = withContext(ioDispatcher) {
        val request = ServiceBuilder.buildService(StarshipClient::class.java)
        mapToStarShipFleet(request.getStarships())
    }

    interface StarshipClient {
        @GET(" /api/starships/")
        suspend fun getStarships(): StartShipsDO
    }
}

object ServiceBuilder {
    private val client = OkHttpClient.Builder().build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    fun <T> buildService(service: Class<T>): T {
        return retrofit.create(service)
    }
}