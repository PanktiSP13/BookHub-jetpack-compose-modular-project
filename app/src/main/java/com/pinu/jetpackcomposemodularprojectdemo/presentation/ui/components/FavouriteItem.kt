package com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.pinu.jetpackcomposemodularprojectdemo.R
import com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.theme.BookHubTypography
import com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.theme.SurfaceColor
import com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.theme.TextSecondary
import com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.theme.dummyBookDate
import com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.theme.dummyBookTitle
import com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.theme.dummyUrl

@Preview
@Composable
fun FavouriteItem(
    imageUrl: String = "",
    title: String = "",
    description: String = "",
    onItemClick : () -> Unit = {}) {


    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceColor),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 4.dp)
            .clickable { onItemClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.book) ?: rememberAsyncImagePainter(dummyUrl),
                contentDescription = stringResource(id = R.string.book),
                modifier = Modifier
                    .size(width = 50.dp, height = 70.dp)
                    .clip(RoundedCornerShape(6.dp)),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(
                    text = dummyBookTitle,
                    style = BookHubTypography.bodySmall,
                    overflow = TextOverflow.Ellipsis, maxLines = 2,
                )
                Spacer(modifier = Modifier.padding(top = 6.dp))
                Text(
                    text = dummyBookDate,
                    style = BookHubTypography.labelMedium.copy(color = TextSecondary),
                    overflow = TextOverflow.Ellipsis, maxLines = 1,
                )
            }

        }
    }
}