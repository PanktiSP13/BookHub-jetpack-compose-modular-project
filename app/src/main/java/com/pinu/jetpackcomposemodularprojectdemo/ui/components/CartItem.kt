package com.pinu.jetpackcomposemodularprojectdemo.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.pinu.jetpackcomposemodularprojectdemo.R
import com.pinu.jetpackcomposemodularprojectdemo.ui.theme.Pink
import com.pinu.jetpackcomposemodularprojectdemo.ui.theme.Pink80
import com.pinu.jetpackcomposemodularprojectdemo.ui.theme.dummyString
import com.pinu.jetpackcomposemodularprojectdemo.ui.theme.dummyUrl

@Preview(showBackground = false)
@Composable
fun CartItem() {
    val qty = remember {
        mutableIntStateOf(1)
    }
    val showAlert = remember {
        mutableStateOf(false)
    }

    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier.padding(vertical = 8.dp, horizontal = 12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.book) ?: rememberAsyncImagePainter(
                    dummyUrl
                ),
                contentDescription = "book",
                modifier = Modifier
                    .size(width = 100.dp, height = 170.dp)
                    .clip(RoundedCornerShape(6.dp)),
                contentScale = ContentScale.FillHeight
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                Text(
                    text = dummyString, style = TextStyle(
                        fontFamily = FontFamily.SansSerif,
                        fontSize = 16.sp,
                        color = Color.Black, fontWeight = FontWeight.W400,
                    ), overflow = TextOverflow.Ellipsis, maxLines = 2, lineHeight = 16.sp
                )
                Spacer(modifier = Modifier.padding(top = 35.dp))
                Text(
                    text = "$500", style = TextStyle(
                        fontFamily = FontFamily.SansSerif,
                        fontSize = 20.sp,
                        color = Color.Black, fontWeight = FontWeight.Bold,
                    ), overflow = TextOverflow.Ellipsis, maxLines = 2, lineHeight = 16.sp
                )
                Spacer(modifier = Modifier.padding(top = 12.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Qty : ", style = TextStyle(fontSize = 16.sp))
                    Spacer(modifier = Modifier.padding(start = 4.dp))
                    Box(contentAlignment = Alignment.Center) {
                        IconButton(onClick = {
                            if (qty.intValue > 1) {
                                qty.intValue -= 1
                            } else {
                                showAlert.value = true
                            }
                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.remove),
                                contentDescription = "Add",
                                tint = if (qty.intValue > 1) Color.Black else Color.LightGray
                            )
                        }
                    }

                    Spacer(modifier = Modifier.padding(start = 4.dp))
                    Box(
                        modifier = Modifier
                            .defaultMinSize(minWidth = 30.dp, minHeight = 30.dp)
                            .border(
                                border = BorderStroke(width = 1.dp, color = Pink),
                                shape = RoundedCornerShape(4.dp)
                            ), contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "${qty.intValue}",
                            color = Color.Black,
                            style = TextStyle(fontSize = 14.sp)
                        )
                    }
                    Spacer(modifier = Modifier.padding(start = 4.dp))
                    Box(contentAlignment = Alignment.Center) {
                        IconButton(onClick = {
                            qty.intValue += 1
                        }) {
                            Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
                        }
                    }
                    Spacer(modifier = Modifier.padding(start = 12.dp))
                    Button(
                        onClick = {
                            showAlert.value = true
                        },
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(0.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Pink80)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.Clear, contentDescription = "remove")
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

}
