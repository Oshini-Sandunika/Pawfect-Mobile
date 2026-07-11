package com.example.pawfect_mobile.ui.screens.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import kotlinx.coroutines.delay
import java.lang.Math.random
import kotlin.time.Duration.Companion.milliseconds

val imageList = arrayOf(
    "1534361960057-19889db9621e",
    "1543466835-00a7907e9de1",
    "1514888286974-6c03e2ca1dba",
    "1548199973-03cce0bbc87b",
    "1587300003388-59208cc962cb",
    "1472491235688-bdc81a63246e",
    "1511044568932-338cba0ad803",
    "1560114928-40f1f1eb26a0"
)


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ImageCarousel() {
    val carouselImages =
        imageList.map { "https://images.unsplash.com/photo-${it}?q=80&w=1000&auto=format&fit=crop" }

    val pagerState =
        rememberPagerState(
            pageCount = { carouselImages.size },
            initialPage = (random() * carouselImages.size).toInt()
        )

    LaunchedEffect(Unit) {
        while (true) {
            delay(10000.milliseconds)
            val nextPage = (pagerState.currentPage + 1) % pagerState.pageCount
            pagerState.animateScrollToPage(nextPage)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxSize()
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize(),
            userScrollEnabled = false,
        ) { page ->
            GlideImage(
                model = carouselImages[page],
                contentDescription = "Carousel Image",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        // Dark overlay to make text readable
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.3f))
        )
    }
}
