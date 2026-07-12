package com.example.pawfect_mobile.ui.screens.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Calculate
import androidx.compose.material.icons.twotone.CalendarMonth
import androidx.compose.material.icons.twotone.Payments
import androidx.compose.material.icons.twotone.Quiz
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pawfect_mobile.ui.components.StyledCard
import com.example.pawfect_mobile.ui.components.StyledIconButton

@Composable
fun ActionCard(
    onSearchClick: () -> Unit,
    onQuizClick: () -> Unit,
    onAgeCalculatorClick: () -> Unit,
    onBudgetClick: () -> Unit,
    onCostEstimatorClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    StyledCard(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Search(onSearchClick)
            Spacer(modifier = Modifier.height(16.dp))

            // Tool Links
            Text("Tools")
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                StyledIconButton(
                    icon = Icons.TwoTone.Quiz,
                    label = "Pet Quiz",
                    modifier = Modifier.padding(4.dp),
                    onClick = onQuizClick
                )
                StyledIconButton(
                    icon = Icons.TwoTone.CalendarMonth,
                    label = "Age Calculator",
                    modifier = Modifier.padding(4.dp),
                    onClick = onAgeCalculatorClick
                )
                StyledIconButton(
                    icon = Icons.TwoTone.Calculate,
                    label = "Budget",
                    modifier = Modifier.padding(4.dp),
                    onClick = onBudgetClick
                )
                StyledIconButton(
                    icon = Icons.TwoTone.Payments,
                    label = "Cost Estimator",
                    modifier = Modifier.padding(4.dp),
                    onClick = onCostEstimatorClick
                )
            }
        }
    }
}
