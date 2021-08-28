package com.udacity.asteroidradar.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.ItemAsteroidBinding
import com.udacity.asteroidradar.domain.Asteroid

class AsteroidRadarAdapter(val clickListenerAsteroidRadar: ClickAsteroidRadar) :
    ListAdapter<Asteroid, AsteroidRadarAdapter.ViewHolder>(AsteroidDiff()) {

    lateinit var clickListener: ClickAsteroidRadar

    init {
        clickListener = clickListenerAsteroidRadar
    }

    interface ClickAsteroidRadar {
        fun clickAsteroidRadar(asteroid: Asteroid)
    }

    class ViewHolder private constructor(private val binding: ItemAsteroidBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(asteroid: Asteroid, clickListener: ClickAsteroidRadar) {
            binding.asteroid = asteroid
            binding.clickListener = clickListener

            asteroid.isPotentiallyHazardous?.let {
                when (it) {
                    true -> R.drawable.planetarium
                    false -> R.drawable.shooting_star
                }
            }?.let {
                binding.isPotentiallyHazardousImageView.setImageResource(
                    it
                )
            }
        }

        companion object {
            fun create(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemAsteroidBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(
            getItem(position),
            clickListener
        )
    }
}

class AsteroidDiff : DiffUtil.ItemCallback<Asteroid>() {
    override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
        return oldItem == newItem
    }
}