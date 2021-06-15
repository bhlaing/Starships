package com.billy.starships.preferences

interface PreferenceManager {
    fun updateStarshipPreference(shipNumber: String): Boolean
    fun getFavouriteSites(): List<String>
}