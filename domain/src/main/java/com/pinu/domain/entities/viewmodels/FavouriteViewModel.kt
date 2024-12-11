package com.pinu.domain.entities.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pinu.domain.entities.ToastMessage
import com.pinu.domain.entities.ToastMessageType
import com.pinu.domain.entities.events.FavouritesEvents
import com.pinu.domain.entities.events.SharedEvents
import com.pinu.domain.entities.states.FavouritesState
import com.pinu.domain.repositories.FavouriteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(
    private val sharedViewModel: SharedViewModel,
    private val favouriteRepo: FavouriteRepository,
) : ViewModel() {

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
            _favouriteState.update { it.copy(isLoading = true) }

            favouriteRepo.fetchFavourites().collect { data ->
                data.fold(onSuccess = {
                    _favouriteState.value = _favouriteState.value.copy(
                        favouriteList = it.data ?: emptyList(), isLoading = false)
                }, onFailure = {
                    onFailure(it.message ?: "")
                })
            }
        }
    }

    private fun addToFavourites(bookId: Int) {
        viewModelScope.launch {
            favouriteRepo.addToFavourites(bookId).collect { data ->
                data.fold(onSuccess = { favourites ->

                    _favouriteState.update {
                        it.copy(favouriteList = favourites.data ?: emptyList())
                    }
                    showToastMessage(ToastMessageType.SUCCESS,favourites.message)
                }, onFailure = {
                    onFailure(it.message ?: "")
                })
            }
        }
    }

    private fun removeFromFavourites(bookId: Int) {
        viewModelScope.launch {
            favouriteRepo.removeFromFavourites(bookId).collect { data ->
                data.fold(onSuccess = { favourites ->
                    _favouriteState.update {
                        it.copy(favouriteList = favourites.data ?: emptyList())
                    }
                    showToastMessage(ToastMessageType.SUCCESS,favourites.message)

                }, onFailure = {
                    onFailure(it.message ?: "")
                })
            }
        }
    }

    private fun onFailure(errorMessage: String) {
        _favouriteState.update { it.copy(isLoading = false) }
        showToastMessage(ToastMessageType.ERROR,errorMessage)
    }

    private fun showToastMessage(type: ToastMessageType, msg: String) {
        sharedViewModel.onEvent(SharedEvents.ShowToastMessage(ToastMessage(type = type, message = msg)))
    }

}