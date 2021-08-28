package com.udacity.asteroidradar.detail

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.appcompat.app.AlertDialog
import androidx.core.animation.doOnRepeat
import androidx.fragment.app.Fragment
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentDetailBinding
import com.udacity.asteroidradar.databinding.FragmentMainBinding

class DetailFragment : Fragment() {
    lateinit var binding: FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val asteroid = DetailFragmentArgs.fromBundle(arguments!!).selectedAsteroid

        binding.asteroid = asteroid

        binding.helpButton.setOnClickListener {
            displayAstronomicalUnitExplanationDialog()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        initAnimation()
    }

    private fun displayAstronomicalUnitExplanationDialog() {
        val builder = AlertDialog.Builder(activity!!)
            .setMessage(getString(R.string.astronomica_unit_explanation))
            .setPositiveButton(android.R.string.ok, null)
        builder.create().show()
    }

    private fun initAnimation() {
        binding.fragmentDetailImageViewRocket.viewTreeObserver
            .addOnPreDrawListener(object :
                ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    binding.fragmentDetailImageViewRocket.viewTreeObserver.removeOnPreDrawListener(
                        this
                    )
                    setAnimationRocket()

                    return true
                }
            })

        setAnimationPlanet()
    }

    private fun setAnimationRocket() {
        val finalWidth = binding.fragmentDetailImageViewRocket.measuredWidth.toFloat()

        val transAnimationTranslationRocket: ObjectAnimator =
            ObjectAnimator.ofFloat(
                binding.fragmentDetailImageViewRocket,
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
                binding.fragmentDetailImageViewRocket,
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
                binding.fragmentDetailImagePlanet,
                "rotation",
                0f,
                360f
            )
        transAnimationRotationPlanet.duration = 500000
        transAnimationRotationPlanet.repeatCount = ValueAnimator.INFINITE
        transAnimationRotationPlanet.repeatMode = ValueAnimator.RESTART

        transAnimationRotationPlanet.start()
    }
}
