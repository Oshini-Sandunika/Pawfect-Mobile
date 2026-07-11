package com.example.pawfect_mobile.ui.layouts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.ImageShader
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.pawfect_mobile.R
import com.example.pawfect_mobile.ui.theme.AppTheme

@Composable
fun AppLayout(
    noInset: Boolean = false,
    topBar: @Composable (() -> Unit) = {},
    content: @Composable (BoxScope.() -> Unit),
) {
    val snackState = remember { SnackbarHostState() }
    val image = ImageBitmap.imageResource(R.drawable.background_tile)
    val brush =
        remember(image) {
            ShaderBrush(ImageShader(image, TileMode.Repeated, TileMode.Repeated))
        }
    Box(
        Modifier
            .fillMaxSize()
            .background(brush)
    ) {
    }

    AppTheme {
        Scaffold(
            snackbarHost = { SnackbarHost(snackState) },
            topBar = topBar,
            content = {
                Box(
                    content = content,
                    modifier = if (noInset) {
                        Modifier.consumeWindowInsets(it)
                    } else {
                        Modifier.padding(it)
                    }
                        .background(brush, alpha = 0.15f)
                        .fillMaxSize()
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