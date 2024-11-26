package com.pinu.domain.entities.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pinu.domain.entities.events.ProfileEvents
import com.pinu.domain.entities.network_service.request.ProfileRequest
import com.pinu.domain.entities.states.ProfileState
import com.pinu.domain.repositories.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
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
            else -> {
                // do nothing
            }
        }
    }

    private fun fetchProfileData() {
        viewModelScope.launch {
            profileRepo.fetchProfileData().collect { data ->
                data.fold(onSuccess = {
                    _profileState.value = _profileState.value.copy(userProfileData = it)
                }, onFailure = { setErrorMessage(it.message ?: "") })
            }
        }
    }

    private fun addUpdateProfileData(profileRequest: ProfileRequest) {
        viewModelScope.launch {
            profileRepo.addUpdateProfileData(profileRequest).collect { data ->
                data.fold(onSuccess = {
                    _profileState.value =
                        _profileState.value.copy(userProfileData = it, profileUpdateSuccess = true)
                }, onFailure = { setErrorMessage(it.message ?: "") })
            }
        }
    }


    private fun updateProfilePic(profilePic: String) {
        viewModelScope.launch {
            profileRepo.updateProfilePic(profilePic).collect { data ->
                data.fold(onSuccess = {
                    _profileState.value = _profileState.value.copy(userProfileData = it)
                }, onFailure = { setErrorMessage(it.message ?: "") })
            }
        }
    }

    private fun setErrorMessage(errorMessage: String) {
        _profileState.value = _profileState.value.copy(errorMessage = errorMessage)
    }
}