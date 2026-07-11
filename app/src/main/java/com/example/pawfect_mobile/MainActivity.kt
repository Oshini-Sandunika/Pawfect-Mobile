package com.example.pawfect_mobile

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.fragment.app.FragmentActivity
import com.example.pawfect_mobile.ui.layouts.AppLayout
import com.example.pawfect_mobile.ui.navigation.AppNavigation
import com.example.pawfect_mobile.ui.theme.AppTheme
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.enableEdgeToEdge()

        setContent {
            AppTheme(darkTheme = true) {
                AppNavigation()
            }
        }
    }
}

@Composable
fun Home() {
    AppLayout {
        Button({ Firebase.auth.signOut() }) { Text("Logout") }
    }
}