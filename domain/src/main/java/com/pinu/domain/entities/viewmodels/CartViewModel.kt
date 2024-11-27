package com.pinu.domain.entities.viewmodels

import androidx.lifecycle.ViewModel
import com.pinu.domain.entities.events.CartEvents
import com.pinu.domain.entities.states.BooksState
import com.pinu.domain.entities.states.CartState
import com.pinu.domain.repositories.CartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(private val cartRepository: CartRepository) : ViewModel() {

    private val _cartState = MutableStateFlow(CartState())
    val cartState = _cartState.asStateFlow()


    fun onEvent(events: CartEvents){
        when(events){
            is CartEvents.FetchCartHistory -> {}
            else->{
                // do nothing
            }
        }

    }



}