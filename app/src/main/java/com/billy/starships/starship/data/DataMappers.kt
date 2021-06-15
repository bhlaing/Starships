package com.billy.starships.starship.data

import com.billy.starships.starship.domain.model.StarshipFleet

fun mapToStarShipFleet(startShipsDO: StartShipsDO?) =
    StarshipFleet(
        startShipsDO?.results?.map {
            mapStarShip(it)
        } ?: throw IllegalStateException("No army in the fleet!"),
        !startShipsDO.next.isNullOrBlank(),
        !startShipsDO.previous.isNullOrBlank(),
    )
fun mapStarShip(starShip: StartShipsDO.StarShip) = starShip.let {
    StarshipFleet.Starship(
        formatShipNumber(it.url),
        it.name,
        it.model,
        it.manufacturer)
}

private fun formatShipNumber(urlString: String) =
    urlString.removePrefix("http://swapi.dev/api/starships/")
        .removeSuffix("/")