package com.example.pawfect_mobile.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pawfect_mobile.R
import com.example.pawfect_mobile.ui.components.TextInput
import com.example.pawfect_mobile.ui.layouts.AppLayout

@Preview
@Composable
fun Login() {
    AppLayout {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {

            Row(
                modifier = Modifier
                    .height(120.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.pawfect_logo),
                    contentDescription = "",
                    modifier = Modifier.size(96.dp)
                )
                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxHeight()
                ) {
                    Text("Pawfect", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Text("Where paws meet their perfect match", fontSize = 12.sp)
                }
            }

            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Surface(shadowElevation = 12.dp) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(32.dp)
                    ) {
                        Text("Welcome back!", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                        Text(
                            "Enter your login details to continue",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Thin,
                            modifier = Modifier.padding(bottom = 24.dp)
                        )

                        TextInput("Email")
                        TextInput("Password")

                        Button({}) {
                            Text("Login")
                        }
                    }
                }
            }
        }

    }
}