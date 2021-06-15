package com.billy.starships.starship.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.billy.starships.databinding.ActivityDescriptionBinding
import com.billy.starships.shared.observeNonNull
import com.billy.starships.starship.ui.model.StarShipItem
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DescriptionActivity : AppCompatActivity() {

    private val viewModel: DescriptionViewModel by viewModels()
    private lateinit var binding: ActivityDescriptionBinding

    companion object {
        private const val SHIP_NUMBER = "shipNumber"

        fun makeDescriptionIntent(context: Context, shipNumber: String) =
            Intent(context, DescriptionActivity::class.java).apply {
                putExtra(SHIP_NUMBER, shipNumber)
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDescriptionBinding.inflate(layoutInflater)

        setContentView(binding.root)

        viewModel.initialise(intent.getStringExtra(SHIP_NUMBER)!!)
        setUpObservers()
    }

    private fun setUpObservers() {
        observeNonNull(viewModel.starShip, ::onStarShipReceived)
    }

    private fun onStarShipReceived(starShipItem: StarShipItem) {
        with(starShipItem) {
            binding.descriptionLayout.shipName.text = name
            binding.descriptionLayout.shipModel.text = model
            binding.descriptionLayout.shipManufacturer.text = manufacturer
            binding.descriptionLayout.favIcon.alpha = if (fav) 1.0f else 0.5f

            binding.descriptionLayout.favIcon.setOnClickListener { viewModel.onFav(number) }
        }
    }
}