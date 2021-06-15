package com.billy.starships.starship.ui.model

data class StarShipItem(
    val number: String,
    val name: String,
    val model: String,
    val manufacturer: String,
    var fav: Boolean = false
)