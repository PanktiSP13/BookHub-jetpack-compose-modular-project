package com.pinu.jetpackcomposemodularprojectdemo.ui.dialog

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.pinu.jetpackcomposemodularprojectdemo.R
import com.pinu.jetpackcomposemodularprojectdemo.ui.theme.GreenSuccess

@Preview
@Composable
fun PaymentSuccessfulDialog(
    onDismiss: () -> Unit = {},
    onContinueShoppingClicked: () -> Unit = {}
) {
    Dialog(
        onDismissRequest = {
            onDismiss()
        }, properties = DialogProperties(
            dismissOnClickOutside = true,
            dismissOnBackPress = true,
        )
    ) {

        Card(
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(4.dp),
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 200.dp),
            shape = RoundedCornerShape(16.dp)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Image(
                    painter = painterResource(id = R.drawable.payment_success),
                    contentDescription = "payment success",
                    modifier = Modifier
                        .size(70.dp),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.padding(top = 16.dp))
                Text(
                    text = stringResource(R.string.payment_successful),
                    style = TextStyle(
                        fontSize = 16.sp,
                        color = Color.Black, fontWeight = FontWeight.W500,
                    ),
                )
                Spacer(modifier = Modifier.padding(top = 16.dp))
                Text(
                    text = stringResource(R.string.payment_success_desc),
                    style = TextStyle(
                        fontSize = 12.sp,
                        color = Color.Black, fontWeight = FontWeight.W300,
                    ),
                    textAlign = TextAlign.Center,
                )
                Spacer(modifier = Modifier.padding(top = 16.dp))
                ElevatedButton(
                    onClick = { onContinueShoppingClicked() },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = GreenSuccess)
                ) {
                    Text(text = stringResource(R.string.continue_shopping))
                }

            }
        }
    }
}