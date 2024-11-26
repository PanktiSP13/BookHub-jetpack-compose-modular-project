package com.pinu.domain.entities.events

sealed class CartEvents {
    data object NavigateBack : CartEvents()
    data class UpdateBookItemQuantity(val qty: Int, val bookId: String) : CartEvents()
    data class RemoveBookItemFromCart(val bookId: String) : CartEvents()
    data object ProceedToCheckout : CartEvents()
    data class NavigateToBookDetailScreen(val bookId: String) : CartEvents()
    data object ClearCart : CartEvents()
    data object FetchCartHistory : CartEvents()
    data object ContinueShoppingNavigateToDashBoard : CartEvents()
}