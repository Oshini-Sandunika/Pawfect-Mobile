package com.example.pawfect_mobile.ui.screens.staff.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.pawfect_mobile.data.models.Inquiry
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun InquiriesTab(inquiries: List<Inquiry>) {
    if (inquiries.isEmpty()) {
        Text("No inquiries yet.", modifier = Modifier.padding(16.dp))
        return
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(inquiries) { inquiry ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Pet ID: ${inquiry.petId}",
                        fontWeight = FontWeight.Bold
                    )
                    Text(text = "User ID: ${inquiry.userId}")
                    Text(
                        text = "Date: ${SimpleDateFormat("MMM dd, yyyy", Locale.US).format(Date(inquiry.timestamp))}"
                    )
                    Text(
                        text = "Message: ${inquiry.message}",
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        }
    }
}
