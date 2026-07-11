package com.example.pawfect_mobile

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.fragment.app.FragmentActivity
import androidx.fragment.compose.AndroidFragment
import com.example.pawfect_mobile.fragments.HomeFragment
import com.example.pawfect_mobile.ui.layouts.AppLayout
import com.example.pawfect_mobile.ui.theme.AppTheme

class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.enableEdgeToEdge()

        setContent {
            AppTheme(darkTheme = true) {
                Home()
            }
        }
    }
}

@Composable
fun Home() {
    AppLayout {
        AndroidFragment<HomeFragment>()
    }
}