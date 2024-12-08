package com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.util

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun RenderScreen(
    isLoading: Boolean = false,
    showError : Boolean = false,
    onSuccess: @Composable () -> Unit,
    onLoading: @Composable () -> Unit = {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { CircularProgressIndicator() }
    },
    onError: @Composable () -> Unit = {},
    modifier: Modifier? = null
) {
    Box(
        modifier = modifier ?: Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        when {
            isLoading -> onLoading()
            showError -> onError()
            else -> onSuccess()
        }
    }
}