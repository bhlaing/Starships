package com.billy.starships.starship.data

import com.billy.starships.starship.domain.exception.StarShipException
import com.billy.starships.starship.domain.model.StarshipFleet
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import javax.inject.Inject

class StarshipServiceImpl @Inject constructor() : StarshipService {

    val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
    private val client = ServiceBuilder.buildService(StarshipClient::class.java)


    override suspend fun getStarships() = withContext(ioDispatcher) {
        try {
            mapToStarShipFleet(client.getStarships())
        } catch (ex: Exception) {
            // Catch any exception here to re-throw as domain exception
            // to demonstrate exception handling
            throw StarShipException(ex.message)
        }
    }

    override suspend fun getStarShipByNumber(starShipNumber: String) = withContext(ioDispatcher) {
        try {
            mapStarShip(client.getStarShip(starShipNumber))
        } catch (ex: Exception) {
            // Catch any exception here to re-throw as domain exception
            //  to demonstrate exception handling
            throw StarShipException(ex.message)
        }
    }

    interface StarshipClient {
        @GET(" /api/starships/")
        suspend fun getStarships(): StartShipsDO

        @GET(" /api/starships/{shipNumber}")
        suspend fun getStarShip(@Path("shipNumber") shipNumber: String): StartShipsDO.StarShip
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