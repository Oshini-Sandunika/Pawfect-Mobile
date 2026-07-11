package com.example.pawfect_mobile.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pawfect_mobile.R
import com.example.pawfect_mobile.ui.layouts.AppLayout

@Preview
@Composable
fun SplashScreen() {
    AppLayout(bgAlpha = 0.6f, noScroll =true) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column (
                modifier = Modifier.background(Color.White.copy(alpha = 0.6f)).padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ){
            Image(
                painter = painterResource(id = R.drawable.pawfect_logo),
                contentDescription = "Pawfect Logo",
                modifier = Modifier.size(160.dp)
            )
            Text(
                text = "Pawfect",
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 16.dp)
            )
            Text(
                text = "Where paws meet their perfect match",
                fontSize = 16.sp,
                modifier = Modifier.padding(top = 8.dp)
            )
            }
        }
    }
}
