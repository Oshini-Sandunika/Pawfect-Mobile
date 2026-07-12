package com.example.pawfect_mobile.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun StyledIconButton(
    icon: ImageVector,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    label: String? = null,
    elevation: Dp = 0.dp,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .clickable { onClick() }
    ) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(
                1.dp,
                MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
            ),
            tonalElevation = elevation,
            shadowElevation = elevation,
            modifier = Modifier.size(56.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label ?: "",
                tint = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.8f),
                modifier = Modifier.padding(12.dp)
            )
        }
        if (label != null) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = label, fontSize = 12.sp, fontWeight = FontWeight.Medium)
        }
    }
}
