package com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.screens

import android.content.Context
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
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
import com.pinu.domain.entities.AppBarEvents
import com.pinu.domain.entities.AppBarState
import com.pinu.domain.entities.AppBarUIConfig
import com.pinu.domain.entities.ToastMessage
import com.pinu.domain.entities.ToastMessageType
import com.pinu.domain.entities.events.BooksEvents
import com.pinu.domain.entities.events.CartEvents
import com.pinu.domain.entities.events.FavouritesEvents
import com.pinu.domain.entities.events.SharedEvents
import com.pinu.domain.entities.network_service.request.AddToCartRequest
import com.pinu.domain.entities.states.BooksState
import com.pinu.domain.entities.states.CartState
import com.pinu.domain.entities.states.FavouritesState
import com.pinu.domain.entities.states.SharedState
import com.pinu.domain.entities.viewmodels.BooksViewModel
import com.pinu.domain.entities.viewmodels.CartViewModel
import com.pinu.domain.entities.viewmodels.FavouriteViewModel
import com.pinu.domain.entities.viewmodels.SharedViewModel
import com.pinu.jetpackcomposemodularprojectdemo.R
import com.pinu.jetpackcomposemodularprojectdemo.navigation.NavigationRoutes
import com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.components.BookHubAppBar
import com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.components.QuantityItem
import com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.theme.BookHubTypography
import com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.theme.OnPrimaryColor
import com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.theme.PrimaryVariant
import com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.theme.SurfaceColor
import com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.theme.TextSecondary
import com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.util.RenderScreen
import com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.util.showCustomToast

@Composable
fun BookDetailRootUI(navController: NavController, bookId: Int = 0, sharedViewModel: SharedViewModel) {

    val booksViewModel = hiltViewModel<BooksViewModel>()
    val favouriteViewModel = hiltViewModel<FavouriteViewModel>()
    val cartViewModel = hiltViewModel<CartViewModel>()

    BookDetailScreen(
        bookId,
        booksState = booksViewModel.bookState.collectAsState().value,
        cartState = cartViewModel.cartState.collectAsState().value,
        favouritesState = favouriteViewModel.favouriteState.collectAsState().value,
        sharedState = sharedViewModel.sharedState.collectAsState().value,
        navController = navController,
        onSharedEvents = sharedViewModel::onEvent,
        onEvent = { events ->
            when (events) {
                is BooksEvents.NavigateBack -> navController.popBackStack()
                is BooksEvents.NavigateToCartScreen -> {
                    navController.navigate(route = NavigationRoutes.CartScreen.route)
                }
                else -> {
                    // do nothing
                }
            }
            booksViewModel.onEvent(events)
        },
        onCartEvent = cartViewModel::onEvent,
        onFavouriteEvent = favouriteViewModel::onEvent
    )
}

