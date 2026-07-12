package com.example.pawfect_mobile.ui.components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ImageInput(
    value: ImageInputValue,
    onValueChange: (ImageInputValue) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "Image URL"
) {
    var urlInput by remember(value) {
        mutableStateOf(
            when (value) {
                is ImageInputValue.Url -> value.url
                else -> ""
            }
        )
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            onValueChange(ImageInputValue.LocalUri(uri))
        }
    }

    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        val displayModel = when (value) {
            is ImageInputValue.Url -> value.url
            is ImageInputValue.LocalUri -> value.uri
            else -> null
        }

        if (displayModel != null && displayModel.toString().isNotBlank()) {
            GlideImage(
                model = displayModel,
                contentDescription = "Selected Image",
                modifier = Modifier
                    .size(150.dp)
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(12.dp))
        }

        OutlinedTextField(
            value = urlInput,
            onValueChange = {
                urlInput = it
                if (it.isNotBlank()) {
                    onValueChange(ImageInputValue.Url(it))
                } else {
                    onValueChange(ImageInputValue.Empty)
                }
            },
            label = { Text(label) },
            modifier = Modifier.fillMaxWidth()
        )
        
        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = {
            launcher.launch(
                PickVisualMediaRequest(
                    mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly
                )
            )
        }) {
            Text("Select Local File")
        }
    }
}
