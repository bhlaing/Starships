package com.billy.starships.starship.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.billy.starships.R
import com.billy.starships.databinding.ViewStarshipBinding
import com.billy.starships.starship.ui.model.StarShipItem

@SuppressLint("InflateParams")
class ShipsAdapter : RecyclerView.Adapter<ShipsAdapter.SpaceshipViewHolder>() {

    private var starShips: List<StarShipItem> = emptyList()
    private var onFavIconTappedListener: (String) -> Unit = {}
    private var onItemTappedListener: (String) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpaceshipViewHolder {
        return SpaceshipViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.view_starship, null)
        )
    }

    override fun onBindViewHolder(holderSpaceship: SpaceshipViewHolder, position: Int) =
        holderSpaceship.bind(starShips[position], onFavIconTappedListener, onItemTappedListener)


    override fun getItemCount() = starShips.size

    fun setOnFavListener(onFavListener: (String) -> Unit) {
        this.onFavIconTappedListener = onFavListener
    }

    fun setOnItemTappedListener(onItemTappedListener: (String) -> Unit) {
        this.onItemTappedListener = onItemTappedListener
    }

    fun setStarShips(starShips: List<StarShipItem>) {
        this.starShips = starShips
        notifyDataSetChanged()
    }

    fun updateStarShips(starShips: List<StarShipItem>, updatedItem: Int) {
        this.starShips = starShips
        notifyItemChanged(updatedItem)
    }

    inner class SpaceshipViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding = ViewStarshipBinding.bind(view)

        fun bind(
            starShipItem: StarShipItem,
            onFavListener: (String) -> Unit,
            onItemTappedListener: (String) -> Unit
        ) {
            with(starShipItem) {
                binding.shipName.text = name
                binding.shipModel.text = model
                binding.shipManufacturer.text = manufacturer
                binding.favIcon.alpha = if (fav) 1.0f else 0.5f

                binding.favIcon.setOnClickListener { onFavListener(number) }
                binding.root.setOnClickListener { onItemTappedListener(number) }
            }
        }
    }
}