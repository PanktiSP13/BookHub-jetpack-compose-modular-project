package com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.pinu.domain.entities.AppBarEvents
import com.pinu.domain.entities.AppBarState
import com.pinu.domain.entities.AppBarUIConfig
import com.pinu.domain.entities.events.BooksEvents
import com.pinu.domain.entities.states.BooksState
import com.pinu.domain.entities.states.FavouritesState
import com.pinu.domain.entities.viewmodels.BooksViewModel
import com.pinu.domain.entities.viewmodels.FavouriteViewModel
import com.pinu.jetpackcomposemodularprojectdemo.R
import com.pinu.jetpackcomposemodularprojectdemo.navigation.NavigationRoutes
import com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.components.BookHubAppBar
import com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.components.BookItem
import com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.theme.BookHubTypography
import com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.theme.SurfaceColor
import com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.theme.TextPrimary
import com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.util.RenderScreen

@Composable
fun BookListRootUI(
    navController: NavController
){

    val booksViewModel = hiltViewModel<BooksViewModel>()
    val favouriteViewModel = hiltViewModel<FavouriteViewModel>()

    BookListScreen(bookState =  booksViewModel.bookState.collectAsState().value,
        favouritesState = favouriteViewModel.favouriteState.collectAsState().value,
        navController = navController,
        onEvent = { event ->
            when (event) {
                is BooksEvents.NavigateToBookDetailScreen -> {
                    navController.navigate(NavigationRoutes.BookDetailScreen.getBookDetail())
                }
                else -> Unit
            }
            booksViewModel.onEvent(event)
        })
}

@Preview(showBackground = true)
@Composable
fun BookListScreen(
    bookState: BooksState = BooksState(),
    favouritesState: FavouritesState = FavouritesState(),
    navController: NavController = rememberNavController(),
    onEvent: (BooksEvents) -> Unit = {}) {

    val searchQuery = remember { mutableStateOf("") }

    // Trigger once and prevent re-execution when returning to the screen.
    LaunchedEffect(Unit) {
        bookState.bookList.takeIf { it.isEmpty() }?.let { onEvent(BooksEvents.GetBookList) }
    }

    Scaffold(
        containerColor = SurfaceColor,
        topBar = {
            BookHubAppBar(
                appBarUIConfig = AppBarUIConfig(title = stringResource(R.string.book_list), canGoBack = true),
                appBarState = AppBarState(favouriteState = favouritesState),
                appBarEvents = AppBarEvents(onBackPressed = { navController.popBackStack() })
            )
        },
    ) { contentPadding ->
        Column(modifier = Modifier.padding(contentPadding)) {

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceColor),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                TextField(
                    value = searchQuery.value,
                    onValueChange = { value ->
                        searchQuery.value = value
                        // based on requirement
                        if (searchQuery.value.length > 5) {
                            onEvent(BooksEvents.OnSearchBooksByName(searchQuery.value))
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary,
                        unfocusedContainerColor = Color.White,
                        focusedContainerColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,  // Removes the focused underline
                        unfocusedIndicatorColor = Color.Transparent, // Removes the unfocused underline
                    ),
                    prefix = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = stringResource(R.string.search),
                            tint = Color.LightGray,
                            modifier = Modifier.size(18.dp)
                        )
                    },
                    suffix = {
                        if (searchQuery.value.isNotEmpty()) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = stringResource(R.string.clear),
                                tint = Color.Black,
                                modifier = Modifier.clickable {
                                    searchQuery.value = ""
                                    onEvent(BooksEvents.OnSearchBooksByName(""))
                                }
                            )
                        }

                    },
                    placeholder = {
                        if (searchQuery.value.isEmpty()) {
                            Text(
                                text = stringResource(R.string.search_book_here),
                                style = BookHubTypography.bodyLarge.copy(color = Color.LightGray),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.padding(start = 4.dp)
                            )
                        }
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),
                    maxLines = 1
                )
            }

            RenderScreen(
                isLoading = bookState.isLoading,
                onSuccess = {
                    if (bookState.bookList.isNotEmpty()) {
                        LazyColumn {
                            items(bookState.bookList) { item ->
                                BookItem(item) {
                                    onEvent(BooksEvents.NavigateToBookDetailScreen(bookId = item.id,item = item))
                                }
                            }
                        }
                    }
                },
                onError = {

                }
            )
        }
    }
}
