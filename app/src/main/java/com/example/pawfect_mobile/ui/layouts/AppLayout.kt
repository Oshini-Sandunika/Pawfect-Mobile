package com.example.pawfect_mobile.ui.layouts

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.pawfect_mobile.ui.theme.AppTheme

@Composable
fun AppLayout(content: @Composable (BoxScope.() -> Unit)) {
    val snackState = remember { SnackbarHostState() }

    AppTheme {
        Scaffold(
            snackbarHost = { SnackbarHost(snackState) },
            content = {
                Box(
                    content = content,
                    modifier = Modifier.padding(it)
                )
            }
        )
    }
}


@Preview
@Composable
fun AppLayoutTest() {
    AppLayout { }
}