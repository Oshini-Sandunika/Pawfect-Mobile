package com.example.pawfect_mobile.ui.screens.staff.components

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.example.pawfect_mobile.data.models.Inquiry
import com.example.pawfect_mobile.ui.components.StyledCard
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun InquiriesTab(inquiries: List<Inquiry>) {
    if (inquiries.isEmpty()) {
        Text("No inquiries yet.", modifier = Modifier.padding(16.dp))
        return
    }

    val context = LocalContext.current

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(inquiries) { inquiry ->
            StyledCard(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Date: ${
                                SimpleDateFormat("MMM dd, yyyy", Locale.US).format(
                                    Date(
                                        inquiry.timestamp
                                    )
                                )
                            }",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    val petName = inquiry.pet?.name ?: "Unknown Pet"
                    val petBreed = inquiry.pet?.breed ?: ""
                    Text(
                        text = "Interested in $petName",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    if (petBreed.isNotBlank()) {
                        Text(
                            text = petBreed,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                    HorizontalDivider()
                    Spacer(modifier = Modifier.height(12.dp))

                    var userName = inquiry.username
                    if (userName.isBlank()) {
                        userName = "Unknown User"
                    }
                    Text(
                        text = "From: $userName",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "Message: ${inquiry.message}",
                        modifier = Modifier.padding(top = 8.dp),
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            enabled = inquiry.email.isNotBlank(),
                            shape = RoundedCornerShape(6.dp),
                            onClick = {
                                val intent = Intent(Intent.ACTION_SENDTO).apply {
                                    data = "mailto:${inquiry.email}".toUri()
                                }
                                context.startActivity(intent)
                            },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                                contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                        ) {
                            Icon(
                                Icons.Default.Email,
                                contentDescription = "Email",
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Email")
                        }

                        Button(
                            enabled = inquiry.phone.isNotBlank(),
                            shape = RoundedCornerShape(6.dp),
                            onClick = {
                                val intent = Intent(Intent.ACTION_DIAL).apply {
                                    data = "tel:${inquiry.phone}".toUri()
                                }
                                context.startActivity(intent)
                            },
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(
                                Icons.Default.Phone,
                                contentDescription = "Call",
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Call")
                        }
                    }
                }
            }
        }
    }
}
