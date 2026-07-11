package com.example.pawfect_mobile.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pawfect_mobile.ui.theme.AppTheme

@Composable
fun TextInput(
    label: String,
    required: Boolean = false,
    description: String = ""
) {
    val state = remember { TextFieldState() }

    Column() {
        Row(modifier = Modifier.padding(bottom = 4.dp)) {
            Text(label)
            if (required) {
                Text(
                    "*",
                    fontSize = 14.sp,
                    modifier = Modifier.padding(horizontal = 4.dp),
                    color = Color.Red
                )
            }
        }
        TextField(
            state,
            supportingText = { Text(description) }

        )
    }
}

@Preview
@Composable
fun TextInputPreview() {
    AppTheme {
        Surface {
            Box(modifier = Modifier.padding(12.dp)) {
                TextInput("Test Input", required = true, description = "Description Here")
            }
        }
    }
}