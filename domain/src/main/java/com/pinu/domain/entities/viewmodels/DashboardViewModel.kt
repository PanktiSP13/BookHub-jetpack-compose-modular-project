package com.pinu.domain.entities.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pinu.domain.entities.ToastMessage
import com.pinu.domain.entities.ToastMessageType
import com.pinu.domain.entities.events.ProfileEvents
import com.pinu.domain.entities.events.SharedEvents
import com.pinu.domain.entities.network_service.request.ProfileRequest
import com.pinu.domain.entities.states.ProfileState
import com.pinu.domain.repositories.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val sharedViewModel: SharedViewModel,
    private val profileRepo: ProfileRepository,
) :
    ViewModel() {

    private val _profileState = MutableStateFlow(ProfileState())
    val profileState = _profileState.asStateFlow()


    fun onEvent(events: ProfileEvents) {
        when (events) {
            is ProfileEvents.FetchProfileData -> fetchProfileData()
            is ProfileEvents.AddUpdateProfileData -> addUpdateProfileData(events.profileData)
            is ProfileEvents.UpdateProfilePic -> updateProfilePic(events.profilePic)
            else -> {
                // do nothing
            }
        }
    }

    private fun fetchProfileData() {
        viewModelScope.launch {
            profileRepo.fetchProfileData().collect { data ->
                data.fold(onSuccess = { response ->
                    _profileState.update { it.copy(userProfileData = response.data) }
                }, onFailure = { onFailure(it.message ?: "") })
            }
        }
    }

    private fun addUpdateProfileData(profileRequest: ProfileRequest) {
        viewModelScope.launch {

            _profileState.update { state -> state.copy(isLoading = true) }
            profileRepo.addUpdateProfileData(profileRequest).collect { data ->
                data.fold(onSuccess = { response ->
                    _profileState.update { state ->
                        state.copy(
                            isLoading = false,
                            userProfileData = response.data,
                            profileUpdateSuccess = true
                        )
                    }
                    showToastMessage(ToastMessageType.SUCCESS, response.message)

                }, onFailure = { onFailure(it.message ?: "") })
            }
        }
    }


    private fun updateProfilePic(profilePic: File) {

        _profileState.update { state -> state.copy(isUploadingProfilePic = true) }
        viewModelScope.launch {
            profileRepo.updateProfilePic(profilePic).collect { data ->
                data.fold(onSuccess = { response ->
                    _profileState.update { it.copy(userProfileData = response.data, isUploadingProfilePic = false) }
                    showToastMessage(ToastMessageType.SUCCESS, response.message)
                }, onFailure = {
                    _profileState.update { state -> state.copy(isUploadingProfilePic = false) }
                    onFailure(it.message ?: "")
                })
            }
        }
    }

    private fun onFailure(errorMessage: String) {
        _profileState.update { it.copy(isLoading = false) }
        showToastMessage(ToastMessageType.ERROR, errorMessage)
    }

    private fun showToastMessage(type: ToastMessageType, msg: String) {
        sharedViewModel.onEvent(
            SharedEvents.ShowToastMessage(
                ToastMessage(
                    type = type,
                    message = msg
                )
            )
        )
    }
}