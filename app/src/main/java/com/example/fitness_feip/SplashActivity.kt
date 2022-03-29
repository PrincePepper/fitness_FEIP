package com.example.fitness_feip

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity


class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        startSplashAmimation()
    }

    private fun startSplashAmimation() {
        val SPLASH_DISPLAY = 1000
        Handler().postDelayed({
            val SplashIntent = Intent(this@SplashActivity, MainActivity::class.java)
            this@SplashActivity.startActivity(SplashIntent)
            finish()
        }, SPLASH_DISPLAY.toLong())
    }
}