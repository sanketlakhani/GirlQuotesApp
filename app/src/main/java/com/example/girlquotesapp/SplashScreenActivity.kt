package com.example.girlquotesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager

class SplashScreenActivity : AppCompatActivity() {

    private val SPLASH_SCREEN_TIME_OUT=3000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        Handler().postDelayed(Runnable {
            val i = Intent(
                this,
                MainActivity::class.java
            )
            startActivity(i)
            finish()
        }, SPLASH_SCREEN_TIME_OUT.toLong())
    }
}