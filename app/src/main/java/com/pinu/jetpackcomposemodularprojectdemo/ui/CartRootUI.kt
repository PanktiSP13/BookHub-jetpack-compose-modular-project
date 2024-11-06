package com.pinu.jetpackcomposemodularprojectdemo.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.pinu.jetpackcomposemodularprojectdemo.navigation.NavigationRoutes
import com.pinu.jetpackcomposemodularprojectdemo.ui.components.BookItem
import com.pinu.jetpackcomposemodularprojectdemo.ui.components.CommonAppBar
import com.pinu.jetpackcomposemodularprojectdemo.ui.theme.Pink80

@Preview(showBackground = true)
@Composable
fun CartRootUI(navController: NavController= rememberNavController()){
    Scaffold(
        topBar = {
            CommonAppBar(title = "Cart", canGoBack = true ,
                isCartVisible = false, onBackPressed = {
                    navController.popBackStack()
            })
        },
        bottomBar = {
            Button(
                onClick = { },
                elevation = ButtonDefaults.elevatedButtonElevation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonColors(
                    containerColor = Pink80,
                    contentColor = Pink80,
                    disabledContentColor = Color.Gray,
                    disabledContainerColor = Color.Gray
                )
            ) {
                Text(
                    text = "Proceed to Checkout", color = Color.White,
                    style = TextStyle(fontWeight = FontWeight.Bold)
                )


            }
        }
    ) { contentPadding ->
        Surface(modifier = Modifier.padding(contentPadding)) {

            LazyColumn {
                items(20){
                    Text("Pinu")
                }
            }
        }
    }



}