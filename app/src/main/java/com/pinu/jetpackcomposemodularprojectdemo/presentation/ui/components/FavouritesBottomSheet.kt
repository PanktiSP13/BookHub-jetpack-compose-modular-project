package com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pinu.domain.entities.events.CartEvents
import com.pinu.domain.entities.events.FavouritesEvents
import com.pinu.domain.entities.states.FavouritesState
import com.pinu.jetpackcomposemodularprojectdemo.R
import com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.theme.BookHubTypography
import com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.theme.PrimaryVariant
import com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.theme.SecondaryColor
import com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.theme.SurfaceColor
import com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.util.RenderScreen

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun FavouritesBottomSheet(
    favouriteState: FavouritesState = FavouritesState(),
    onFavouriteEvents: (FavouritesEvents)-> Unit = {},
    onCartEvents: (CartEvents) -> Unit = {},
    onDismiss: () -> Unit = {}) {


    LaunchedEffect(favouriteState.favouriteList) {
        onFavouriteEvents(FavouritesEvents.FetchFavourites)
    }

    ModalBottomSheet(onDismissRequest = { onDismiss()},
       containerColor = SurfaceColor) {

        RenderScreen(
            modifier = if (favouriteState.isLoading || favouriteState.favouriteList.isEmpty()) Modifier
                .fillMaxWidth()
                .height(200.dp) else Modifier.fillMaxSize(),
            isLoading = favouriteState.isLoading,
            showError = favouriteState.isLoading.not() && favouriteState.favouriteList.isEmpty(),
            onLoading = {
                CircularProgressIndicator(color = SecondaryColor)
            },
            onSuccess = {
                LazyColumn {
                    item {
                        Column(modifier = Modifier.fillParentMaxWidth().padding(horizontal = 12.dp)) {
                            Text(
                                stringResource(R.string.favourites),
                                style = BookHubTypography.headlineSmall.copy(fontWeight = FontWeight.Bold)
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            HorizontalDivider(color = PrimaryVariant)
                            Spacer(modifier = Modifier.height(12.dp))
                        }
                    }
                    items(favouriteState.favouriteList) {
                        FavouriteItem(
                            favouriteItem = it,
                            onEvents = onFavouriteEvents, onCartEvents = onCartEvents
                        )
                    }
                }
            },
            onError = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp),
                    contentAlignment = Alignment.Center
                ) {
                    if (favouriteState.favouriteList.isEmpty()) {
                        Text(
                            stringResource(R.string.no_favourites_found),
                            style = BookHubTypography.bodyMedium
                        )
                    }
                }
            }
        )
    }
}