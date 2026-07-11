package com.example.pawfect_mobile.ui.screens.home.components

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pawfect_mobile.AdoptionCostActivity
import com.example.pawfect_mobile.FindPerfectPetActivity
import com.example.pawfect_mobile.HumanYearConverterActivity
import com.example.pawfect_mobile.MonthlyCareBudgetActivity

@Composable
fun ActionCard(onSearchClick: () -> Unit, modifier: Modifier = Modifier) {
    val context = LocalContext.current

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(4.dp),
        colors = CardDefaults.cardColors(containerColor = androidx.compose.ui.graphics.Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Search()
            Spacer(modifier = Modifier.height(16.dp))

            // Tool Links
            Text("Tools")
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ToolItem(
                    icon = Icons.Default.Favorite,
                    label = "Pet Quiz",
                    onClick = {
                        context.startActivity(Intent(context, FindPerfectPetActivity::class.java))
                    }
                )
                ToolItem(
                    icon = Icons.Default.DateRange,
                    label = "Age Calculator",
                    onClick = {
                        context.startActivity(
                            Intent(
                                context,
                                HumanYearConverterActivity::class.java
                            )
                        )
                    }
                )
                ToolItem(
                    icon = Icons.Default.Calculate,
                    label = "Budget",
                    onClick = {
                        context.startActivity(
                            Intent(
                                context,
                                MonthlyCareBudgetActivity::class.java
                            )
                        )
                    }
                )
                ToolItem(
                    icon = Icons.Default.Calculate,
                    label = "Cost Estimator",
                    onClick = {
                        context.startActivity(Intent(context, AdoptionCostActivity::class.java))
                    }
                )
            }
        }
    }
}

@Composable
private fun ToolItem(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable { onClick() }
            .padding(4.dp)
    ) {
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = MaterialTheme.colorScheme.primaryContainer,
            modifier = Modifier.size(56.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier.padding(12.dp)
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = label, fontSize = 12.sp, fontWeight = FontWeight.Medium)
    }
}
