package com.example.pawfect_mobile.ui.screens.search.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.example.pawfect_mobile.ui.components.StyledCard

@Preview
@Composable
fun NoSearch() {
    StyledCard(modifier = Modifier) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(64.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Image(
                painter = painterResource(id = R.drawable.enter_search),
                contentDescription = "",
                modifier = Modifier
                    .height(120.dp)
                    .alpha(0.7f)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Search for your perfect pet!",
                fontSize = 18.sp
            )
            Text(
                modifier = Modifier.fillMaxSize(0.6f),
                textAlign = TextAlign.Center,
                lineHeight = 14.sp,
                text = "Enter your search and select a category to get started",
                fontSize = 12.sp
            )
        }
    }
}