@Preview(showBackground = true)
@Composable
fun BookDetailScreen(
    bookId: Int = 0,
    booksState: BooksState = BooksState(),
    cartState: CartState = CartState(),
    favouritesState: FavouritesState = FavouritesState(),
    sharedState: SharedState = SharedState(),
    navController: NavController = rememberNavController(),
    onEvent: (BooksEvents) -> Unit = {},
    onCartEvent: (CartEvents) -> Unit = {},
    onFavouriteEvent: (FavouritesEvents) -> Unit = {},
    onSharedEvents: (SharedEvents) -> Unit = {},
) {


    val scrollState = rememberScrollState()
    val isFavourite = remember { mutableStateOf(false) }
    val isItemInCart = remember { mutableStateOf(false) }
    val context = LocalContext.current

    val qty = remember { mutableIntStateOf(1) }

    LaunchedEffect(key1 = bookId) {
        bookId.takeIf { it != 0 }.let {
            onEvent(BooksEvents.FetchBookDetails(bookId))
        }
    }

    LaunchedEffect(booksState.selectedBookDetail) {
        isFavourite.value = booksState.selectedBookDetail?.isFavourite ?: false
        isItemInCart.value = booksState.selectedBookDetail?.isInCart ?: false
    }

    LaunchedEffect(key1 = sharedState.toastMessage) {
        showCustomToast(context = context, toastMessage = sharedState.toastMessage)
        onSharedEvents(SharedEvents.ClearToastMessage)
    }


    LaunchedEffect(key1 = cartState.reloadBookDetail) {
        cartState.reloadBookDetail.takeIf { it }?.let {
            onEvent(BooksEvents.FetchBookDetails(bookId = booksState.selectedBookDetail?.id ?: 0))
            onCartEvent(CartEvents.ValueUpdateReloadBookDetail)
        }
    }


    fun addToCartCTA() {
        if (isItemInCart.value) {
            onEvent(BooksEvents.NavigateToCartScreen)
        } else {
            onCartEvent(
                CartEvents.AddToCart(
                    isFromBookDetail = true,
                    AddToCartRequest(
                        bookId = booksState.selectedBookDetail?.id ?: 0,
                        qty = qty.intValue
                    )
                )
            )
        }
    }

    fun onFavouriteCTA(isFavourited: Boolean) {
        isFavourite.value = isFavourited
        if (isFavourite.value) {
            onFavouriteEvent(
                FavouritesEvents.AddAsFavourite(
                    booksState.selectedBookDetail?.id ?: 0
                )
            )
        } else {
            onFavouriteEvent(
                FavouritesEvents.RemoveFromFavourites(
                    booksState.selectedBookDetail?.id ?: 0
                )
            )
        }
    }

    fun onUpdateQuantityCTA(isQtyDecrease: Boolean) {
        if (isQtyDecrease) {
            if (qty.intValue > 1) {
                qty.intValue -= 1
            } else {
                showCustomToast(
                    context,
                    toastMessage = ToastMessage(
                        type = ToastMessageType.WARNING,
                        message = context.getString(R.string.qty_validation)
                    )
                )
            }
        } else {
            qty.intValue += 1
        }
    }


    Scaffold(
        containerColor = SurfaceColor,
        topBar = {
            BookHubAppBar(
                appBarUIConfig = AppBarUIConfig(title = stringResource(R.string.book_detail), canGoBack = true),
                appBarState = AppBarState(favouriteState = favouritesState),
                appBarEvents = AppBarEvents(onBackPressed = { navController.popBackStack() })
            )
        },
        bottomBar = {
            BookDetailBottomCard(context = context,
                isLoading = cartState.isLoading,
                isItemInCart = booksState.selectedBookDetail?.isInCart ?: isItemInCart.value,
                isFavourite = isFavourite.value,
                addToCart = ::addToCartCTA,
                onFavouriteChange = ::onFavouriteCTA
            )
        }) { contentPadding ->
        Surface(
            modifier = Modifier.padding(contentPadding),
            color = SurfaceColor
        ) {

            RenderScreen(
                isLoading = booksState.isLoading && bookId !=0,
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
                            model = booksState.selectedBookDetail?.imageUrl ?: R.drawable.img_placeholder,
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
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 2,
                        )
                        Spacer(modifier = Modifier.padding(top = 12.dp))
                        Text(
                            text = booksState.selectedBookDetail?.bookPublishedDate ?: "",
                            style = BookHubTypography.bodySmall.copy(color = TextSecondary),
                            overflow = TextOverflow.Ellipsis, maxLines = 1,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.padding(top = 12.dp))

                        QuantityItem(qtyValue = qty.intValue,
                            onQtyIncrease = { onUpdateQuantityCTA(false) },
                            onQtyDecrease = { onUpdateQuantityCTA(true) })

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
                }
            )

        }
    }
}


@Composable
fun BookDetailBottomCard(
    isLoading: Boolean,
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
            onClick = { onFavouriteChange(!isFavourite) }) {
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
            Spacer(modifier = Modifier.width(12.dp))
            if (isLoading) CircularProgressIndicator(
                color = Color.White,
                modifier = Modifier.size(15.dp)
            )
        }
    }

}
