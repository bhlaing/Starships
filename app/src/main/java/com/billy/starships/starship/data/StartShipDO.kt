package com.billy.starships.starship.data

import com.google.gson.annotations.SerializedName

class StartShipsDO(
    @SerializedName("count")
    val count: String,
    @SerializedName("next")
    val next: String,
    @SerializedName("previous")
    val previous: String,
    @SerializedName("results")
    val results: List<StarShip>) {
    class StarShip(
        @SerializedName("name")
        val name: String,
        @SerializedName("model")
        val model: String,
        @SerializedName("manufacturer")
        val manufacturer: String,
        @SerializedName("cost_in_credits")
        val costInCredits: String,
        @SerializedName("length")
        val length: String,
        @SerializedName("max_atmosphering_speed")
        val maxAtmospheringSpeed: String,
        @SerializedName("crew")
        val crew: String,
        @SerializedName("passengers")
        val passengers: String,
        @SerializedName("cargo_capacity")
        val cargoCapacity: String,
        @SerializedName("consumables")
        val cosumables: String,
        @SerializedName("hyperdrive_rating")
        val hyperdriveRating: String,
        @SerializedName("MGLT")
        val mglt: String,
        @SerializedName("starship_class")
        val starshipClass: String,
        @SerializedName("pilots")
        val pilots: List<String>,
        @SerializedName("films")
        val films: List<String>
    )
}