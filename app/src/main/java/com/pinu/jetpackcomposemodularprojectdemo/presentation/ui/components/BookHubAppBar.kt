package com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pinu.domain.entities.AppBarEvents
import com.pinu.domain.entities.AppBarState
import com.pinu.domain.entities.AppBarUIConfig
import com.pinu.domain.entities.events.CartEvents
import com.pinu.domain.entities.events.FavouritesEvents
import com.pinu.domain.entities.events.ProfileEvents
import com.pinu.jetpackcomposemodularprojectdemo.R
import com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.theme.BookHubTypography
import com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.theme.OnPrimaryColor
import com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.theme.Pink
import com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.theme.PrimaryColor

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun BookHubAppBar(
    appBarUIConfig: AppBarUIConfig = AppBarUIConfig(),
    appBarState: AppBarState = AppBarState(),
    appBarEvents: AppBarEvents = AppBarEvents(),
) {

    val showFavourites = rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(key1 = appBarState.cartState.itemMovedToCart) {
        appBarState.cartState.itemMovedToCart.takeIf { it }?.let {
            showFavourites.value = false
            appBarEvents.cartEvents(CartEvents.ValueUpdateItemMovedToCart)
        }
    }

    Surface(color = Pink) {
        TopAppBar(
            title = {
                Text(
                    text = appBarUIConfig.title,
                    style = BookHubTypography.titleLarge.copy(
                        fontWeight = FontWeight.Normal, color = OnPrimaryColor),
                    modifier = Modifier.fillMaxWidth()
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = PrimaryColor ),
            navigationIcon = {
                AppBarNavigationIcon(
                    appBarUIConfig,
                    onBackClicked = { appBarEvents.onBackPressed() },
                    onProfileClicked = { appBarEvents.profileEvents(ProfileEvents.NavigateToProfileScreen) })
            },
            actions = {
                AppBarActions(appBarUIConfig, onFavouriteClicked = {
                    showFavourites.value = true
                }, onCartClick = {
                    appBarEvents.cartEvents(CartEvents.NavigateToCartScreen)
                })
            },
        )
    }

    if (showFavourites.value) {
        FavouritesBottomSheet(
            favouriteState = appBarState.favouriteState,
            onFavouriteEvents = { event ->
                when (event) {
                    is FavouritesEvents.NavigateToBookDetailScreen -> {
                        showFavourites.value = false
                    }

                    is FavouritesEvents.NavigateToCartScreen -> {
                        appBarEvents.cartEvents(CartEvents.NavigateToCartScreen)
                        showFavourites.value = false
                    }

                    else -> {}
                }
                appBarEvents.favouriteEvents(event)
            },
            onCartEvents = { cartEvent -> appBarEvents.cartEvents(cartEvent) },
            onDismiss = { showFavourites.value = false })
    }
}

@Composable
fun AppBarNavigationIcon(
    appBarUIConfig: AppBarUIConfig,
    onBackClicked: () -> Unit = {},
    onProfileClicked: () -> Unit = {},
) {
    if (appBarUIConfig.canGoBack) {
        Image(
            painter = painterResource(id = R.drawable.back_arrow),
            contentDescription = stringResource(R.string.back),
            modifier = Modifier
                .padding(start = 12.dp)
                .size(25.dp)
                .indication(indication = ripple(bounded = false),
                    interactionSource = remember { MutableInteractionSource() })
                .clickable { onBackClicked() },
            colorFilter = ColorFilter.tint(color = OnPrimaryColor)
        )

    } else {
        if (appBarUIConfig.isProfileOptionAvailable) {
            IconButton(onClick = onProfileClicked) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = stringResource(R.string.profile),
                    tint = OnPrimaryColor
                )
            }

        }
    }
}

@Composable
fun AppBarActions(
    appBarUIConfig: AppBarUIConfig,
    onFavouriteClicked: () -> Unit = {},
    onCartClick: () -> Unit = {},
) {

    if (appBarUIConfig.isFavouritesVisible) {
        IconButton(onClick = onFavouriteClicked) {
            Image(
                painter = painterResource(id = R.drawable.favourite_checked),
                modifier = Modifier.size(25.dp),
                contentDescription = stringResource(R.string.favourites)
            )
        }
    }

    if (appBarUIConfig.isCartVisible) {
        IconButton(onClick = onCartClick) {
            Image(
                painter = painterResource(id = R.drawable.cart),
                modifier = Modifier.size(25.dp),
                contentDescription = stringResource(R.string.cart),
                colorFilter = ColorFilter.tint(color = Color.White)
            )
        }
    }
}

