package com.pinu.jetpackcomposemodularprojectdemo.ui.screens


import android.os.Build
import android.util.Patterns
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.pinu.jetpackcomposemodularprojectdemo.R
import com.pinu.jetpackcomposemodularprojectdemo.ui.components.CommonAppBar
import com.pinu.jetpackcomposemodularprojectdemo.ui.dialog.UploadProfilePicDialog
import com.pinu.jetpackcomposemodularprojectdemo.ui.theme.BackgroundColor
import com.pinu.jetpackcomposemodularprojectdemo.ui.theme.BookHubTypography
import com.pinu.jetpackcomposemodularprojectdemo.ui.theme.OnPrimaryColor
import com.pinu.jetpackcomposemodularprojectdemo.ui.theme.PrimaryColor
import com.pinu.jetpackcomposemodularprojectdemo.ui.theme.PrimaryVariant
import com.pinu.jetpackcomposemodularprojectdemo.ui.theme.SurfaceColor
import com.pinu.jetpackcomposemodularprojectdemo.ui.util.BookHubImage
import com.pinu.jetpackcomposemodularprojectdemo.ui.util.CommonFormTextField
import com.pinu.jetpackcomposemodularprojectdemo.ui.util.checkIfAllPermissionGranted
import kotlinx.coroutines.launch

@Preview
@Composable
fun ProfileRootUI(navController: NavHostController = rememberNavController()) {

    val scrollState = rememberScrollState()
    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val showImagePickerDialog = remember { mutableStateOf(false) }

    /* ------form values-----------*/
    val userName = remember { mutableStateOf("") }
    val userMobileNumber = remember { mutableStateOf("") }
    val userEmail = remember { mutableStateOf("") }
    val userGender = remember { mutableStateOf("") }
    val selectedGender = remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    val context = LocalContext.current
    val selectedImage = remember {
        mutableStateOf<Any?>(null)
    }

    val imagePickerPermission =
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.S_V2) arrayOf(android.Manifest.permission.CAMERA) else arrayOf(
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        )

    // Launcher for permission request
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { _ ->
        showImagePickerDialog.value = true
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        selectedImage.value = bitmap
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract =
        ActivityResultContracts.GetContent()
    ) { uri ->
        selectedImage.value = uri
    }

    fun isValidData(
        error: (String) -> Unit = {},
        onValidData: () -> Unit = {}
    ) {
        if (userName.value.isEmpty()) {
            error(context.getString(R.string.validation_empty_name))
        } else if (userEmail.value.isEmpty()) {
            error(context.getString(R.string.validation_empty_email))
        } else if (!Patterns.EMAIL_ADDRESS.matcher(userEmail.value).matches()) {
            error(context.getString(R.string.validation_email))
        } else if (userMobileNumber.value.isEmpty()) {
            error(context.getString(R.string.validation_mobile_number))
        } else if (userMobileNumber.value.length < 10) {
            error(context.getString(R.string.please_enter_valid_mobile_number))
        } else if (userGender.value.isEmpty()) {
            error(context.getString(R.string.validation_gender))
        } else if (selectedGender.value.isEmpty()) {
            error(context.getString(R.string.validation_gender_selection))
        } else {
            onValidData()
        }
    }

    Scaffold(topBar = {
            CommonAppBar(
                isProfileOptionAvailable = false,
                isFavouritesVisible = false,
                isCartVisible = false, canGoBack = true,
                title = stringResource(R.string.profile), navController = navController
            )
        }, bottomBar = {
            ElevatedButton(
                onClick = {
                    isValidData(error = { errorMsg ->
                        coroutineScope.launch {
                            snackBarHostState.showSnackbar(errorMsg)
                        }
                    }) {
                        focusManager.clearFocus()
                        navController.popBackStack()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                colors = ButtonDefaults.buttonColors(PrimaryColor),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = stringResource(R.string.add),
                    style = BookHubTypography.bodyMedium.copy(color = OnPrimaryColor)
                )
            }
        }, snackbarHost = {
            // latest way of material3 to show snackBar
            SnackbarHost(
                hostState = snackBarHostState,
                modifier = Modifier.padding(top = 16.dp),
            ) {

                //https://developer.android.com/reference/kotlin/androidx/compose/material/package-summary#snackbarhost
                Snackbar(
                    snackbarData = it,
                    containerColor = BackgroundColor,
                    contentColor = MaterialTheme.colorScheme.onBackground,
                )

            }
    }) { paddingValues ->

        Surface(
            color = SurfaceColor,
            modifier = Modifier.padding(paddingValues)
        ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            //profile pic
            Box {

                BookHubImage(
                    image = selectedImage.value ?: R.drawable.book,
                        contentDescription = stringResource(id = R.string.profile),
                        modifier = Modifier
                            .padding(top = 20.dp)
                            .size(150.dp)
                            .clip(CircleShape)
                            .border(1.dp, PrimaryColor, CircleShape),
                        contentScale = ContentScale.Crop)

                IconButton(
                    onClick = {
                        showImagePickerDialog.value = true
                    },
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .clip(CircleShape)
                        .background(PrimaryVariant)
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = stringResource(R.string.edit),
                        tint = OnPrimaryColor
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            CommonFormTextField(
                labelTxt = stringResource(R.string.name),
                txtValue = userName.value,
                isSingleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Text,
                ),
                keyboardActions = KeyboardActions(onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                }),
                onValueChange = {
                    userName.value = it
                },
            )
            CommonFormTextField(
                labelTxt = stringResource(R.string.email),
                txtValue = userEmail.value,
                isSingleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Email,
                ),
                keyboardActions = KeyboardActions(onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                }),
                onValueChange = {
                    userEmail.value = it
                },
            )

            CommonFormTextField(
                labelTxt = stringResource(R.string.mobile_number),
                txtValue = userMobileNumber.value,
                isSingleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Number,
                ),
                keyboardActions = KeyboardActions(onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                }),
                onValueChange = {
                    if (it.length <= 10) {
                        userMobileNumber.value = it
                    }
                },
            )
            CommonFormTextField(
                labelTxt = stringResource(R.string.gender),
                txtValue = userGender.value,
                isSingleLine = true,
                onValueChange = {
                    userGender.value = it
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Text,
                ),
                keyboardActions = KeyboardActions(onNext = {
                    focusManager.clearFocus()
                }),

                )
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                listOf(
                    stringResource(R.string.male),
                    stringResource(R.string.female),
                    stringResource(R.string.other)
                ).onEach { option ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start,
                    ) {
                        RadioButton(
                            selected = option == selectedGender.value,
                            onClick = {
                                selectedGender.value = option
                            },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = PrimaryColor,
                                unselectedColor = Color.DarkGray,
                            )
                        )
                        Text(text = option, style = BookHubTypography.titleSmall)
                    }

                }
            }

        }
        }
    }

    if (showImagePickerDialog.value) {

        if (checkIfAllPermissionGranted(context, imagePickerPermission)) {
            UploadProfilePicDialog(
                onDismiss = {
                    showImagePickerDialog.value = false
                },
                onCameraClicked = {
                    cameraLauncher.launch()
                    showImagePickerDialog.value = false
                },
                onGalleryClicked = {
                    galleryLauncher.launch("image/*")
                    showImagePickerDialog.value = false
                })
        } else {
            permissionLauncher.launch(imagePickerPermission)
        }
    }

}
