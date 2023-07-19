package hu.bme.aut.android.cookbook

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import hu.bme.aut.android.cookbook.anim.LoadingAnimation
import hu.bme.aut.android.cookbook.ui.theme.CookBookTheme
import java.util.*

class StarterActivity : ComponentActivity() {

    private var timer = Timer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CookBookTheme {
                // A surface container using the 'background' color from the theme
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    LoadingAnimation()
                }
            }
        }
        timedChangeToMainActivity()

    }

    private fun timedChangeToMainActivity() {
        timer = Timer()
        val  timeout: Long = 4800
        timer.schedule(object : TimerTask() {
            override fun run() {
                intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }, timeout)
    }
}