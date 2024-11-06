package com.pinu.jetpackcomposemodularprojectdemo.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.pinu.jetpackcomposemodularprojectdemo.R
import com.pinu.jetpackcomposemodularprojectdemo.ui.theme.dummyString
import com.pinu.jetpackcomposemodularprojectdemo.ui.theme.dummyUrl

@Preview()
@Composable
fun BookItem(
    imageUrl: String = "",
    title: String = "",
    author: String = "",
    description: String = "",
    onItemClick : () -> Unit = {}
) {

    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardColors(
            containerColor = Color.White,
            contentColor = MaterialTheme.colorScheme.surfaceVariant,
            disabledContentColor = MaterialTheme.colorScheme.surfaceVariant,
            disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant
        ), modifier = Modifier
            .fillMaxWidth()
            .height(170.dp)
            .defaultMinSize(minHeight = 100.dp) // if you don't give fixed height then set default height
            .padding(horizontal = 12.dp, vertical = 4.dp)
            .clickable { onItemClick()  }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter("https://m.media-amazon.com/images/I/81gRz9A4F6L._UF1000,1000_QL80_.jpg"),
                contentDescription = "book",
                modifier = Modifier
                    .size(width = 100.dp, height = 150.dp)
                    .clip(RoundedCornerShape(4.dp))
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                Text(
                    text = "To apply the custom font across your app, you can set it as part of your app’s theme.",
                    style = TextStyle(
                        fontFamily = FontFamily.SansSerif,
                        fontSize = 16.sp,
                        color = Color.Black, fontWeight = FontWeight.Bold,
                    ), overflow = TextOverflow.Ellipsis, maxLines = 2,
                    lineHeight = 18.sp
                )
                Spacer(modifier = Modifier.padding(top = 12.dp))
                Text(
                    text = "12/05/2323",
                    style = TextStyle(
                        fontFamily = FontFamily.SansSerif,
                        fontSize = 12.sp,
                        color = Color.Gray,
                    ),
                    overflow = TextOverflow.Ellipsis, maxLines = 1,
                )
                Spacer(modifier = Modifier.padding(top = 12.dp))
                Text(
                    text = dummyString,
                    style = TextStyle(
                        fontFamily = FontFamily.SansSerif,
                        fontSize = 12.sp,
                        color = Color.Gray,
                    ),
                    overflow = TextOverflow.Ellipsis,
                )
            }

        }
    }

}
