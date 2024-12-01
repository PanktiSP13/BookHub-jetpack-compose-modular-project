package com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.books.screens

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.pinu.domain.entities.events.BooksEvents
import com.pinu.domain.entities.events.CartEvents
import com.pinu.domain.entities.events.FavouritesEvents
import com.pinu.domain.entities.network_service.request.AddToCartRequest
import com.pinu.domain.entities.states.BooksState
import com.pinu.domain.entities.viewmodels.BooksViewModel
import com.pinu.domain.entities.viewmodels.CartViewModel
import com.pinu.domain.entities.viewmodels.FavouriteViewModel
import com.pinu.jetpackcomposemodularprojectdemo.R
import com.pinu.jetpackcomposemodularprojectdemo.navigation.NavigationRoutes
import com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.components.BookHubAppBar
import com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.theme.BookHubTypography
import com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.theme.OnPrimaryColor
import com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.theme.PrimaryVariant
import com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.theme.SurfaceColor
import com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.theme.TextSecondary
import com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.util.RenderScreen
import com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.util.showToast

@Composable
fun BookDetailRootUI(
    navController: NavController = rememberNavController(),
    booksViewModel: BooksViewModel,
    favouriteViewModel: FavouriteViewModel
) {

    val booksState = booksViewModel.bookState.collectAsState(initial = BooksState())

    Log.e("@@@", "BookDetailRootUI: ${booksState.value.bookList}")

    val cartViewModel = hiltViewModel<CartViewModel>()


    BookDetailScreen(booksState = booksState.value,
        favouriteViewModel = favouriteViewModel,
        navController = navController, onEvent = { events ->
            when (events) {
                is BooksEvents.NavigateBack -> navController.popBackStack()
                is BooksEvents.AddToCart -> {
                    // Book Detail Screen triggers the event, but the cart management logic stays centralized.
                    // todo request model here
                    cartViewModel.onEvent(
                        CartEvents.AddToCart(
                            AddToCartRequest(
                                bookId = events.bookID,
                                qty = 0
                            )
                        )
                    )
                }
                is BooksEvents.GoToCart ->{
                    navController.navigate(route = NavigationRoutes.CartScreen.route)
                }
                is BooksEvents.AddAsFavourite ->{
                    // Book Detail Screen triggers the event, but the favourite management logic stays centralized.
                    favouriteViewModel.onEvent(FavouritesEvents.AddAsFavourite(events.bookID))
                }
                is BooksEvents.RemoveFromFavourites ->{
                    favouriteViewModel.onEvent(FavouritesEvents.RemoveFromFavourites(events.bookID))
                }

                else -> {
                    // do nothing
                }
            }
            booksViewModel.onEvent(events)
        })
}

