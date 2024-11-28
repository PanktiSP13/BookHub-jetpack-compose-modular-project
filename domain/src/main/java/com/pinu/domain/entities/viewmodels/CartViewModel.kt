package com.pinu.domain.entities.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pinu.domain.entities.events.CartEvents
import com.pinu.domain.entities.network_service.request.AddToCartRequest
import com.pinu.domain.entities.network_service.request.UpdateItemQuantityRequest
import com.pinu.domain.entities.network_service.response.CartItemsResponse
import com.pinu.domain.entities.states.CartState
import com.pinu.domain.repositories.CartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(private val cartRepo: CartRepository) : ViewModel() {

    private val _cartState = MutableStateFlow(CartState())
    val cartState = _cartState.asStateFlow()


    fun onEvent(events: CartEvents){
        when(events){
            is CartEvents.FetchCartHistory -> getCartItems()
            is CartEvents.ClearCart -> clearCart()
            is CartEvents.AddToCart -> addToCart(events.cartRequest)
            is CartEvents.RemoveBookItemFromCart -> removeItemFromCart(events.bookId)
            is CartEvents.UpdateBookItemQuantity -> updateItemQuantity(events.updateItemQuantityRequest)
            else->{
                // do nothing
            }
        }

    }


    private fun getCartItems() {
        viewModelScope.launch {
            cartRepo.fetchCartHistory().collect { data ->
                data.fold(onSuccess = {
                    _cartState.value = _cartState.value.copy(cartItemResponse = it)
                }, onFailure = { setErrorMessage(it.message ?: "") })
            }
        }
    }

    private fun clearCart() {
        viewModelScope.launch {
            cartRepo.clearCart().collect { data ->
                data.fold(onSuccess = {
                    _cartState.value = _cartState.value.copy(cartItemResponse = CartItemsResponse())
                }, onFailure = { setErrorMessage(it.message ?: "") })
            }
        }
    }

    private fun addToCart(cartRequest: AddToCartRequest) {
        viewModelScope.launch {
            cartRepo.addToCart(cartRequest).collect { data ->
                data.fold(onSuccess = {
                    _cartState.value = _cartState.value.copy(cartItemResponse = it,
                        successMessage = it.message)
                }, onFailure = { setErrorMessage(it.message ?: "") })
            }
        }
    }

    private fun removeItemFromCart(bookID: Int) {
        viewModelScope.launch {
            cartRepo.removeItemFromCart(bookID).collect { data ->
                data.fold(onSuccess = {
                    _cartState.value = _cartState.value.copy(cartItemResponse = it, successMessage = it.message)
                }, onFailure = { setErrorMessage(it.message ?: "") })
            }
        }
    }

    private fun updateItemQuantity(updateItemQuantityRequest: UpdateItemQuantityRequest) {
        viewModelScope.launch {
            cartRepo.updateItemQuantity(updateItemQuantityRequest).collect { data ->
                data.fold(onSuccess = {
                    _cartState.value = _cartState.value.copy(cartItemResponse = it, successMessage = it.message)
                }, onFailure = { setErrorMessage(it.message ?: "") })
            }
        }
    }

    private fun setErrorMessage(errorMessage: String) {
        _cartState.value = _cartState.value.copy(errorMessage = errorMessage)
    }

    private fun updateSuccessMessage(successMessage: String) {
        _cartState.value = _cartState.value.copy(errorMessage = successMessage)
    }

}