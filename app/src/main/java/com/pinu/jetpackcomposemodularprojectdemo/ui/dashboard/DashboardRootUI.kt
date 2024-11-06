package com.pinu.jetpackcomposemodularprojectdemo.ui.dashboard

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pinu.jetpackcomposemodularprojectdemo.ui.components.BookItem
import com.pinu.jetpackcomposemodularprojectdemo.ui.components.CommonAppBar

@Preview(showBackground = true)
@Composable
fun DashboardRootUI() {
    Scaffold(
        topBar = {
            CommonAppBar(title = "Dashboard", onCartClick = {

            })
        },
    ) { contentPadding ->
        Surface(modifier = Modifier.padding(contentPadding)) {

            LazyColumn(modifier = Modifier.padding(top = 12.dp)) {
                items(15) { it ->
                    BookItem()
                }
            }
        }
    }
}

