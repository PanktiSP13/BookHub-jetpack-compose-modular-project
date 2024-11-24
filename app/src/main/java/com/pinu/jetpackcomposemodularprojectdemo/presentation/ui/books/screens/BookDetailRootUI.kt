package com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.books.screens

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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.pinu.jetpackcomposemodularprojectdemo.R
import com.pinu.jetpackcomposemodularprojectdemo.navigation.NavigationRoutes
import com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.components.CommonAppBar
import com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.theme.BookHubTypography
import com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.theme.OnPrimaryColor
import com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.theme.PrimaryVariant
import com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.theme.SurfaceColor
import com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.theme.TextSecondary
import com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.theme.dummyBookDate
import com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.theme.dummyDescription
import com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.theme.dummyString
import com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.util.showToast

@Preview(showBackground = true)
@Composable
fun BookDetailRootUI(navController: NavController = rememberNavController()) {
    val scrollState = rememberScrollState()
    val isFavourite = remember { mutableStateOf(false) }
    val favouriteMessage = remember { mutableStateOf("") }
    val context = LocalContext.current


    Scaffold(
        containerColor = SurfaceColor,
        topBar = {
            CommonAppBar(title = stringResource(R.string.book_detail),
                canGoBack = true, navController = navController)
        },
        bottomBar = {
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
                        isFavourite.value = !isFavourite.value
                        favouriteMessage.value = if (isFavourite.value) "Book added to your favourites" else "Book removed from your favourites"
                        showToast(context, favouriteMessage.value)

                    }) {
                    Image(
                        painter = painterResource(id = if (isFavourite.value) R.drawable.favourite_checked else R.drawable.favourite_unchecked),
                        contentDescription = favouriteMessage.value,
                        modifier = Modifier
                            .size(40.dp)
                            .padding(8.dp)
                    )
                }
                Button(
                    onClick = {
                        navController.navigate(NavigationRoutes.CartScreen.route)
                    },
                    elevation = ButtonDefaults.elevatedButtonElevation(),
                    modifier = Modifier.fillMaxWidth().padding(start = 12.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryVariant)
                ) {
                    Text(
                        text = stringResource(R.string.add_to_cart),
                        color = OnPrimaryColor,)


                }
            }

        }
    ) { contentPadding ->
        Surface(modifier = Modifier.padding(contentPadding), color = SurfaceColor) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 12.dp)
                    .padding(top = 12.dp)
                    .verticalScroll(
                        scrollState, flingBehavior = ScrollableDefaults.flingBehavior()
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter =/* rememberAsyncImagePainter(
                        model = "https://m.media-amazon.com/images/I/81gRz9A4F6L._UF1000,1000_QL80_.jpg"
                    )?:*/ painterResource(id = R.drawable.book),
                    contentDescription = stringResource(id = R.string.book),
                    modifier = Modifier.fillMaxWidth().height(350.dp),
                    contentScale = ContentScale.Fit
                )
                Spacer(modifier = Modifier.padding(top = 12.dp))
                Text(
                    text = dummyString,
                    style = BookHubTypography.headlineSmall.copy(fontWeight = FontWeight.Medium),
                    overflow = TextOverflow.Ellipsis, maxLines = 2,
                    lineHeight = 18.sp,
                )
                Spacer(modifier = Modifier.padding(top = 12.dp))
                Text(
                    text = dummyBookDate,
                    style = BookHubTypography.bodySmall.copy(color = TextSecondary),
                    overflow = TextOverflow.Ellipsis, maxLines = 1,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.padding(top = 12.dp))
                Text(
                    text = "$500", style = BookHubTypography.headlineMedium.copy(color = PrimaryVariant), overflow = TextOverflow.Ellipsis,
                    maxLines = 2, lineHeight = 16.sp,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.padding(top = 12.dp))
                Text(
                    text = "$dummyDescription \n\n $dummyDescription \n\n $dummyDescription \n\n $dummyDescription \n\n $dummyDescription ",
                    style = BookHubTypography.bodyMedium.copy(color = TextSecondary),
                )

            }
        }
    }

}
