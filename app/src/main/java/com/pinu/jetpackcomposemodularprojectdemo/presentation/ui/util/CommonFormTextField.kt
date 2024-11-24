package com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.util

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.theme.BookHubTypography
import com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.theme.PrimaryColor
import com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.theme.TextPrimary
import com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.theme.TextSecondary

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
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
) {

    Column(
        modifier = modifier.fillMaxWidth().padding(horizontal = 12.dp)) {
        OutlinedTextField(
            value = txtValue,
            readOnly = isReadOnly,
            onValueChange = { value ->
                onValueChange(value)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            label = {
                Text(
                    text = labelTxt,
                    style = BookHubTypography.labelSmall.copy(color = TextSecondary)
                )
            },
            singleLine = isSingleLine,
            keyboardActions = keyboardActions,
            keyboardOptions = keyboardOptions,
            shape = RoundedCornerShape(10.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = TextPrimary,
                unfocusedTextColor = TextPrimary,
                disabledTextColor = TextSecondary,
                focusedBorderColor = PrimaryColor,
                unfocusedBorderColor = Color.LightGray,
                focusedLabelColor = PrimaryColor,
                errorBorderColor = Color.Red,
                // you can add as per your requirement
            )
        )
    }

}