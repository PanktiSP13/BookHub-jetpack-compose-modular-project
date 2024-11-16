package com.pinu.jetpackcomposemodularprojectdemo.ui.components

import androidx.compose.foundation.Image
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
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.pinu.jetpackcomposemodularprojectdemo.R
import com.pinu.jetpackcomposemodularprojectdemo.ui.theme.BackgroundColor
import com.pinu.jetpackcomposemodularprojectdemo.ui.theme.BookHubTypography
import com.pinu.jetpackcomposemodularprojectdemo.ui.theme.SurfaceColor
import com.pinu.jetpackcomposemodularprojectdemo.ui.theme.TextSecondary
import com.pinu.jetpackcomposemodularprojectdemo.ui.theme.dummyBookDate
import com.pinu.jetpackcomposemodularprojectdemo.ui.theme.dummyDescription
import com.pinu.jetpackcomposemodularprojectdemo.ui.theme.dummyString
import com.pinu.jetpackcomposemodularprojectdemo.ui.theme.dummyUrl

@Preview
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
        colors = CardDefaults.cardColors(containerColor = SurfaceColor),
        modifier = Modifier
            .fillMaxWidth()
            .height(170.dp)
            .defaultMinSize(minHeight = 100.dp) // if you don't give fixed height then set default height
            .padding(horizontal = 12.dp, vertical = 4.dp)
            .clickable { onItemClick() }
    ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)) {
            Image(
                painter = painterResource(id = R.drawable.book) ?:rememberAsyncImagePainter(dummyUrl),
                contentDescription = stringResource(R.string.book),
                modifier = Modifier
                    .size(width = 100.dp, height = 150.dp)
                    .clip(RoundedCornerShape(6.dp)),
                contentScale = ContentScale.FillBounds
            )
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)) {
                Text(
                    text = dummyString,
                    style = BookHubTypography.titleMedium,
                    overflow = TextOverflow.Ellipsis, maxLines = 2,
                    lineHeight = 18.sp
                )
                Spacer(modifier = Modifier.padding(top = 12.dp))
                Text(
                    text = dummyBookDate,
                    style = BookHubTypography.bodySmall.copy(color = TextSecondary),
                    overflow = TextOverflow.Ellipsis, maxLines = 1,
                )
                Spacer(modifier = Modifier.padding(top = 12.dp))
                Text(
                    text = dummyDescription,
                    style = BookHubTypography.bodySmall.copy(color = TextSecondary),
                    overflow = TextOverflow.Ellipsis,
                )
            }

        }
    }

}
