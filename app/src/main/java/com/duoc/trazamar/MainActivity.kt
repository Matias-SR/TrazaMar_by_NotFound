package com.duoc.trazamar

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.duoc.trazamar.ui.screens.LoginScreen
import com.duoc.trazamar.ui.theme.TrazaMarTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TrazaMarTheme {
                LoginScreen(
                    onLoginExitoso = {
                        Toast.makeText(this, "¡Login exitoso!", Toast.LENGTH_SHORT).show()
                    }
                )
            }
        }
    }
}