package com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.screens

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.pinu.domain.entities.AppBarEvents
import com.pinu.domain.entities.AppBarUIConfig
import com.pinu.domain.entities.events.ProfileEvents
import com.pinu.domain.entities.events.SharedEvents
import com.pinu.domain.entities.network_service.request.ProfileRequest
import com.pinu.domain.entities.states.ProfileState
import com.pinu.domain.entities.states.SharedState
import com.pinu.domain.entities.viewmodels.DashboardViewModel
import com.pinu.domain.entities.viewmodels.SharedViewModel
import com.pinu.jetpackcomposemodularprojectdemo.R
import com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.components.BookHubAppBar
import com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.dialog.UploadProfilePicDialog
import com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.theme.BackgroundColor
import com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.theme.BookHubTypography
import com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.theme.OnPrimaryColor
import com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.theme.PrimaryColor
import com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.theme.PrimaryVariant
import com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.theme.SurfaceColor
import com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.util.BookHubImage
import com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.util.CommonFormTextField
import com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.util.FileUtils
import com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.util.Permissions.imagePickerPermission
import com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.util.checkIfAllPermissionGranted
import com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.util.showCustomToast
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File

@Composable
fun ProfileRootUI(
    navController: NavHostController = rememberNavController(),
    sharedViewModel: SharedViewModel,
) {

    val dashboardViewModel = hiltViewModel<DashboardViewModel>()

    ProfileScreen(
        profileState = dashboardViewModel.profileState.collectAsState().value,
        sharedState = sharedViewModel.sharedState.collectAsState().value,
        navController = navController,
        onSharedEvents = sharedViewModel::onEvent,
        onEvent = { event->
            when(event){
                is ProfileEvents.OnNavigateBack -> navController.popBackStack()
                else -> {
                    // do nothing
                }
            }
            dashboardViewModel.onEvent(event)
        }
    )
}

@Preview(showBackground = true)
@Composable
fun ProfileScreen(
    profileState: ProfileState = ProfileState(),
    sharedState: SharedState = SharedState(),
    navController: NavController = rememberNavController(),
    onSharedEvents: (SharedEvents) -> Unit = {},
    onEvent: (ProfileEvents) -> Unit = {},
) {

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
    val selectedImage = remember { mutableStateOf<Any?>(null) }

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
        bitmap?.let {
            onEvent(ProfileEvents.UpdateProfilePic(FileUtils.saveBitmapToFile(context, it)))
        }
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        selectedImage.value = uri
        uri?.let {
            onEvent(ProfileEvents.UpdateProfilePic(File(FileUtils.getRealPathFromURI(context,uri)?:"")))
        }
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

    LaunchedEffect(Unit) {
        onEvent(ProfileEvents.FetchProfileData)
    }

    LaunchedEffect(profileState.userProfileData != null) {
        userName.value = profileState.userProfileData?.name ?: ""
        userEmail.value = profileState.userProfileData?.email ?: ""
        userMobileNumber.value = profileState.userProfileData?.mobileNumber ?: ""
        userGender.value = profileState.userProfileData?.getGender(context) ?: ""
        selectedGender.value = profileState.userProfileData?.getGender(context) ?: ""
        selectedImage.value = profileState.userProfileData?.profilePicUrl ?: ""
    }


    LaunchedEffect(key1 = sharedState.toastMessage) {
        showCustomToast(context = context, toastMessage = sharedState.toastMessage)
        onSharedEvents(SharedEvents.ClearToastMessage)
    }


    Scaffold(topBar = {
        BookHubAppBar(
            appBarUIConfig = AppBarUIConfig(
                title = stringResource(R.string.profile),
                isProfileOptionAvailable = false,
                isFavouritesVisible = false,
                isCartVisible = false, canGoBack = true
            ),
            appBarEvents = AppBarEvents(onBackPressed = { navController.popBackStack() })
        )
    },
        bottomBar = {
            ProfileAddUpdateCTA(
                isLoading = profileState.isLoading,
                buttonText = if (profileState.userProfileData != null) stringResource(R.string.update) else stringResource(
                    R.string.add
                )
            ) {
                isValidData(error = { errorMsg ->
                    coroutineScope.launch {
                        snackBarHostState.showSnackbar(errorMsg)
                    }
                }) {

                    coroutineScope.launch {
                        delay(300) // debounce interval
                        onEvent(
                            ProfileEvents.AddUpdateProfileData(
                            ProfileRequest(
                                name = userName.value,
                                email = userEmail.value,
                                mobileNumber = userMobileNumber.value
                            )
                        )
                        )
                    }
                    focusManager.clearFocus()
                }
            }
        },
        snackbarHost = { ShowSnackBar(snackBarHostState) }) { paddingValues ->

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
                ProfilePic(
                    profileState.isUploadingProfilePic,
                    profileState.userProfileData?.profilePicUrl ?: selectedImage.value
                ) {

                    if (checkIfAllPermissionGranted(context, imagePickerPermission)) {
                        showImagePickerDialog.value = true
                    } else {
                        permissionLauncher.launch(imagePickerPermission)
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                ProfileForm(
                    focusManager = focusManager,
                    userName = userName.value,
                    userEmail = userEmail.value,
                    userMobileNumber = userMobileNumber.value,
                    gender = userGender.value,
                    selectedGender = selectedGender.value,
                    onNameChange = {
                    userName.value = it
                    },
                    onEmailChange = {
                    userEmail.value = it
                    },
                    onMobileNumberChange = {
                        userMobileNumber.value = it
                    },
                    onGenderChange = {
                        userGender.value = it
                        selectedGender.value = it
                    },
                    onGenderSelected = {
                        selectedGender.value = it
                        userGender.value = it
                    })
            }
        }
    }

    if (showImagePickerDialog.value) {
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
    }
}

