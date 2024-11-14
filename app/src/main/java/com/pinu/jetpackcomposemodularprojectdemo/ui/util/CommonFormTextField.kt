package com.pinu.jetpackcomposemodularprojectdemo.ui.util

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pinu.jetpackcomposemodularprojectdemo.ui.theme.Pink

@Preview(showBackground = true)
@Composable
fun CommonFormTextField(
    modifier: Modifier = Modifier,
    txtValue: String = "John Doe",
    labelTxt: String = "Name",
    errorMessage: String = "",
    isReadOnly: Boolean = false,
    isSingleLine: Boolean = false,
    onValueChange: (String) -> Unit = {},
    focusRequester: FocusRequester = FocusRequester(),
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
) {

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 4.dp)
            .focusRequester(focusRequester)
    ) {
        OutlinedTextField(
            value = txtValue ?: "",
            readOnly = isReadOnly,
            onValueChange = { value ->
                onValueChange(value)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            label = {
                Text(
                    text = labelTxt, style = TextStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.W400,
                        fontFamily = FontFamily.SansSerif,
                        color = Color.Gray
                    )
                )
            },
            singleLine = isSingleLine,
            keyboardActions = keyboardActions,
            keyboardOptions = keyboardOptions,
            shape = RoundedCornerShape(10.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                disabledTextColor = Color.LightGray,
                focusedBorderColor = Pink,
                unfocusedBorderColor = Color.LightGray,
                focusedLabelColor = Pink,
                errorBorderColor = Color.Red,
                // you can add as per your requirement
            )
        )
    }

}