package com.sredna.balloonpop

import android.animation.ObjectAnimator
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.ImageView
import androidx.core.animation.doOnEnd
import kotlinx.android.synthetic.main.activity_balloonpop_game.*


class BalloonPopActivity : AppCompatActivity() {

    private lateinit var balloonCreator: Runnable
    private val handler: Handler = Handler()

    private val balloonLayoutIds: ArrayList<Int> = arrayListOf(
            R.layout.balloon_image_view_small,
            R.layout.balloon_image_view,
            R.layout.balloon_image_view_large
        )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_balloonpop_game)

        balloonCreator = Runnable {
            try {
                createBalloon()
            } finally {
                handler.postDelayed(balloonCreator, 1000)
            }
        }

        balloonCreator.run()
    }

    private fun createBalloon() {
        val balloon: ImageView = LayoutInflater.from(this).inflate(balloonLayoutIds.random(), layoutBalloonpopGame, false) as ImageView
        // Make the balloon invisible until it is placed where it is supposed to be to avoid flicker
        balloon.visibility = View.INVISIBLE
        layoutBalloonpopGame.addView(balloon)

        // Post so that it waits to render the balloon when the width and height will be available
        layoutBalloonpopGame.post {
            // Move the balloon the the middle of the screen
            val middleOfLayout = layoutBalloonpopGame.width / 2f
            balloon.x = middleOfLayout - balloon.width / 2f
            balloon.y = layoutBalloonpopGame.height.toFloat()

            val startPosition = layoutBalloonpopGame.bottom.toFloat()
            val endPosition = layoutBalloonpopGame.top.toFloat() - balloon.height
            val yAnimator = ObjectAnimator.ofFloat(balloon, View.TRANSLATION_Y, startPosition, endPosition)

            // How long it takes the balloon to move off the screen
            yAnimator.duration = 2500L

            // Show the balloon and start animating it
            balloon.visibility = View.VISIBLE
            yAnimator.start()
            yAnimator.doOnEnd {
                layoutBalloonpopGame.removeView(balloon)
            }
        }
    }
}