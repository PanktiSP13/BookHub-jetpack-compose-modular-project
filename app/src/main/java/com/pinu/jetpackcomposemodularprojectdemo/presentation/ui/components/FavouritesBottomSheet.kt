package com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.pinu.domain.entities.events.FavouritesEvents
import com.pinu.domain.entities.states.FavouritesState
import com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.theme.SurfaceColor

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun FavouritesBottomSheet(
    favouriteState: FavouritesState = FavouritesState(),
    onEvents: (FavouritesEvents)-> Unit = {},
    onDismiss: () -> Unit = {}) {

    ModalBottomSheet(onDismissRequest = { onDismiss()},
       containerColor = SurfaceColor) {
        // todo check it
//        LazyVerticalGrid(columns = GridCells.Fixed(2)) {
//            items(favouriteState.favouriteList){
//                FavouriteItem()
//            }
//        }
        LazyColumn {
            items(favouriteState.favouriteList){
                FavouriteItem(favouriteItem = it,onEvents = onEvents)
            }
        }
    }
}