@Composable
fun ProfileForm(
    focusManager: FocusManager,
    userName: String,
    userEmail: String,
    userMobileNumber: String,
    gender: String,
    selectedGender: String,
    onNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onMobileNumberChange: (String) -> Unit,
    onGenderChange: (String) -> Unit,
    onGenderSelected: (String) -> Unit
) {

    CommonFormTextField(
        labelTxt = stringResource(R.string.name),
        txtValue = userName,
        isSingleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Next,
            keyboardType = KeyboardType.Text,
        ),
        keyboardActions = KeyboardActions(onNext = {
            focusManager.moveFocus(FocusDirection.Down)
        }),
        onValueChange = {
            onNameChange(it)
        },
    )
    CommonFormTextField(
        labelTxt = stringResource(R.string.email),
        txtValue = userEmail,
        isSingleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Next,
            keyboardType = KeyboardType.Email,
        ),
        keyboardActions = KeyboardActions(onNext = {
            focusManager.moveFocus(FocusDirection.Down)
        }),
        onValueChange = { onEmailChange(it) },
    )

    CommonFormTextField(
        labelTxt = stringResource(R.string.mobile_number),
        txtValue = userMobileNumber,
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
                onMobileNumberChange(it)
            }
        },
    )
    CommonFormTextField(
        labelTxt = stringResource(R.string.gender),
        txtValue = gender,
        isSingleLine = true,
        onValueChange = {
            onGenderChange(it)
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
                    selected = option == selectedGender,
                    onClick = {
                        onGenderSelected(option)
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

@Composable
fun ProfilePic(
    isUploadingProfilePic: Boolean = false,
    selectedImage: Any?,
    showImagePickerDialog: () -> Unit,
) {
    Box {

        BookHubImage(
            image = selectedImage ?: R.drawable.user_placeholder,
            contentDescription = stringResource(id = R.string.profile),
            modifier = Modifier
                .padding(top = 20.dp)
                .size(150.dp)
                .clip(CircleShape)
                .border(1.dp, PrimaryColor, CircleShape),
            contentScale = ContentScale.Crop,
            placeholderImg = painterResource(R.drawable.user_placeholder)
        )

        IconButton(
            onClick = { showImagePickerDialog() },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .clip(CircleShape)
                .background(PrimaryVariant)
        ) {
            if (isUploadingProfilePic) {
                CircularProgressIndicator(
                    modifier = Modifier.padding(8.dp),
                    color = Color.White,
                    strokeWidth = 1.dp
                )
            } else {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = stringResource(R.string.edit),
                    tint = OnPrimaryColor
                )
            }
        }
    }
}


@Composable
fun ShowSnackBar(snackBarHostState: SnackbarHostState) {
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
}

@Composable
fun ProfileAddUpdateCTA(
    isLoading: Boolean = false,
    buttonText: String,
    onClickOnAddProfile: () -> Unit
) {
    ElevatedButton(
        onClick = { onClickOnAddProfile() },
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        colors = ButtonDefaults.buttonColors(PrimaryColor),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            text = buttonText,
            style = BookHubTypography.bodyMedium.copy(color = OnPrimaryColor)
        )
        Spacer(modifier = Modifier.width(12.dp))
        if (isLoading) CircularProgressIndicator(
            color = Color.White,
            modifier = Modifier.size(15.dp)
        )
    }
}
