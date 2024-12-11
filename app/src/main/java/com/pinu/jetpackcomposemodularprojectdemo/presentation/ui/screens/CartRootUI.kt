package com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.pinu.domain.entities.AppBarEvents
import com.pinu.domain.entities.AppBarUIConfig
import com.pinu.domain.entities.events.CartEvents
import com.pinu.domain.entities.events.SharedEvents
import com.pinu.domain.entities.states.CartState
import com.pinu.domain.entities.states.SharedState
import com.pinu.domain.entities.viewmodels.CartViewModel
import com.pinu.domain.entities.viewmodels.SharedViewModel
import com.pinu.jetpackcomposemodularprojectdemo.R
import com.pinu.jetpackcomposemodularprojectdemo.navigation.NavigationRoutes
import com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.components.BookHubAppBar
import com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.components.CartItem
import com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.theme.BookHubTypography
import com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.theme.GreenSuccess
import com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.theme.OnPrimaryColor
import com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.theme.PrimaryVariant
import com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.theme.SurfaceColor
import com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.util.CommonAlertDialog
import com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.util.showCustomToast

@Composable
fun CartRootUI(navController: NavController, sharedViewModel: SharedViewModel) {

    val cartViewModel = hiltViewModel<CartViewModel>()

    CartScreenUI(
        cartState = cartViewModel.cartState.collectAsState().value,
        sharedState = sharedViewModel.sharedState.collectAsState().value,
        navController = navController,
        onSharedEvents = sharedViewModel::onEvent,
    ) { event ->
        when (event) {

            is CartEvents.NavigateBack -> navController.popBackStack()

            is CartEvents.ContinueShoppingNavigateToDashBoard -> {
                // redirect back to dashboard with removing all stack entries
                navController.navigate(NavigationRoutes.DashboardScreen.route) {
                    // Clear the stack up to the root
                    popUpTo(0) { inclusive = true }
                    launchSingleTop = true
                }
            }

            is CartEvents.NavigateToBookDetailScreen -> {
                navController.navigate(route = NavigationRoutes.BookDetailScreen.getBookDetail(event.bookId))
            }

            else -> {}
        }

        cartViewModel.onEvent(event)
    }
}

@Preview(showBackground = true)
@Composable
fun CartScreenUI(
    cartState: CartState = CartState(),
    sharedState: SharedState = SharedState(),
    navController: NavController = rememberNavController(),
    onSharedEvents: (SharedEvents) -> Unit = {},
    onEvent: (CartEvents) -> Unit = {},
) {

    val context = LocalContext.current
    val showAlert = remember { mutableStateOf(false) }
    val showPaymentProceedDialog = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        onEvent(CartEvents.FetchCartHistory)
    }

    LaunchedEffect(sharedState.toastMessage) {
        sharedState.toastMessage.message.takeIf { it.isNotEmpty() }?.let {
            showCustomToast(context = context, toastMessage = sharedState.toastMessage)
            onSharedEvents(SharedEvents.ClearToastMessage)
        }
    }

    Scaffold(
        containerColor = SurfaceColor,
        topBar = {
            BookHubAppBar(
                appBarUIConfig = AppBarUIConfig(
                    title = stringResource(R.string.cart),
                    canGoBack = true, isCartVisible = false, isFavouritesVisible = false
                ),
                appBarEvents = AppBarEvents(onBackPressed = { navController.popBackStack() })
            )
        },
        bottomBar = {
            if (cartState.cartItemResponse?.data?.items?.isNotEmpty() == true){
                Button(
                    onClick = {
                        showPaymentProceedDialog.value = true
                    },
                    elevation = ButtonDefaults.elevatedButtonElevation(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PrimaryVariant
                    )
                ) {
                    Text(
                        text = stringResource(R.string.proceed_to_checkout),
                        color = OnPrimaryColor
                    )
                    if (cartState.isLoadingForPayment) CircularProgressIndicator(
                        modifier = Modifier
                            .padding(start = 12.dp)
                            .size(15.dp)
                    )
                }
            }
        }
    ) { contentPadding ->
        Surface(
            modifier = Modifier.padding(contentPadding),
            color = Color.White
        ) {

            if (!cartState.cartItemResponse?.data?.items.isNullOrEmpty()) {
                LazyColumn {
                    items(items = cartState.cartItemResponse?.data?.items ?: emptyList()) {
                        CartItem(cartItem = it, onEvent = onEvent)
                    }
                    item {
                        Column(modifier = Modifier
                            .padding(12.dp)
                            .fillMaxWidth()) {
                            HorizontalDivider(thickness = 1.dp, color = Color.Gray)
                            Spacer(modifier = Modifier.height(30.dp))
                            Row {
                                Text(stringResource(R.string.total_price), style = BookHubTypography.titleMedium.copy(fontWeight = FontWeight.Bold))
                                Spacer(modifier = Modifier.weight(1f))
                                Text("₹ ${cartState.cartItemResponse?.data?.totalPrice?:0.0}")
                            }
                            Spacer(modifier = Modifier.height(12.dp))
                            Row {
                                Text(stringResource(R.string.total_items), style = BookHubTypography.titleMedium.copy(fontWeight = FontWeight.Bold))
                                Spacer(modifier = Modifier.weight(1f))
                                Text("${cartState.cartItemResponse?.data?.totalItems?:0}")
                            }
                        }
                    }


                }


            } else {
                if (cartState.isLoading.not() && cartState.isLoadingForPayment.not() &&
                    cartState.cartItemResponse?.data?.items?.isEmpty() == true
                ) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(18.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            Image(
                                painter = painterResource(id = R.drawable.payment_success),
                                contentDescription = stringResource(R.string.payment_success),
                                modifier = Modifier.size(70.dp),
                                contentScale = ContentScale.Crop
                            )
                            Spacer(modifier = Modifier.padding(top = 16.dp))
                            Text(
                                text = stringResource(R.string.payment_successful),
                                style = BookHubTypography.headlineSmall.copy(fontWeight = FontWeight.Medium),
                            )
                            Spacer(modifier = Modifier.padding(top = 16.dp))
                            Text(
                                text = stringResource(R.string.payment_success_desc),
                                style = BookHubTypography.titleSmall,
                                textAlign = TextAlign.Center,
                            )
                            Spacer(modifier = Modifier.padding(top = 16.dp))
                            ElevatedButton(
                                onClick = { onEvent(CartEvents.ContinueShoppingNavigateToDashBoard) },
                                shape = RoundedCornerShape(8.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = GreenSuccess)
                            ) {
                                Text(
                                    text = stringResource(R.string.continue_shopping),
                                    style = TextStyle(color = Color.White)
                                )
                            }

                        }
                    }
                }
            }
        }
    }

    if (showAlert.value) {
        CommonAlertDialog(
            onNegativeButtonClicked = {
                showAlert.value = false
            },
            onPositiveButtonClicked = {
                showAlert.value = false
            })
    }
    if (showPaymentProceedDialog.value) {
        CommonAlertDialog(
            title = stringResource(R.string.are_you_sure_you_want_to_proceed),
            positiveButtonText = stringResource(R.string.yes),
            negativeButtonText = stringResource(R.string.no),
            onNegativeButtonClicked = {
                showPaymentProceedDialog.value = false
            }, onPositiveButtonClicked = {
                onEvent(CartEvents.ClearCart)
                showPaymentProceedDialog.value = false
        })
    }

}
