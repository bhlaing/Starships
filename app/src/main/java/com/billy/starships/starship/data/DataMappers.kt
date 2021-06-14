package com.billy.starships.starship.data

import com.billy.starships.starship.domain.model.StarshipFleet

fun mapToStarShipFleet(startShipsDO: StartShipsDO?) =
    StarshipFleet(
        startShipsDO?.results?.map {
            StarshipFleet.Starship(it.name, it.model, it.manufacturer)
        } ?: throw IllegalStateException("No army in the fleet!"),
        startShipsDO.next.isNotBlank(),
        startShipsDO.previous.isNotBlank()
    )
