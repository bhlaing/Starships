package com.billy.starships.ui.description

import com.billy.starships.BaseCoroutinesTest
import com.billy.starships.starship.data.StarshipService
import com.billy.starships.starship.domain.exception.StarShipException
import com.billy.starships.starship.ui.description.DescriptionViewModel
import com.billy.starships.starship.ui.shared.preferences.PreferenceManager
import com.billy.starships.ui.buildStarShip
import junit.framework.Assert.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify

@ExperimentalCoroutinesApi
class DescriptionViewModelTest : BaseCoroutinesTest() {
    private lateinit var viewModel: DescriptionViewModel

    @Mock
    lateinit var preferenceManager: PreferenceManager

    @Mock
    lateinit var starShipService: StarshipService

    @Before
    override fun setUp() {
        super.setUp()

        viewModel = DescriptionViewModel(preferenceManager, starShipService)
    }

    @Test
    fun `given a ship number then retrieves starship correctly`() {
        runBlocking {
            whenever(preferenceManager.getFavouriteStarshipNumbers()).thenReturn(emptyList())
            whenever(starShipService.getStarShipByNumber("1")).thenReturn(buildStarShip())

            viewModel.initialise("1")

            verify(starShipService).getStarShipByNumber("1")
        }
    }

    @Test
    fun `given a ship when ship number matches user preference, then display it as favourite`() {
        runBlocking {
            whenever(preferenceManager.getFavouriteStarshipNumbers()).thenReturn(listOf("1"))
            whenever(starShipService.getStarShipByNumber("1")).thenReturn(
                buildStarShip("1", "name1", "model1", "manufacturer1")
            )

            viewModel.initialise("1")

            verify(starShipService).getStarShipByNumber("1")

            with(viewModel.starShip.value!!) {
                assertTrue(fav)
            }
        }
    }

    @Test
    fun `given a ship when ship does not matche user preference, then does not display it as favourite`() {
        runBlocking {
            whenever(preferenceManager.getFavouriteStarshipNumbers()).thenReturn(listOf("1"))
            whenever(starShipService.getStarShipByNumber("1")).thenReturn(
                buildStarShip("2", "name1", "model1", "manufacturer1")
            )

            viewModel.initialise("1")

            verify(starShipService).getStarShipByNumber("1")

            with(viewModel.starShip.value!!) {
                assertFalse(fav)
            }
        }
    }

    @Test
    fun `given a ship  when ship does not match user preference, then does not display it as favourite`() {
        runBlocking {
            whenever(preferenceManager.getFavouriteStarshipNumbers()).thenReturn(listOf("1"))
            whenever(starShipService.getStarShipByNumber("1")).thenReturn(
                buildStarShip("2", "name1", "model1", "manufacturer1")
            )

            viewModel.initialise("1")

            verify(starShipService).getStarShipByNumber("1")

            with(viewModel.starShip.value!!) {
                assertFalse(fav)
            }
        }
    }

    @Test
    fun `given a ship then displays details correctly`() {
        runBlocking {
            whenever(preferenceManager.getFavouriteStarshipNumbers()).thenReturn(listOf("1"))
            whenever(starShipService.getStarShipByNumber("1")).thenReturn(
                buildStarShip("2", "name1", "model1", "manufacturer1")
            )

            viewModel.initialise("1")

            verify(starShipService).getStarShipByNumber("1")

            with(viewModel.starShip.value!!) {
                assertFalse(fav)

                assertEquals("2", number)
                assertEquals("name1", name)
                assertEquals("model1", model)
                assertEquals("manufacturer1", manufacturer)
            }
        }
    }

    @Test
    fun `given a starship, when favourite is toggled, then update favourite preference`() {
        runBlocking {
            whenever(preferenceManager.getFavouriteStarshipNumbers()).thenReturn(emptyList())

            viewModel.onFav("2")

            verify(preferenceManager).updateStarshipPreference("2")
        }
    }

    @Test
    fun `given a favourite toggle, when success, then refreshes details`() {
        runBlocking {
            whenever(preferenceManager.getFavouriteStarshipNumbers()).thenReturn(emptyList())
            whenever(starShipService.getStarShipByNumber("2")).thenReturn(
                buildStarShip("2", "name1", "model1", "manufacturer1")
            )
            whenever(preferenceManager.updateStarshipPreference("2")).thenReturn(true)

            viewModel.onFav("2")

            verify(preferenceManager).updateStarshipPreference("2")
            verify(starShipService).getStarShipByNumber("2")
        }
    }

    @Test
    fun `given an exception, then displays exception message`() {
        runBlocking {
            whenever(preferenceManager.getFavouriteStarshipNumbers()).thenReturn(emptyList())
            whenever(starShipService.getStarShipByNumber("1")).thenThrow(StarShipException("Oops"))

            viewModel.initialise("1")

            Assert.assertEquals("Oops", viewModel.error.value!!)
        }
    }
}