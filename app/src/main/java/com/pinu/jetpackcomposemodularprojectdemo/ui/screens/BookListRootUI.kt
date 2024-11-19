package com.pinu.jetpackcomposemodularprojectdemo.ui.screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.pinu.jetpackcomposemodularprojectdemo.R
import com.pinu.jetpackcomposemodularprojectdemo.navigation.NavigationRoutes
import com.pinu.jetpackcomposemodularprojectdemo.ui.components.BookItem
import com.pinu.jetpackcomposemodularprojectdemo.ui.components.CommonAppBar
import com.pinu.jetpackcomposemodularprojectdemo.ui.theme.BookHubTypography
import com.pinu.jetpackcomposemodularprojectdemo.ui.theme.SurfaceColor
import com.pinu.jetpackcomposemodularprojectdemo.ui.theme.TextPrimary
import com.pinu.jetpackcomposemodularprojectdemo.ui.util.getBookList

@Preview(showBackground = true)
@Composable
fun BookListRootUI(navController: NavController = rememberNavController()){

    val context = LocalContext.current
    // State for search query
    val searchQuery = remember { mutableStateOf(TextFieldValue("")) }

//    // Filter items based on the search query todo add it when list us used
//    val filteredItems = list.filter { it ->
//        it.contains(searchQuery.value.text, ignoreCase = true)
//    }

    val filterItem = remember { mutableIntStateOf(15) }

    LaunchedEffect(true) {
        val bookList = getBookList(context)
        Log.e("@@@@", "BookListRootUI: $bookList")
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
                            if (value.text.isEmpty()) {
                                filterItem.intValue = 15
                            } else {
                                filterItem.intValue = 1
                            }

                            searchQuery.value = value
                        },
                        modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(8.dp),
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
                                contentDescription = "search",
                                tint = Color.LightGray,
                                modifier = Modifier.size(18.dp)
                            )
                        },
                        suffix = {
                            if (searchQuery.value.text.isNotEmpty()) {
                                Icon(
                                    imageVector = Icons.Default.Clear,
                                    contentDescription = stringResource(R.string.clear),
                                    tint = Color.Black,
                                    modifier = Modifier.clickable {
                                        searchQuery.value = TextFieldValue("")
                                        filterItem.intValue = 15
                                    }
                                )
                            }

                        },
                        placeholder = {
                            if (searchQuery.value.text.isEmpty()) {
                                Text(
                                    text = stringResource(R.string.search_book_here),
                                    style = BookHubTypography.bodyLarge.copy(color = Color.LightGray),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier.padding(start = 4.dp)
                                )
                            }
                        }, keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text, imeAction = ImeAction.Done
                        )
                    )
                }

                LazyColumn {
                    items(filterItem.intValue) {
                        BookItem {
                            navController.navigate(NavigationRoutes.BookDetailScreen.route)
                        }
                    }
                }
            }
        }
    }
