package com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.components

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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.pinu.domain.entities.events.CartEvents
import com.pinu.domain.entities.events.FavouritesEvents
import com.pinu.domain.entities.network_service.request.AddToCartRequest
import com.pinu.domain.entities.network_service.response.BookResponse
import com.pinu.jetpackcomposemodularprojectdemo.R
import com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.theme.BookHubTypography
import com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.theme.PrimaryColor
import com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.theme.SurfaceColor
import com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.theme.TextSecondary
import com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.theme.defaultBook
import com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.util.CommonAlertDialog

@Preview
@Composable
fun FavouriteItem(
    favouriteItem: BookResponse.BookItemResponse = defaultBook,
    onEvents: (FavouritesEvents) -> Unit = {},
    onCartEvents: (CartEvents) -> Unit = {}
) {

    val showRemoveFromFavouriteDialog = remember { mutableStateOf(false) }
    val showMoveToCartDialog = remember { mutableStateOf(false) }

    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceColor),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 4.dp)
            .clickable {
                onEvents(FavouritesEvents.NavigateToBookDetailScreen(favouriteItem.id))
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            AsyncImage(
                model = favouriteItem.imageUrl ?: R.drawable.book,
                contentDescription = stringResource(id = R.string.book),
                modifier = Modifier
                    .size(width = 50.dp, height = 70.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .clickable {
                        onEvents(FavouritesEvents.NavigateToBookDetailScreen(favouriteItem.id))
                    },
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(
                    text = favouriteItem.name ?: "",
                    style = BookHubTypography.bodySmall,
                    overflow = TextOverflow.Ellipsis, maxLines = 2,
                )
                Spacer(modifier = Modifier.padding(top = 6.dp))
                Text(
                    text = favouriteItem.bookPublishedDate ?: defaultBook.bookPublishedDate,
                    style = BookHubTypography.labelMedium.copy(color = TextSecondary),
                    overflow = TextOverflow.Ellipsis, maxLines = 1,
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {

                    Text(
                        stringResource(R.string.remove),
                        style = BookHubTypography.labelLarge.copy(
                            color = Color.Gray,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.clickable {
                            showRemoveFromFavouriteDialog.value = true
                        }
                    )
                    VerticalDivider(
                        modifier = Modifier
                            .height(10.dp)
                            .padding(start = 6.dp, end = 6.dp),
                    )
                    Text(
                        stringResource(if (favouriteItem.isInCart) R.string.go_to_cart else R.string.move_to_cart),
                        style = BookHubTypography.labelLarge.copy(
                            color = PrimaryColor,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.clickable {
                            if (favouriteItem.isInCart){
                                onEvents(FavouritesEvents.NavigateToCartScreen)
                            }else{
                                showMoveToCartDialog.value = true
                            }
                        }
                    )
                }
            }
        }
    }

    if (showMoveToCartDialog.value) {
        CommonAlertDialog(
            title = stringResource(R.string.move_item_to_cart),
            positiveButtonText = stringResource(R.string.yes_move),
            negativeButtonText = stringResource(R.string.maybe_later),
            onNegativeButtonClicked = {
                showMoveToCartDialog.value = false
            },
            onPositiveButtonClicked = {
                onCartEvents(
                    CartEvents.AddToCart(
                        isFromBookDetail = false,
                        cartRequest = AddToCartRequest(
                            bookId = favouriteItem.id,
                            qty = 1
                        )
                    )
                )
                showMoveToCartDialog.value = false
            })
    }

    if (showRemoveFromFavouriteDialog.value) {
        CommonAlertDialog(
            title = stringResource(R.string.remove_from_favourites),
            positiveButtonText =  stringResource(R.string.remove),
            negativeButtonText = stringResource(R.string.no),
            onNegativeButtonClicked = {
                showRemoveFromFavouriteDialog.value = false
            },
            onPositiveButtonClicked = {
                onEvents(FavouritesEvents.RemoveFromFavourites(favouriteItem.id))
                showRemoveFromFavouriteDialog.value = false
            })
    }
}