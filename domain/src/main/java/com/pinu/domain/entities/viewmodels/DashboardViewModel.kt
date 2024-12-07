package com.pinu.domain.entities.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pinu.domain.entities.ToastMessage
import com.pinu.domain.entities.ToastMessageType
import com.pinu.domain.entities.events.ProfileEvents
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
class DashboardViewModel @Inject constructor(private val profileRepo: ProfileRepository) :
    ViewModel() {

    private val _profileState = MutableStateFlow(ProfileState())
    val profileState = _profileState.asStateFlow()


    fun onEvent(events: ProfileEvents) {
        when (events) {
            is ProfileEvents.FetchProfileData -> fetchProfileData()
            is ProfileEvents.AddUpdateProfileData -> addUpdateProfileData(events.profileData)
            is ProfileEvents.UpdateProfilePic -> updateProfilePic(events.profilePic)
            is ProfileEvents.ClearToastMessage -> {
                _profileState.update {
                    it.copy(
                        toastMessage = ToastMessage(
                            ToastMessageType.NONE,
                            ""
                        )
                    )
                }
            }
            else -> {
                // do nothing
            }
        }
    }

    private fun fetchProfileData() {
        viewModelScope.launch {
            profileRepo.fetchProfileData().collect { data ->
                data.fold(onSuccess = {
                    _profileState.value = _profileState.value.copy(userProfileData = it.data)
                }, onFailure = { onFailure(it.message ?: "") })
            }
        }
    }

    private fun addUpdateProfileData(profileRequest: ProfileRequest) {
        viewModelScope.launch {

            _profileState.update { state -> state.copy(isLoading = true) }
            profileRepo.addUpdateProfileData(profileRequest).collect { data ->
                data.fold(onSuccess = {
                    _profileState.value = _profileState.value.copy(
                        userProfileData = it.data,
                        profileUpdateSuccess = true,
                        toastMessage = ToastMessage(type = ToastMessageType.SUCCESS, it.message)
                    )
                    _profileState.update { state -> state.copy(isLoading = false) }

                }, onFailure = { onFailure(it.message ?: "") })
            }
        }
    }


    private fun updateProfilePic(profilePic: File) {
        viewModelScope.launch {
            profileRepo.updateProfilePic(profilePic).collect { data ->
                data.fold(onSuccess = {
                    _profileState.value = _profileState.value.copy(
                        userProfileData = it.data,
                        toastMessage = ToastMessage(type = ToastMessageType.SUCCESS, it.message)
                    )
                }, onFailure = {
                    onFailure(it.message ?: "")
                    Log.e("@@@@@", "updateProfilePic: ${it.message}" )
                })
            }
        }
    }

    private fun onFailure(errorMessage: String) {
        _profileState.value = _profileState.value.copy(
            toastMessage = ToastMessage(
                type = ToastMessageType.ERROR,
                errorMessage
            ), isLoading = false
        )
    }
}