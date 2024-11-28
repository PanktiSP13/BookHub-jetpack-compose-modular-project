package com.pinu.domain.entities.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pinu.domain.entities.events.FavouritesEvents
import com.pinu.domain.entities.states.FavouritesState
import com.pinu.domain.repositories.FavouriteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(private val favouriteRepo: FavouriteRepository):ViewModel() {

    private val _favouriteState = MutableStateFlow(FavouritesState())
    val favouriteState = _favouriteState.asStateFlow()

    fun onEvent(events: FavouritesEvents){
        when(events){
            is FavouritesEvents.FetchFavourites -> getFavourites()
            is FavouritesEvents.AddAsFavourite -> addToFavourites(events.bookID)
            is FavouritesEvents.RemoveFromFavourites -> removeFromFavourites(events.bookID)
            else-> {
                // do nothing
            }
        }
    }

    private fun getFavourites() {
        viewModelScope.launch {
            favouriteRepo.fetchFavourites().collect { data ->
                data.fold(onSuccess = {
                    _favouriteState.value = _favouriteState.value.copy(
                        favouriteList = it.data ?: emptyList())
                }, onFailure = {
                    setErrorMessage(it.message ?: "")
                })
            }
        }
    }

    private fun addToFavourites(bookId: Int) {
        viewModelScope.launch {
            favouriteRepo.addToFavourites(bookId).collect { data ->
                data.fold(onSuccess = {
                    _favouriteState.value =
                        _favouriteState.value.copy(favouriteList = it.data ?: emptyList(),
                             successMessage = it.message)
                }, onFailure = {
                    setErrorMessage(it.message ?: "")
                })
            }
        }
    }

    private fun removeFromFavourites(bookId: Int) {
        viewModelScope.launch {
            favouriteRepo.removeFromFavourites(bookId).collect { data ->
                data.fold(onSuccess = {
                    _favouriteState.value =
                        _favouriteState.value.copy(favouriteList = it.data ?: emptyList(),
                            successMessage = it.message)
                }, onFailure = {
                    setErrorMessage(it.message ?: "")
                })
            }
        }
    }

    private fun setErrorMessage(errorMessage: String) {
        _favouriteState.value = _favouriteState.value.copy(errorMessage = errorMessage)
    }

    private fun updateSuccessMessage(successMessage: String) {
        _favouriteState.value = _favouriteState.value.copy(errorMessage = successMessage)
    }
}