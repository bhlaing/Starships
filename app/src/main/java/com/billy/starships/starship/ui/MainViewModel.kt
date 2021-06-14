package com.billy.starships.starship.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.billy.starships.starship.data.StarshipService
import com.billy.starships.starship.domain.model.StarshipFleet
import com.billy.starships.starship.ui.model.StarShipItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val starshipService: StarshipService): ViewModel() {

    private val _starShips: MutableLiveData<List<StarShipItem>> = MutableLiveData()
    val starShips: LiveData<List<StarShipItem>> = _starShips

    fun onFav(position: Int) {
    }

    fun initialise() = retrieveStarShips()


    private fun retrieveStarShips() {
        viewModelScope.launch {
            val fleet = starshipService.getStarships()
            _starShips.value = mapToStarShipItems(fleet.starShips)
        }
    }

    private fun mapToStarShipItems(starShips: List<StarshipFleet.Starship>) =
        starShips.map { StarShipItem(it.name, it.model, it.manufacturer, true) }


}