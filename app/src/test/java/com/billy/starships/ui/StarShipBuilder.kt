package com.billy.starships.ui

import com.billy.starships.starship.domain.model.StarshipFleet

fun buildStarShip(
    shipNumber: String = "1",
    name: String = "star1",
    model: String = "coca cola",
    manufacturer: String = "tesla"
) = StarshipFleet.Starship(shipNumber, name, model, manufacturer)

fun buildFleet(
    starShips: List<StarshipFleet.Starship> = listOf(buildStarShip()),
    previous: Boolean = false,
    next: Boolean = true
) = StarshipFleet(starShips, previous, next)