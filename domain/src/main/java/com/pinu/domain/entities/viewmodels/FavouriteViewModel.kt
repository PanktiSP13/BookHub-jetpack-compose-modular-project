package com.pinu.domain.entities.viewmodels

import androidx.lifecycle.ViewModel
import com.pinu.domain.entities.events.FavouritesEvents
import com.pinu.domain.entities.states.CartState
import com.pinu.domain.entities.states.FavouritesState
import com.pinu.domain.repositories.FavouriteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(private val favouriteRepo: FavouriteRepository):ViewModel() {

    private val _favouriteState = MutableStateFlow(FavouritesState())
    val favouriteState = _favouriteState.asStateFlow()

    fun onEvent(events: FavouritesEvents){
        when(events){
            is FavouritesEvents.AddAsFavourite-> {}
            is FavouritesEvents.RemoveFromFavourites-> {}
            else-> {
                // do nothing
            }
        }
    }
}