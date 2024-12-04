package com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.pinu.domain.entities.events.CartEvents
import com.pinu.domain.entities.network_service.request.UpdateItemQuantityRequest
import com.pinu.domain.entities.network_service.response.CartItemsResponse
import com.pinu.jetpackcomposemodularprojectdemo.R
import com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.theme.BookHubTypography
import com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.theme.OnSecondaryColor
import com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.theme.PrimaryColor
import com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.theme.SurfaceColor
import com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.util.CommonAlertDialog

@Preview(showBackground = false)
@Composable
fun CartItem(
    cartItem: CartItemsResponse.CartItemsData.CartItem = CartItemsResponse.CartItemsData.CartItem(),
    onEvent: (CartEvents) -> Unit = {}
) {

    val qty = remember {
        mutableIntStateOf(cartItem.quantity ?: 1)
    }
    val showAlert = remember {
        mutableStateOf(false)
    }

    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceColor),
        modifier = Modifier.padding(vertical = 8.dp, horizontal = 12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = cartItem.imgUrl ?: R.drawable.book,
                contentDescription = stringResource(id = R.string.book),
                modifier = Modifier
                    .size(width = 70.dp, height = 120.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .clickable {
                        onEvent(
                            CartEvents.NavigateToBookDetailScreen(bookId = cartItem.bookId ?: 0)
                        )
                    },
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
            ) {
                Text(
                    text = cartItem.title ?: "",
                    style = BookHubTypography.titleSmall.copy(fontWeight = FontWeight.Medium),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2,
                    lineHeight = 16.sp
                )
                Spacer(modifier = Modifier.padding(top = 14.dp))
                Text(
                    text = "${cartItem.price ?: 0.0}",
                    style = BookHubTypography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2,
                    lineHeight = 16.sp
                )
                Spacer(modifier = Modifier.padding(top = 6.dp))

                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                    Text(text = stringResource(R.string.qty), style = BookHubTypography.bodySmall)
                    Box(contentAlignment = Alignment.Center) {
                        IconButton(onClick = {
                            if (qty.intValue > 1) {
                                qty.intValue -= 1

                                onEvent(
                                    CartEvents.UpdateBookItemQuantity(
                                        UpdateItemQuantityRequest(
                                            bookId = cartItem.bookId ?: 0,
                                            quantity = qty.intValue
                                        )
                                    )
                                )
                            } else {
                                showAlert.value = true
                            }

                        },
                            modifier = Modifier
                                .padding(start = 6.dp)
                                .size(25.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.remove),
                                contentDescription = "Add",
                                tint = if (qty.intValue > 1) Color.Black else Color.LightGray,
                            )
                        }
                    }

                    Spacer(modifier = Modifier.padding(start = 4.dp))
                    Box(
                        modifier = Modifier
                            .padding(start = 6.dp)
                            .defaultMinSize(minWidth = 25.dp, minHeight = 25.dp)
                            .border(
                                border = BorderStroke(width = 1.dp, color = PrimaryColor),
                                shape = RoundedCornerShape(4.dp)
                            ), contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "${qty.intValue}",
                            style = BookHubTypography.bodyMedium
                        )
                    }
                    Spacer(modifier = Modifier.padding(start = 4.dp))
                    Box(contentAlignment = Alignment.Center) {
                        IconButton(
                            onClick = {
                                qty.intValue += 1

                                onEvent(
                                    CartEvents.UpdateBookItemQuantity(
                                        UpdateItemQuantityRequest(
                                            bookId = cartItem.bookId ?: 0,
                                            quantity = qty.intValue
                                        )
                                    )
                                )

                            }, modifier = Modifier
                                .padding(start = 6.dp)
                                .size(25.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = stringResource(id = R.string.add)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(onClick = { showAlert.value = true }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = stringResource(R.string.remove),
                            tint = OnSecondaryColor
                        )
                    }
                }
            }
        }
    }

    if (showAlert.value) {
        CommonAlertDialog(
            onNegativeButtonClicked = { showAlert.value = false },
            onPositiveButtonClicked = {
                onEvent(CartEvents.RemoveBookItemFromCart(bookId = cartItem.bookId ?: 0))
                showAlert.value = false
            })
    }

}
