package com.billy.starships.starship.data

import com.billy.starships.starship.domain.model.StarshipFleet
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import javax.inject.Inject

class StarshipServiceImpl @Inject constructor() : StarshipService {

    val ioDispatcher: CoroutineDispatcher = Dispatchers.IO

    override suspend fun getStarships() = withContext(ioDispatcher) {
        try {
            val client = ServiceBuilder.buildService(StarshipClient::class.java)
            mapToStarShipFleet(client.getStarships())
        } catch (ex: Exception) {
            StarshipFleet(emptyList(), true, true)
        }
    }

    interface StarshipClient {
        @GET(" /api/starships/")
        suspend fun getStarships(): StartShipsDO
    }
}

object ServiceBuilder {
    private val client = OkHttpClient.Builder().build()
    private const val BASE_URL = "https://swapi.dev/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    fun <T> buildService(service: Class<T>): T {
        return retrofit.create(service)
    }
}