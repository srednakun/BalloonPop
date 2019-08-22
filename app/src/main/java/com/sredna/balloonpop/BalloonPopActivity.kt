package com.sredna.balloonpop

import android.animation.ObjectAnimator
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.ImageView
import androidx.core.content.res.ResourcesCompat
import kotlinx.android.synthetic.main.activity_balloonpop_game.*


class BalloonPopActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_balloonpop_game)
        createBalloon()
    }

    private fun createBalloon() {
        val balloon: ImageView = LayoutInflater.from(this).inflate(R.layout.balloon_image_view, layoutBalloonpopGame, false) as ImageView
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
            //how far the balloon has to go to start from the bottom of any screen
            //and float to the top of the screen and dissapear.
            //val balloonDistance =
            // How long it takes the balloon to move off the screen
            yAnimator.duration = 7000L


            // Show the balloon and start animating it
            balloon.visibility = View.VISIBLE
            yAnimator.start()
        }
    }
}