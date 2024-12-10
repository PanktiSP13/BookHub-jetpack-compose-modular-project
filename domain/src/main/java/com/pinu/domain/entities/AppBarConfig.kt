package com.pinu.domain.entities

import com.pinu.domain.entities.events.CartEvents
import com.pinu.domain.entities.events.FavouritesEvents
import com.pinu.domain.entities.events.ProfileEvents
import com.pinu.domain.entities.states.CartState
import com.pinu.domain.entities.states.FavouritesState



data class AppBarUIConfig(
    val title: String = "",
    val canGoBack: Boolean = false,
    val isCartVisible: Boolean = false,
    val isFavouritesVisible: Boolean = false,
    val isProfileOptionAvailable: Boolean = false,
)

data class AppBarState(
    val favouriteState: FavouritesState = FavouritesState(),
    val cartState: CartState = CartState(),
)

data class AppBarEvents(
    val favouriteEvents: (FavouritesEvents) -> Unit = {},
    val cartEvents: (CartEvents) -> Unit = {},
    val profileEvents: (ProfileEvents) -> Unit = {},
    val onBackPressed: () -> Unit = {},
)
