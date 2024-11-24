package com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.util

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CheckPermissions(
    permissions: List<String>,
    onAllPermissionsGranted: () -> Unit,
    onPermissionDenied: () -> Unit
) {
    val multiplePermissionsState = rememberMultiplePermissionsState(permissions)

    when {
        multiplePermissionsState.allPermissionsGranted -> {
            // All permissions granted
            onAllPermissionsGranted()
        }

        multiplePermissionsState.shouldShowRationale -> {
            // Show rationale UI
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("Permissions are required for this feature.")
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { multiplePermissionsState.launchMultiplePermissionRequest() }) {
                    Text("Request Permissions")
                }
            }
        }

        else -> {
            // Initial request or denied permanently
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("Permissions are required to proceed.")
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { multiplePermissionsState.launchMultiplePermissionRequest() }) {
                    Text("Grant Permissions")
                }
            }
        }
    }
}
