package com.billy.starships.starship.ui.shared.preferences

import javax.inject.Inject

/**
 * In memory storage of user preferences
 * this class can be used to manage favourites, preferred sort methods, order of items etc..
 *
 * when scaling is required, we can inject services to make them asynchronous.
 * For now its a simple class that store ship numbers.
 */
class PreferenceManagerImpl @Inject constructor() : PreferenceManager {
    private val favouriteStarShips: MutableSet<String> = mutableSetOf()

    // Return type is just a place holder to move to asynchronous update of preferences
    // where we may need to provide feedback of a remote operation.
    //
    // In this case we always consider it successful for demoing purpose
    override fun updateStarshipPreference(shipNumber: String): Boolean {
        if (!favouriteStarShips.add(shipNumber)) {
            favouriteStarShips.remove(shipNumber)
        }

        return true
    }

    override fun getFavouriteSites(): List<String> = favouriteStarShips.toList()
}