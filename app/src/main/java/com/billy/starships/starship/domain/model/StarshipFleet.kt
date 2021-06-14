package com.billy.starships.starship.domain.model

class StarshipFleet(
    val starShips: List<Starship>,
    val previous: Boolean = false,
    val next: Boolean = true
) {
    class Starship(val name: String, val model: String, val manufacturer: String)
}

