package com.billy.starships.starship.data

import com.billy.starships.starship.domain.exception.StarShipException
import com.billy.starships.starship.domain.model.StarshipFleet
import kotlin.jvm.Throws

interface StarshipService {

    @Throws(StarShipException::class)
    suspend fun getStarships(): StarshipFleet

    @Throws(StarShipException::class)
    suspend fun getStarShipByNumber(starShipNumber: String): StarshipFleet.Starship
}