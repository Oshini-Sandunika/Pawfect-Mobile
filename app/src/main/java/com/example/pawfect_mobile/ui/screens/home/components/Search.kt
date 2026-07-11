package com.example.pawfect_mobile.ui.screens.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.pawfect_mobile.R

@Composable
fun Search(onSearchClick: () -> Unit) {
    OutlinedTextField(
        value = "",
        onValueChange = {},
        modifier = Modifier
            .fillMaxWidth()
            .onFocusChanged {
                if (it.isFocused) {
                    onSearchClick()
                }
            },
        placeholder = { Text(stringResource(R.string.search_pets)) },
        readOnly = true,
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
        trailingIcon =
            {
                Image(
                    painter = painterResource(id = R.drawable.pawfect_logo),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .size(48.dp)
                        .background(Color(1f, 1f, 1f, 0.3f))
                )
            },
        shape = RoundedCornerShape(12.dp),
    )
}