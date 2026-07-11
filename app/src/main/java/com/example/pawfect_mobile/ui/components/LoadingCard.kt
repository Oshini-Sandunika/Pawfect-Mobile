package com.example.pawfect_mobile.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pawfect_mobile.R

@Composable
@Preview
fun Loading() {
    StyledCard(modifier = Modifier) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(64.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painterResource(id = R.drawable.loading),
                contentDescription = "",
                modifier = Modifier
                    .height(120.dp)
                    .alpha(0.7f)
            )
            Spacer(modifier = Modifier.height(20.dp))
            LinearProgressIndicator()
            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Loading....",
                textAlign = TextAlign.Center,
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.height(10.dp))

            Text(
                textAlign = TextAlign.Center,
                lineHeight = 14.sp,
                text = "Fetching the latest data from our server",
                fontSize = 12.sp
            )
        }
    }
}