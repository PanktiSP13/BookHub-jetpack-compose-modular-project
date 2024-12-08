package com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pinu.jetpackcomposemodularprojectdemo.R
import com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.theme.BookHubTypography
import com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.theme.PrimaryColor

@Preview(showBackground = true)
@Composable
fun QuantityItem(qtyValue: Int = 1,
                 onQtyDecrease: () -> Unit={},
                 onQtyIncrease: () -> Unit={}) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(6.dp)
    ) {
        Text(
            text = stringResource(R.string.qty),
            style = BookHubTypography.bodySmall
        )
        Box(contentAlignment = Alignment.Center) {
            IconButton(
                onClick = {
                    onQtyDecrease()
                },
                modifier = Modifier
                    .padding(start = 6.dp)
                    .size(25.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.remove),
                    contentDescription = stringResource(R.string.add),
                    tint = if (qtyValue > 1) Color.Black else Color.LightGray,
                )
            }
        }

        Spacer(modifier = Modifier.padding(start = 4.dp))
        Box(
            modifier = Modifier
                .padding(start = 6.dp)
                .defaultMinSize(minWidth = 25.dp, minHeight = 25.dp)
                .border(
                    border = BorderStroke(
                        width = 1.dp,
                        color = PrimaryColor
                    ),
                    shape = RoundedCornerShape(4.dp)
                ), contentAlignment = Alignment.Center
        ) {
            Text(
                text = qtyValue.toString(),
                style = BookHubTypography.bodyMedium
            )
        }
        Spacer(modifier = Modifier.padding(start = 4.dp))
        Box(contentAlignment = Alignment.Center) {
            IconButton(
                onClick = { onQtyIncrease() },
                modifier = Modifier
                    .padding(start = 6.dp)
                    .size(25.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(id = R.string.add)
                )
            }
        }
    }
}