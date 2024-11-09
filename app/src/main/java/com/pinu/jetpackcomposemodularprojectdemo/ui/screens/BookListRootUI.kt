package com.pinu.jetpackcomposemodularprojectdemo.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.pinu.jetpackcomposemodularprojectdemo.navigation.NavigationRoutes
import com.pinu.jetpackcomposemodularprojectdemo.ui.components.BookItem
import com.pinu.jetpackcomposemodularprojectdemo.ui.components.CommonAppBar
import com.pinu.jetpackcomposemodularprojectdemo.ui.theme.Pink

@Preview(showBackground = true)
@Composable
fun BookListRootUI(navController: NavController = rememberNavController()){

    // State for search query
    val searchQuery = remember { mutableStateOf(TextFieldValue("")) }

//    // Filter items based on the search query todo add it when list us used
//    val filteredItems = list.filter { it ->
//        it.contains(searchQuery.value.text, ignoreCase = true)
//    }

    val filterItem = remember { mutableIntStateOf(15) }


    Scaffold(
        topBar = {
            CommonAppBar(title = "Book List", canGoBack = true ,navController = navController)
        },
    ) { contentPadding ->
        Surface(modifier = Modifier.padding(contentPadding)) {

            Column {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
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
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.White,
                            focusedContainerColor = Color.White,
                            focusedIndicatorColor = Color.Transparent,  // Removes the focused underline
                            unfocusedIndicatorColor = Color.Transparent  // Removes the unfocused underline
                        ),
                        prefix = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "search", tint = Pink,
                            )

                        },
                        suffix = {
                            if (searchQuery.value.text.isNotEmpty()) {
                                Icon(
                                    imageVector = Icons.Default.Clear,
                                    contentDescription = "clear",
                                    tint = Color.Black,
                                    modifier = Modifier.clickable {
                                        searchQuery.value = TextFieldValue("")
                                        filterItem.intValue = 15
                                    }
                                )
                            }

                        },
                        placeholder = {
                            Text(
                                text = "Search book here...",
                                style = TextStyle(color = Color.Gray),
                                modifier = Modifier.padding(start = 8.dp),
                                maxLines = 1, overflow = TextOverflow.Ellipsis
                            )
                        },
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

}