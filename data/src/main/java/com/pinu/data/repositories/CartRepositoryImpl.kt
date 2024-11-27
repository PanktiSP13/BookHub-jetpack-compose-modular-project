package com.pinu.data.repositories

import com.pinu.data.network.network_apis.CartAPIs
import com.pinu.domain.repositories.CartRepository
import javax.inject.Inject


class CartRepositoryImpl @Inject constructor(private val cartAPIs: CartAPIs) : CartRepository {

}