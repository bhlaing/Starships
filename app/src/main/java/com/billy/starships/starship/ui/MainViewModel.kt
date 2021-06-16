package com.billy.starships.starship.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.billy.starships.starship.ui.shared.preferences.PreferenceManager
import com.billy.starships.starship.data.StarshipService
import com.billy.starships.starship.domain.exception.StarShipException
import com.billy.starships.starship.domain.model.StarshipFleet
import com.billy.starships.starship.ui.model.StarShipItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val starshipService: StarshipService,
    private val preferenceManager: PreferenceManager
) : ViewModel() {

    private val _starShips: MutableLiveData<List<StarShipItem>> = MutableLiveData()
    val starShips: LiveData<List<StarShipItem>> = _starShips

    private val _loading: MutableLiveData<Boolean> = MutableLiveData()
    val loading: LiveData<Boolean> = _loading

    private val _error: MutableLiveData<String> = MutableLiveData()
    val error: LiveData<String> = _error

    fun onFav(shipNumber: String) {
       if(preferenceManager.updateStarshipPreference(shipNumber)) {
           retrieveStarShips()
       }
    }

    fun initialise() = retrieveStarShips()

    private fun retrieveStarShips() =
        viewModelScope.launch {
            _loading.value = true
            try {
                val fleet = starshipService.getStarships()
                _starShips.value =
                    mapToStarShipItems(fleet.starShips, preferenceManager.getFavouriteSites())
            } catch (ex: StarShipException) {
                _error.value = ex.message
            } finally {
                _loading.value = false

            }
        }

    private fun mapToStarShipItems(
        starShips: List<StarshipFleet.Starship>,
        favs: List<String>
    ) =
        starShips.map {
            StarShipItem(it.shipNumber, it.name, it.model, it.manufacturer).apply {
                fav = favs.contains(number)
            }
        }
}