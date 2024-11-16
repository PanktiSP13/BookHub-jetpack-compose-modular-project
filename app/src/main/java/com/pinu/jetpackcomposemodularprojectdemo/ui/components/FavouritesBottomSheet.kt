package com.pinu.jetpackcomposemodularprojectdemo.ui.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.pinu.jetpackcomposemodularprojectdemo.ui.theme.SurfaceColor

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun FavouritesBottomSheet(onDismiss: () -> Unit = {}) {
    ModalBottomSheet(onDismissRequest = { onDismiss()},
       containerColor = SurfaceColor) {
        LazyColumn {
            items(10){
                FavouriteItem()
            }
        }
    }
}