package com.billy.starships.starship.data

import com.billy.starships.starship.domain.model.StarshipFleet

interface StarshipService {
    suspend fun getStarships(): StarshipFleet
}