@Preview(showBackground = true)
@Composable
fun BookDetailScreen(
    booksState: BooksState = BooksState(),
    favouriteViewModel: FavouriteViewModel = hiltViewModel<FavouriteViewModel>(),
    navController: NavController = rememberNavController(),
    onEvent: (BooksEvents) -> Unit = {}
) {

    val scrollState = rememberScrollState()
    val isFavourite = remember { mutableStateOf(false) }
    val favouriteMessage = remember { mutableStateOf("") }
    val isItemInCart = remember { mutableStateOf(false) }
    val context = LocalContext.current


    LaunchedEffect(booksState.selectedBookDetail) {
        isFavourite.value = booksState.selectedBookDetail?.isFavourite ?: false
        isItemInCart.value = booksState.selectedBookDetail?.isInCart ?: false
    }

    LaunchedEffect(booksState.error) {
        if (booksState.error.isNotEmpty()) {
            showToast(context = context, message = booksState.error)
            onEvent(BooksEvents.ClearErrorMessage)
        }
    }

    Scaffold(
        containerColor = SurfaceColor,
        topBar = {
            BookHubAppBar(title = stringResource(R.string.book_detail),
                canGoBack = true, navController = navController,
                favouriteViewModel = favouriteViewModel
            )
        },
        bottomBar = {
            BookDetailBottomCard(context = context,
                isItemInCart = booksState.selectedBookDetail?.isInCart ?: isItemInCart.value,
                isFavourite = isFavourite.value,
                addToCart = {
                    if (isItemInCart.value) {
                        onEvent(BooksEvents.GoToCart)
                    } else {
                        onEvent(BooksEvents.AddToCart(booksState.selectedBookDetail?.id ?: 0))
                    }

                    // first perform action and then change title ... todo remove this for final change
                    isItemInCart.value = !isItemInCart.value

                },
                onFavouriteChange = {

                    isFavourite.value = it
                    favouriteMessage.value = if (it) context.getString(R.string.book_added_to_your_favourites) else context.getString(R.string.book_removed_from_your_favourites)
                    showToast(context, favouriteMessage.value)

                    if (isFavourite.value) {
                        onEvent(BooksEvents.AddAsFavourite(booksState.selectedBookDetail?.id ?: 0))
                    } else {
                        onEvent(BooksEvents.RemoveFromFavourites(booksState.selectedBookDetail?.id ?: 0))
                    }

                })
        }) { contentPadding ->
        Surface(
            modifier = Modifier.padding(contentPadding),
            color = SurfaceColor
        ) {

            RenderScreen(
                isLoading = booksState.isLoading,
                onSuccess = {
                    Column(
                        horizontalAlignment = Alignment.Start,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 12.dp)
                            .padding(top = 12.dp)
                            .verticalScroll(
                                scrollState,
                                flingBehavior = ScrollableDefaults.flingBehavior()
                            ),
                    ) {
                        AsyncImage(
                            model = booksState.selectedBookDetail?.imageUrl ?: R.drawable.book,
                            contentDescription = stringResource(id = R.string.book),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(350.dp),
                            contentScale = ContentScale.Fit
                        )
                        Spacer(modifier = Modifier.padding(top = 12.dp))
                        Text(
                            text = booksState.selectedBookDetail?.name ?: "",
                            style = BookHubTypography.headlineSmall.copy(fontWeight = FontWeight.Medium),
                            overflow = TextOverflow.Ellipsis, maxLines = 2,
                        )
                        Spacer(modifier = Modifier.padding(top = 12.dp))
                        Text(
                            text = booksState.selectedBookDetail?.bookPublishedDate ?: "",
                            style = BookHubTypography.bodySmall.copy(color = TextSecondary),
                            overflow = TextOverflow.Ellipsis, maxLines = 1,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.padding(top = 12.dp))
                        Text(
                            text = "$${booksState.selectedBookDetail?.price ?: 0.0}",
                            style = BookHubTypography.headlineMedium.copy(color = PrimaryVariant),
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 2, lineHeight = 16.sp,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.padding(top = 12.dp))
                        Text(
                            text = booksState.selectedBookDetail?.description ?: "",
                            style = BookHubTypography.bodyMedium.copy(color = TextSecondary),
                        )

                    }
                },
            )
        }
    }

}

@Composable
fun BookDetailBottomCard(
    isItemInCart: Boolean,
    context: Context, isFavourite: Boolean,
    addToCart: () -> Unit,
    onFavouriteChange: (Boolean) -> Unit
) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        Card(shape = CircleShape,
            colors = CardDefaults.cardColors(Color.White),
            elevation = CardDefaults.cardElevation(4.dp),
            onClick = {
                onFavouriteChange(!isFavourite)
            }) {
            Image(
                painter = painterResource(id = if (isFavourite) R.drawable.favourite_checked else R.drawable.favourite_unchecked),
                contentDescription = if (isFavourite) context.getString(R.string.book_added_to_your_favourites) else context.getString(
                    R.string.book_removed_from_your_favourites
                ),
                modifier = Modifier
                    .size(40.dp)
                    .padding(8.dp)
            )
        }
        Button(
            onClick = { addToCart() },
            elevation = ButtonDefaults.elevatedButtonElevation(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryVariant)
        ) {
            Text(
                text = if (isItemInCart) stringResource(R.string.go_to_cart) else stringResource(R.string.add_to_cart),
                color = OnPrimaryColor,
            )
        }
    }

}
