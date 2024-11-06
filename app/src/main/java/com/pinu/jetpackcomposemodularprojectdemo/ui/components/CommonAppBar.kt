package com.pinu.jetpackcomposemodularprojectdemo.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pinu.jetpackcomposemodularprojectdemo.R
import com.pinu.jetpackcomposemodularprojectdemo.ui.theme.Pink
import com.pinu.jetpackcomposemodularprojectdemo.ui.theme.Pink80

@OptIn(ExperimentalMaterial3Api::class)
@Preview()
@Composable
fun CommonAppBar(title: String = "Dashboard", onCartClick: () -> Unit = {}) {
    Surface(color = Pink) {
        TopAppBar(
            title = { Text(text = title, modifier = Modifier.fillMaxWidth()) },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = Pink80),
            actions = {
                IconButton(onClick = {
                    onCartClick()
                }) {
                    Image(
                        painter = painterResource(id = R.drawable.cart),
                        modifier = Modifier.size(25.dp),
                        contentDescription = "cart"
                    )
                }
            }
        )
    }

}