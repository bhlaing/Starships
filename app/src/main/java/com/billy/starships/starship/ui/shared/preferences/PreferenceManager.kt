package com.billy.starships.starship.ui.shared.preferences

interface PreferenceManager {
    fun updateStarshipPreference(shipNumber: String): Boolean
    fun getFavouriteStarshipNumbers(): List<String>
}