package com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.util

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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import com.pinu.jetpackcomposemodularprojectdemo.R

@Composable
fun BookHubImage(
    image: Any,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    contentScale: ContentScale = ContentScale.FillBounds,
    placeholderImg: Painter = painterResource(R.drawable.img_placeholder)
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
            AsyncImage(
                model = image,
                contentDescription = contentDescription,
                modifier = modifier,
                contentScale = contentScale,
                placeholder = placeholderImg
            )
        }

        is Uri -> { // URI
            AsyncImage(
                model = image,
                contentDescription = contentDescription,
                modifier = modifier,
                contentScale = contentScale,
                placeholder = placeholderImg
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
