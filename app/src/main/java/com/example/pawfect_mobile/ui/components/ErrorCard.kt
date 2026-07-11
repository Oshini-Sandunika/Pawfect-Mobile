package com.example.pawfect_mobile.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pawfect_mobile.R

@Composable
@Preview
fun ErrorCard(details: String? = null, error: String? = null) {
    StyledCard {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painterResource(id = R.drawable.error),
                contentDescription = "",
                modifier = Modifier
                    .width(200.dp)
                    .alpha(0.7f)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Something went wrong",
                fontSize = 18.sp
            )
            if (!details.isNullOrEmpty()) {
                Text(
                    modifier = Modifier.fillMaxSize(0.6f),
                    textAlign = TextAlign.Center,
                    text = details,
                    fontSize = 14.sp
                )
            }
            Text(
                modifier = Modifier.fillMaxSize(0.6f),
                textAlign = TextAlign.Center,
                text = "Please try again later",
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            if (!error.isNullOrEmpty()) {
                Text(
                    textAlign = TextAlign.Center,
                    color = Color.Red,
                    text = error,
                    fontSize = 12.sp
                )
            }
        }
    }
}