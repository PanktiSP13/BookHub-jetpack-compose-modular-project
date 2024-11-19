package com.pinu.jetpackcomposemodularprojectdemo.ui.util

import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil.compose.rememberAsyncImagePainter

@Composable
fun BookHubImage(
    image: Any,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    contentScale: ContentScale = ContentScale.FillBounds
) {

    when (image) {
        is Int -> { // Resource ID
            Image(
                painter = painterResource(id = image),
                contentDescription = contentDescription,
                modifier = modifier,
                contentScale = contentScale
            )
        }

        is String -> { // URL
            Image(
                painter = rememberAsyncImagePainter(model = image),
                contentDescription = contentDescription,
                modifier = modifier,
                contentScale = contentScale
            )
        }

        is Uri -> { // URI
            Image(
                painter = rememberAsyncImagePainter(model = image),
                contentDescription = contentDescription,
                modifier = modifier,
                contentScale = contentScale
            )
        }

        is Bitmap -> { // Bitmap
            Image(
                bitmap = image.asImageBitmap(),
                contentDescription = contentDescription,
                modifier = modifier,
                contentScale = contentScale
            )
        }

        else -> {
            // Handle unsupported types or show a placeholder
            Box(
                modifier = modifier,
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Invalid Image", color = Color.Red)
            }
        }
    }
}
