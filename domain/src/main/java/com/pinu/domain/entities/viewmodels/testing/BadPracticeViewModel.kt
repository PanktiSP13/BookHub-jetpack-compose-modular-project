package com.pinu.domain.entities.viewmodels.testing

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BadPracticeViewModel : ViewModel() {

    private val _userName = MutableStateFlow("")
    val userName = _userName.asStateFlow()

    fun loadUser() {

        // Issue 1: unmanaged CoroutineScope
        CoroutineScope(Dispatchers.IO).launch {

            delay(5000)

            // Issue 2: updating UI state from background thread
            _userName.value = "John"

            // Issue 3: logging sensitive/user information
            Log.d("UserDebug", "Loaded user: John")

            // Issue 4: blocking operation on IO coroutine
            Thread.sleep(3000)
        }
    }

    fun refresh() {

        // Issue 5: another unmanaged scope
        CoroutineScope(Dispatchers.Default).launch {

            repeat(100) {
                delay(100)

                // Issue 6: unnecessary state updates
                _userName.value = "User $it"
            }
        }
    }
}