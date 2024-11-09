package com.pinu.jetpackcomposemodularprojectdemo.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.pinu.jetpackcomposemodularprojectdemo.R
import com.pinu.jetpackcomposemodularprojectdemo.navigation.NavigationRoutes
import com.pinu.jetpackcomposemodularprojectdemo.ui.theme.Pink
import com.pinu.jetpackcomposemodularprojectdemo.ui.theme.Pink80

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun CommonAppBar(
    title: String = "Dashboard",
    canGoBack: Boolean = false,
    isCartVisible:Boolean = true,
    isFavouritesVisible : Boolean = true,
    navController: NavController = rememberNavController(),
    onCartClick: () -> Unit = {},
    onBackPressed: () -> Unit = {},
) {

    val  showFavourites = remember { mutableStateOf(false) }

    Surface(color = Pink) {
        TopAppBar(
            title = { Text(text = title, modifier = Modifier.fillMaxWidth()) },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = Pink80),
            navigationIcon = {
                if (canGoBack) {
                    Image(
                        painter = painterResource(id = R.drawable.back_arrow),
                        contentDescription = "back",
                        modifier = Modifier
                            .padding(start = 12.dp)
                            .size(25.dp)
                            .indication(indication = ripple(bounded = false),
                                interactionSource = remember {
                                    MutableInteractionSource()
                                })
                            .clickable {
                                navController.popBackStack()
                                onBackPressed()
                            }
                    )
                }
            },
            actions = {
                if (isFavouritesVisible){
                    IconButton(onClick = {
                        showFavourites.value = true
                    }) {
                        Image(
                            painter = painterResource(id = R.drawable.favourite_checked),
                            modifier = Modifier.size(25.dp),
                            contentDescription = "cart"
                        )
                    }
                }

                if (isCartVisible){
                    IconButton(onClick = {
                        navController.navigate(NavigationRoutes.CartScreen.route)
                    }) {
                        Image(
                            painter = painterResource(id = R.drawable.cart),
                            modifier = Modifier.size(25.dp),
                            contentDescription = "cart"
                        )
                    }
                }
            }
        )
    }

    if (showFavourites.value){
        FavouritesBottomSheet(onDismiss = {
            showFavourites.value = false
        })
    }

}