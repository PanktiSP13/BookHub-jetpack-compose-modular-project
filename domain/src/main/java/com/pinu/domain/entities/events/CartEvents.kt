package com.pinu.domain.entities.events

import com.pinu.domain.entities.network_service.request.AddToCartRequest
import com.pinu.domain.entities.network_service.request.UpdateItemQuantityRequest

sealed interface CartEvents {
    data object NavigateBack : CartEvents
    data object NavigateToCartScreen : CartEvents
    data object ValueUpdateReloadBookDetail : CartEvents
    data object ValueUpdateItemMovedToCart : CartEvents
    data class AddToCart(val isFromBookDetail: Boolean, val cartRequest: AddToCartRequest) : CartEvents
    data class UpdateBookItemQuantity(val updateItemQuantityRequest: UpdateItemQuantityRequest) : CartEvents
    data class RemoveBookItemFromCart(val bookId: Int) : CartEvents
    data class NavigateToBookDetailScreen(val bookId: Int) : CartEvents
    data object ClearCart : CartEvents
    data object FetchCartHistory : CartEvents
    data object ContinueShoppingNavigateToDashBoard : CartEvents
}