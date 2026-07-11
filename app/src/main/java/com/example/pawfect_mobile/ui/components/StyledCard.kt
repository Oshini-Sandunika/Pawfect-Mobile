package com.example.pawfect_mobile.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

@Composable
fun StyledCard(
    modifier: Modifier = Modifier,
    shape: Shape? = null,
    colors: CardColors? = null,
    elevation: CardElevation? = null,
    border: BorderStroke? = null,
    content: @Composable ColumnScope.() -> Unit,
) {
    Card(
        modifier = modifier,
        shape = shape ?: RoundedCornerShape(12.dp),
        colors = colors
            ?: CardDefaults.cardColors(containerColor = androidx.compose.ui.graphics.Color.White),
        elevation = elevation ?: CardDefaults.cardElevation(defaultElevation = 8.dp),
        border = border,
        content = content
    )
}