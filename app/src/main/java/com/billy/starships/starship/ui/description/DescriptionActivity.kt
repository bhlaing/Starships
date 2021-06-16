package com.billy.starships.starship.ui.description

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.View.VISIBLE
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import com.billy.starships.R
import com.billy.starships.databinding.ActivityDescriptionBinding
import com.billy.starships.shared.observeNonNull
import com.billy.starships.starship.ui.model.StarShipItem
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DescriptionActivity : AppCompatActivity() {

    private val viewModel: DescriptionViewModel by viewModels()
    private lateinit var binding: ActivityDescriptionBinding
    private val loadingMessage by lazy {
        AlertDialog.Builder(this).apply {
            setMessage(getString(R.string.loading_details))
            setCancelable(false)
        }.create()
    }

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
        showBackButton(binding.toolBar)

        // we know ship number is never null so !!
        // alternatively could have a safety check here to finish the activity
        viewModel.initialise(intent.getStringExtra(SHIP_NUMBER)!!)
        setUpObservers()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun setUpObservers() {
        observeNonNull(viewModel.starShip, ::onStarShipReceived)
        observeNonNull(viewModel.loading, ::onLoadingStateChanged)
        observeNonNull(viewModel.error, ::onError)
    }

    private fun onError(message: String) {
        val snackBar = Snackbar.make(binding.root, message, Snackbar.LENGTH_INDEFINITE)
        val retryAction = {
            viewModel.initialise(intent.getStringExtra(SHIP_NUMBER)!!)
            snackBar.dismiss()
        }

        snackBar.setAction("retry") { retryAction() }
            .show()
    }

    private fun onStarShipReceived(starShipItem: StarShipItem) {
        with(starShipItem) {
            binding.descriptionLayout.shipName.text = name
            binding.descriptionLayout.shipModel.text = model
            binding.descriptionLayout.shipManufacturer.text = manufacturer
            binding.descriptionLayout.favIcon.alpha = if (fav) 1.0f else 0.5f

            binding.descriptionLayout.favIcon.setOnClickListener { viewModel.onFav(number) }

            binding.descriptionLayout.layoutStarshipWrapper.visibility = VISIBLE
        }
    }

    private fun onLoadingStateChanged(loading: Boolean) {
        if (loading) {
            loadingMessage.show()
        } else {
            if(loadingMessage.isShowing) loadingMessage.dismiss()
        }
    }

    private fun showBackButton(toolbar: Toolbar) {
        setSupportActionBar(toolbar)

        if (supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowHomeEnabled(true)
        }
    }
}