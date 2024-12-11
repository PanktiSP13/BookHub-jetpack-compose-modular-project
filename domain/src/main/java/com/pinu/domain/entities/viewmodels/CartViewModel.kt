package com.pinu.domain.entities.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pinu.domain.entities.ToastMessage
import com.pinu.domain.entities.ToastMessageType
import com.pinu.domain.entities.events.CartEvents
import com.pinu.domain.entities.events.SharedEvents
import com.pinu.domain.entities.network_service.request.AddToCartRequest
import com.pinu.domain.entities.network_service.request.UpdateItemQuantityRequest
import com.pinu.domain.entities.states.CartState
import com.pinu.domain.repositories.CartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val sharedViewModel: SharedViewModel,
    private val cartRepo: CartRepository,
) : ViewModel() {

    private val _cartState = MutableStateFlow(CartState())
    val cartState = _cartState.asStateFlow()


    fun onEvent(events: CartEvents){
        when(events){
            is CartEvents.FetchCartHistory -> getCartItems()
            is CartEvents.ClearCart -> clearCart()
            is CartEvents.AddToCart -> addToCart(events.cartRequest, events.isFromBookDetail)
            is CartEvents.RemoveBookItemFromCart -> removeItemFromCart(events.bookId)
            is CartEvents.UpdateBookItemQuantity -> updateItemQuantity(events.updateItemQuantityRequest)


            is CartEvents.ValueUpdateReloadBookDetail -> {
                _cartState.update { it.copy(reloadBookDetail = false) }
            }

            is CartEvents.ValueUpdateItemMovedToCart -> {
                _cartState.update { it.copy(itemMovedToCart = false) }
            }
            else->{
                // do nothing
            }
        }

    }


    private fun getCartItems() {
        viewModelScope.launch {
            cartRepo.fetchCartHistory().collect { data ->
                data.fold(onSuccess = {
                    _cartState.value = _cartState.value.copy(cartItemResponse = it, isLoading = false)
                }, onFailure = { onFailure(it.message ?: "") })
            }
        }
    }

    private fun clearCart() {

        viewModelScope.launch {
            _cartState.update { it.copy(isLoadingForPayment = true) }
            cartRepo.clearCart().collect { data ->
                data.fold(onSuccess = { response ->
                    _cartState.update {
                        it.copy(
                        cartItemResponse = response,
                            isLoadingForPayment = false
                        )
                    }

                }, onFailure = {
                    _cartState.update { it.copy(isLoadingForPayment = false) }
                    onFailure(it.message ?: "")
                })
            }
        }
    }

    private fun addToCart(cartRequest: AddToCartRequest, isFromBookDetail: Boolean) {
        viewModelScope.launch {

            _cartState.update { it.copy(isLoading = true) }

            cartRepo.addToCart(cartRequest).collect { data ->

                data.fold(onSuccess = { response ->
                    _cartState.update {
                        it.copy(
                            cartItemResponse = response, isLoading = false,
                            reloadBookDetail = isFromBookDetail, itemMovedToCart = true
                        )
                    }
                    showToastMessage(ToastMessageType.SUCCESS, response.message)
                }, onFailure = { onFailure(it.message ?: "") })
            }
        }
    }

    private fun removeItemFromCart(bookID: Int) {
        viewModelScope.launch {
            cartRepo.removeItemFromCart(bookID).collect { data ->
                data.fold(onSuccess = { response ->
                    _cartState.update { it.copy(cartItemResponse = response) }
                    showToastMessage(ToastMessageType.SUCCESS, response.message)
                }, onFailure = { onFailure(it.message ?: "") })
            }
        }
    }

    private fun updateItemQuantity(updateItemQuantityRequest: UpdateItemQuantityRequest) {
        viewModelScope.launch {
            cartRepo.updateItemQuantity(updateItemQuantityRequest).collect { data ->
                data.fold(onSuccess = { response ->
                    _cartState.update { it.copy(cartItemResponse = response, isLoading = false) }
                    showToastMessage(ToastMessageType.SUCCESS, response.message)
                }, onFailure = { onFailure(it.message ?: "") })
            }
        }
    }

    private fun onFailure(errorMessage: String) {
        _cartState.update { it.copy(isLoading = false) }
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