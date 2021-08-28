package com.udacity.asteroidradar.main.view

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentMainBinding
import androidx.core.animation.doOnRepeat
import android.view.ViewTreeObserver.*
import androidx.lifecycle.observe
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.database.AsteroidRadarDatabase
import com.udacity.asteroidradar.domain.Asteroid
import com.udacity.asteroidradar.domain.PictureOfDay
import com.udacity.asteroidradar.main.adapter.AsteroidRadarAdapter
import com.udacity.asteroidradar.main.viewModel.MainViewModel
import com.udacity.asteroidradar.main.viewModel.MainViewModelFactory

class MainFragment : Fragment(), AsteroidRadarAdapter.ClickAsteroidRadar {

    lateinit var binding: FragmentMainBinding
    private lateinit var asteroidRadarAdapter: AsteroidRadarAdapter

    private val viewModel: MainViewModel by lazy {
        val database = AsteroidRadarDatabase.getInstance(requireContext())
        val mainViewModelFactory = MainViewModelFactory(database)
        ViewModelProvider(this, mainViewModelFactory).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        initAnimation()
        initListAsteroid()
        initObservers()
    }

    private fun initListAsteroid() {
        asteroidRadarAdapter = AsteroidRadarAdapter(this)

        binding.asteroidRecycler.layoutManager = LinearLayoutManager(this.context)
        binding.asteroidRecycler.adapter = asteroidRadarAdapter
    }

    private fun initObservers() {
        viewModel.pictureOfDay.observe(viewLifecycleOwner) { setPictureOfDay(it) }
        viewModel.getOnAsteroids().observe(viewLifecycleOwner) {
            asteroidRadarAdapter.submitList(it)
        }
    }

    private fun initAnimation() {
        binding.activityMainImageViewRocket.viewTreeObserver
            .addOnPreDrawListener(object :
                OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    binding.activityMainImageViewRocket.viewTreeObserver.removeOnPreDrawListener(
                        this
                    )
                    setAnimationRocket()

                    return true
                }
            })

        setAnimationPlanet()
    }

    private fun setAnimationRocket() {
        val finalWidth = binding.activityMainImageViewRocket.measuredWidth.toFloat()

        val transAnimationTranslationRocket: ObjectAnimator =
            ObjectAnimator.ofFloat(
                binding.activityMainImageViewRocket,
                "X",
                0 - finalWidth,
                resources.displayMetrics.widthPixels.toFloat() + finalWidth
            )

        transAnimationTranslationRocket.duration = 20000
        transAnimationTranslationRocket.repeatCount = ValueAnimator.INFINITE
        transAnimationTranslationRocket.repeatMode = ValueAnimator.RESTART
        transAnimationTranslationRocket.doOnRepeat { animator ->
            animator.pause()
            Handler(Looper.getMainLooper()).postDelayed({
                animator.start()
            }, 5000)
        }

        val transAnimationRotationRocket: ObjectAnimator =
            ObjectAnimator.ofFloat(
                binding.activityMainImageViewRocket,
                "rotation",
                0f,
                360f
            )
        transAnimationRotationRocket.duration = 10000
        transAnimationRotationRocket.repeatCount = ValueAnimator.INFINITE
        transAnimationRotationRocket.repeatMode = ValueAnimator.RESTART

        transAnimationTranslationRocket.start()
        transAnimationRotationRocket.start()
    }

    private fun setAnimationPlanet() {
        val transAnimationRotationPlanet: ObjectAnimator =
            ObjectAnimator.ofFloat(
                binding.activityMainImagePlanet,
                "rotation",
                0f,
                360f
            )
        transAnimationRotationPlanet.duration = 500000
        transAnimationRotationPlanet.repeatCount = ValueAnimator.INFINITE
        transAnimationRotationPlanet.repeatMode = ValueAnimator.RESTART

        transAnimationRotationPlanet.start()
    }

    private fun setPictureOfDay(pictureOfDay: PictureOfDay) {
        Picasso.with(requireContext())
            .load(pictureOfDay.url)
            .into(binding.activityMainImageOfTheDay)
    }

    override fun clickAsteroidRadar(asteroid: Asteroid) {
        findMyNavController(this).navigate(
            MainFragmentDirections
                .actionShowDetail(asteroid)
        )
    }

    private fun findMyNavController(fragment: Fragment): NavController {
        return Navigation.findNavController(binding.root)
    }
}
