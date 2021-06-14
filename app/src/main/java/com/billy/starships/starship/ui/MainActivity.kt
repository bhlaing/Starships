package com.billy.starships.starship.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.billy.starships.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private lateinit var shipAdapter: ShipsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpStarshipsList()
    }


    private fun setUpStarshipsList() {
        shipAdapter = ShipsAdapter().apply { setOnFavListener { viewModel.onFav(it) } }

        binding.starShipList.apply {
            adapter = shipAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }
}