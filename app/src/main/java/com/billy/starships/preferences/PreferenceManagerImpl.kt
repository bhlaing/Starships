package com.billy.starships.preferences

import com.billy.starships.starship.ui.model.StarShipItem
import javax.inject.Inject

/**
 * In memory storage of user preferences
 * this class can be used to manage favourites, preferred sort methods, order of items etc..
 *
 * when scaling is required, we can inject services to make them asynchronous.
 * For now its a simple class that store presentation items.
 */
class PreferenceManagerImpl @Inject constructor() : PreferenceManager {
    private val favouriteStarShips: MutableSet<String> = mutableSetOf()

    // Return type is just a place holder to move to asynchronous flow of preferences
    // in this case we always consider it successful
    override fun updateStarshipPreference(shipNumber: String): Boolean {
        if (favouriteStarShips.contains(shipNumber)) {
            favouriteStarShips.remove(shipNumber)
        } else {
            favouriteStarShips.add(shipNumber)
        }
        return true
    }

    override fun getFavouriteSites(): List<String> = favouriteStarShips.toList()
}