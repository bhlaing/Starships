package com.billy.starships.starship.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.billy.starships.R
import com.billy.starships.databinding.ActivityMainBinding
import com.billy.starships.shared.observeNonNull
import com.billy.starships.starship.ui.description.DescriptionActivity
import com.billy.starships.starship.ui.model.StarShipItem
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private lateinit var shipAdapter: ShipsAdapter
    private val loadingMessage by lazy {
        AlertDialog.Builder(this).apply {
            setMessage(getString(R.string.loading_fleet))
            setCancelable(false)
        }.create()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpStarshipsList()
        setUpObservers()
    }

    override fun onStart() {
        viewModel.initialise()

        super.onStart()
    }

    private fun setUpObservers() {
        observeNonNull(viewModel.starShips, ::onStarShipsReceived)
        observeNonNull(viewModel.loading, ::onLoadingStateChanged)
    }

    private fun onStarShipsReceived(starShipItems: List<StarShipItem>) {
        shipAdapter.setStarShips(starShipItems)
    }

    private fun onLoadingStateChanged(loading: Boolean) {
        if (loading) {
            loadingMessage.show()
        } else {
            if(loadingMessage.isShowing) loadingMessage.dismiss()
        }
    }

    private fun setUpStarshipsList() {
        shipAdapter = ShipsAdapter().apply {
            setOnFavListener { viewModel.onFav(it) }
            setOnItemTappedListener { navigateToDetails(it) }
        }

        binding.starShipList.apply {
            adapter = shipAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun navigateToDetails(shipNumber: String) =
        startActivity(DescriptionActivity.makeDescriptionIntent(this, shipNumber))

}