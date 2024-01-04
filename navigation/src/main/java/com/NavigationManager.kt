package com

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow

class NavigationManager {

    var popUp = MutableStateFlow(false)

    var commands = MutableSharedFlow<NavigationCommand>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )


    fun navigate(directions: NavigationCommand) {
        commands.tryEmit(directions)
    }


    fun navigateBack() {
        popUp.value = true
    }
}
