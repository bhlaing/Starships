package com.billy.starships.ui.home

import com.billy.starships.BaseCoroutinesTest
import com.billy.starships.starship.data.StarshipService
import com.billy.starships.starship.domain.exception.StarShipException
import com.billy.starships.starship.ui.home.MainViewModel
import com.billy.starships.starship.ui.shared.preferences.PreferenceManager
import com.billy.starships.ui.buildFleet
import com.billy.starships.ui.buildStarShip
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify

@ExperimentalCoroutinesApi
class MainViewModelTest : BaseCoroutinesTest() {
    private lateinit var viewModel: MainViewModel

    @Mock
    lateinit var preferenceManager: PreferenceManager

    @Mock
    lateinit var starShipService: StarshipService

    @Before
    override fun setUp() {
        super.setUp()

        viewModel = MainViewModel(starShipService, preferenceManager)
    }

    @Test
    fun `given a fleet of starships, when user has no preference, then does not display items as favourite`() {
        runBlocking {
            whenever(preferenceManager.getFavouriteStarshipNumbers()).thenReturn(emptyList())
            whenever(starShipService.getStarships()).thenReturn(buildFleet())

            viewModel.initialise()

            assertFalse(viewModel.starShips.value!!.first().fav)
        }
    }

    @Test
    fun `given a fleet of starships, when user has preference, then display items as favourite`() {
        runBlocking {
            whenever(preferenceManager.getFavouriteStarshipNumbers()).thenReturn(listOf("1", "3"))
            whenever(starShipService.getStarships()).thenReturn(
                buildFleet(
                    listOf(
                        buildStarShip("1", "name", "model", "manufacturer"),
                        buildStarShip("2", "name1", "model1", "manufacturer1"),
                        buildStarShip("3", "name2", "model2", "manufacturer2")
                    )
                )
            )

            viewModel.initialise()

            with(viewModel.starShips.value!!) {
                assertTrue(component1().fav)
                assertFalse(component2().fav)
                assertTrue(component3().fav)
            }
        }
    }

    @Test
    fun `given a fleet of starships, display correct attributes`() {
        runBlocking {
            whenever(preferenceManager.getFavouriteStarshipNumbers()).thenReturn(emptyList())
            whenever(starShipService.getStarships()).thenReturn(
                buildFleet(
                    listOf(
                        buildStarShip("2", "name1", "model1", "manufacturer1")
                    )
                )
            )

            viewModel.initialise()

            with(viewModel.starShips.value!!.first()) {
                assertEquals("2", number)
                assertEquals("name1", name)
                assertEquals("model1", model)
            }
        }
    }

    @Test
    fun `given a fleet of starships, when favourite is toggled, then update favourite preference`() {
        runBlocking {
            whenever(preferenceManager.getFavouriteStarshipNumbers()).thenReturn(emptyList())
            whenever(starShipService.getStarships()).thenReturn(buildFleet())

            viewModel.onFav("2")

            verify(preferenceManager).updateStarshipPreference("2")
        }
    }

    @Test
    fun `given a preference update, when successful, then refreshes starships`() {
        runBlocking {
            whenever(preferenceManager.getFavouriteStarshipNumbers()).thenReturn(emptyList())
            whenever(preferenceManager.updateStarshipPreference("2")).thenReturn(true)
            whenever(starShipService.getStarships()).thenReturn(buildFleet())

            viewModel.onFav("2")

            verify(preferenceManager).updateStarshipPreference("2")
            verify(starShipService).getStarships()
        }
    }

    @Test
    fun `given an exception, then displays exception message to user`() {
        runBlocking {
            whenever(preferenceManager.getFavouriteStarshipNumbers()).thenReturn(emptyList())
            whenever(starShipService.getStarships()).thenThrow(StarShipException("Oops"))

            viewModel.initialise()

            assertEquals("Oops", viewModel.error.value!!)
        }
    }
}