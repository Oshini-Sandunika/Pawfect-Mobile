package com.example.pawfect_mobile

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import com.example.pawfect_mobile.data.AuthService
import com.example.pawfect_mobile.ui.navigation.AppNavigation
import com.example.pawfect_mobile.ui.theme.AppTheme
import kotlinx.coroutines.launch

class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)
        this.enableEdgeToEdge()

        lifecycleScope.launch {
            AuthService.initialize()
        }

        setContent {
            AppTheme(darkTheme = false) {
                AppNavigation()
            }
        }
    }
}
