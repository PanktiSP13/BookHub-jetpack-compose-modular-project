package com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.books.screens

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
import com.pinu.domain.entities.events.BooksEvents
import com.pinu.domain.entities.states.BooksState
import com.pinu.domain.entities.viewmodels.BooksViewModel
import com.pinu.jetpackcomposemodularprojectdemo.R
import com.pinu.jetpackcomposemodularprojectdemo.navigation.NavigationRoutes
import com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.components.BookItem
import com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.components.CommonAppBar
import com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.theme.BookHubTypography
import com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.theme.SurfaceColor
import com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.theme.TextPrimary

@Composable
fun BookListRootUI(navController: NavController = rememberNavController()){

    val booksViewModel = hiltViewModel<BooksViewModel>()
    val bookState = booksViewModel.bookState.collectAsState()

    BookListScreen(bookState = bookState.value,
        navController = navController,
        onEvent = { event ->
            when (event) {
                is BooksEvents.NavigateToBookDetailScreen -> {
                    navController.navigate(NavigationRoutes.BookDetailScreen.route)
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
    navController: NavController = rememberNavController(),
    onEvent: (BooksEvents) -> Unit = {}) {

    val searchQuery = remember { mutableStateOf("") }

    // Trigger API call when the composable loads
    LaunchedEffect(Unit) {
        onEvent(BooksEvents.GetBookList)
    }

    Scaffold(
        containerColor = SurfaceColor,
        topBar = {
            CommonAppBar(
                title = stringResource(R.string.book_list),
                canGoBack = true,
                navController = navController
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
                        onEvent(BooksEvents.OnSearchBooksByName(value))
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
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text, imeAction = ImeAction.Done
                    )
                )
            }

            if (bookState.bookList.isNotEmpty()){
                LazyColumn {
                    items(bookState.bookList) { item->
                        BookItem(item) {
                            onEvent(BooksEvents.NavigateToBookDetailScreen(item.id))
                        }
                    }
                }
            }
        }
    }

}
