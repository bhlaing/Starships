package com.billy.starships.ui.preference

import com.billy.starships.starship.ui.shared.preferences.PreferenceManagerImpl
import org.junit.Assert.assertEquals
import org.junit.Test

class PreferenceManagerTest {
    private val preferenceManagerImpl = PreferenceManagerImpl()

    @Test
    fun `given an update to favourite ships, when updating a new ship, then adds it to favourite list`() {
        preferenceManagerImpl.updateStarshipPreference("1")

        assertEquals("1", preferenceManagerImpl.getFavouriteStarshipNumbers().first())
    }

    @Test
    fun `given an update to favourite ship, when updating existing ship, then removes it`() {
        preferenceManagerImpl.updateStarshipPreference("2")
        preferenceManagerImpl.updateStarshipPreference("1")

        assertEquals(0, preferenceManagerImpl.getFavouriteStarshipNumbers().size)
    }
}