package ru.netology.statsview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.netology.statsview.ui.StatsView
import ru.netology.statsview.ui.StatsViewText

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = findViewById<StatsView>(R.id.statsView)
        val viewText = findViewById<StatsViewText>(R.id.statsViewText)
        lifecycleScope.launch {
            delay(2000)
            view.data = listOf(
                500F,
                500F,
                500F,
                500F,
            )
            viewText.data = view.data

        }
//        view.startAnimation(
//            AnimationUtils.loadAnimation(this, R.anim.animation).apply {
//                setAnimationListener(object : Animation.AnimationListener {
//                    override fun onAnimationStart(p0: Animation?) {
//
//                    }
//
//                    override fun onAnimationEnd(p0: Animation?) {
//
//                    }
//
//                    override fun onAnimationRepeat(p0: Animation?) {
//
//                    }
//
//                })
//            }
//        )
        // Пример 3 ObjectAnimator через готовые property
//        ObjectAnimator.ofFloat(view, View.ALPHA, 0.25F, 1F).apply {
//            startDelay = 500
//            duration = 300
//            interpolator = BounceInterpolator()
//        }.start()
// Конец примера 3


// Пример 4 Анимация нескольких свойств через PropertyValuesHolder
//        val rotation = PropertyValuesHolder.ofFloat(View.ROTATION, 0F, 360F)
//        val alpha = PropertyValuesHolder.ofFloat(View.ALPHA, 0F, 1F)
//        ObjectAnimator.ofPropertyValuesHolder(view, rotation, alpha)
//            .apply {
//                startDelay = 500
//                duration = 500
//                interpolator = LinearInterpolator()
//            }.start()
// Конец примера 4

// Пример 5 Использование ViewPropertyAnimator
//        view.animate()
//            .rotation(360F)
//            .scaleX(1.2F)
//            .scaleY(1.2F)
//            .setInterpolator(LinearInterpolator())
//            .setStartDelay(500)
//            .setDuration(500)
//            .start()
// Конец примера 5

// Пример 6 Комбинация нескольких анимаций через AnimatorSet
//        val alpha = ObjectAnimator.ofFloat(view, View.ALPHA, 0.25F, 1F).apply {
//            duration = 300
//            interpolator = LinearInterpolator()
//        }
//        val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 0F, 1F)
//        val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 0F, 1F)
//        val scale = ObjectAnimator.ofPropertyValuesHolder(view, scaleX, scaleY).apply {
//            duration = 300
//            interpolator = BounceInterpolator()
//        }
//        AnimatorSet().apply {
//            startDelay = 500
//            playSequentially(scale, alpha)
//        }.start()
// Конец примера 6
    }
}