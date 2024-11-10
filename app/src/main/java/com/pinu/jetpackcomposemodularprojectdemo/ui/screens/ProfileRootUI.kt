package com.pinu.jetpackcomposemodularprojectdemo.ui.screens

import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.pinu.jetpackcomposemodularprojectdemo.R
import com.pinu.jetpackcomposemodularprojectdemo.ui.components.CommonAppBar
import com.pinu.jetpackcomposemodularprojectdemo.ui.theme.Pink
import com.pinu.jetpackcomposemodularprojectdemo.ui.theme.Pink80

@Preview
@Composable
fun ProfileRootUI(navController: NavHostController = rememberNavController()) {

    val context = LocalContext.current
    val openCamera = remember { mutableStateOf(false) }
    val imageBitmap = remember { mutableStateOf<Bitmap?>(null) }

    // Launcher for permission request
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { _ ->
        openCamera.value = true
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        imageBitmap.value = bitmap
        openCamera.value = false
    }

    LaunchedEffect(key1 = openCamera.value) {
        if (openCamera.value){
            cameraLauncher.launch()
        }
    }

    Scaffold(
        topBar = {
            CommonAppBar(isProfileOptionAvailable = false,
                isFavouritesVisible = false,
                isCartVisible = false, canGoBack = true,
                title = "Profile", navController = navController)
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = paddingValues
        ) {
            item {
                Box {
                    if (imageBitmap.value != null) {
                        imageBitmap.value?.asImageBitmap()?.let {
                            Image(
                                bitmap = it,
                                contentDescription = "Profile",
                                modifier = Modifier
                                    .padding(top = 70.dp)
                                    .size(150.dp)
                                    .clip(CircleShape)
                                    .border(1.dp, Pink80, CircleShape),
                                contentScale = ContentScale.Crop
                            )
                        }
                    } else {
                        Image(
                            painter = painterResource(id = R.drawable.book),
                            contentDescription = "Profile",
                            modifier = Modifier
                                .padding(top = 70.dp)
                                .size(150.dp)
                                .clip(CircleShape)
                                .border(1.dp, Pink80, CircleShape),
                            contentScale = ContentScale.Crop
                        )

                    }

                    IconButton(
                        onClick = {

                            if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.CAMERA)
                                == PackageManager.PERMISSION_GRANTED) {
                                openCamera.value = true
                            } else {
                                permissionLauncher.launch(android.Manifest.permission.CAMERA)
                            }
                        },
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .clip(CircleShape)
                            .background(Pink80)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "edit", tint = Pink
                        )
                    }
                }
            }
        }

    }


}
