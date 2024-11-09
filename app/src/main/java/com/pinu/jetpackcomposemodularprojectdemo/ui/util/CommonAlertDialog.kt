package com.pinu.jetpackcomposemodularprojectdemo.ui.util

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.pinu.jetpackcomposemodularprojectdemo.R
import com.pinu.jetpackcomposemodularprojectdemo.ui.theme.Pink
import com.pinu.jetpackcomposemodularprojectdemo.ui.theme.Pink80

@Preview
@Composable
fun CommonAlertDialog(
    title: String = stringResource(R.string.alert),
    text: String = stringResource(R.string.dialog__item_remove_desc),
    positiveButtonText: String = stringResource(R.string.ok),
    negativeButtonText: String = stringResource(R.string.cancel),
    isDismissible: Boolean = true,
    onDismiss: () -> Unit = {},
    onPositiveButtonClicked: () -> Unit={},
    onNegativeButtonClicked: () -> Unit={},
) {
    AlertDialog(
        containerColor = Color.White,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        title = {
            Text(text = title,
                modifier = Modifier.fillMaxWidth(),
                style = TextStyle(fontSize = 22.sp, fontWeight = FontWeight.W600))
        },
        text = { Text(text = text) },
        properties = DialogProperties(
            dismissOnClickOutside = isDismissible
        ),
        tonalElevation = 6.dp,
        onDismissRequest = { onDismiss() },
        dismissButton = {
            Button(
                onClick = { onNegativeButtonClicked() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Pink80,
                    contentColor = Pink
                )
            ) {
                Text(negativeButtonText)
            }
        },
        confirmButton = {
            Button(
                onClick = { onPositiveButtonClicked() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Pink,
                    contentColor = Color.White
                )
            ) {
                Text(positiveButtonText)
            }
        },
    )

}