package com.billy.starships.starship.ui.description

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.billy.starships.starship.ui.shared.preferences.PreferenceManager
import com.billy.starships.starship.data.StarshipService
import com.billy.starships.starship.domain.model.StarshipFleet
import com.billy.starships.starship.ui.model.StarShipItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DescriptionViewModel @Inject constructor(
    private val preferenceManager: PreferenceManager,
    private val starshipService: StarshipService
) : ViewModel() {

    private val _starShip: MutableLiveData<StarShipItem> = MutableLiveData()
    val starShip: LiveData<StarShipItem> = _starShip

    private val _loading: MutableLiveData<Boolean> = MutableLiveData()
    val loading: LiveData<Boolean> = _loading

    fun initialise(shipNumber: String) = retrieveStarShip(shipNumber)

    fun onFav(shipNumber: String) {
        if(preferenceManager.updateStarshipPreference(shipNumber)) {
            retrieveStarShip(shipNumber)
        }
    }

    private fun retrieveStarShip(shipNumber: String) =
        viewModelScope.launch {
            _loading.value = true
            val ship = starshipService.getStarShipByNumber(shipNumber)
            _starShip.value = mapToStarShipItem(ship, preferenceManager.getFavouriteSites())
            _loading.value = false
        }

    private fun mapToStarShipItem(
        starShip: StarshipFleet.Starship,
        favs: List<String>
    ) =
        starShip.let {
            StarShipItem(it.shipNumber, it.name, it.model, it.manufacturer).apply {
                fav = favs.contains(number)
            }
        }
}