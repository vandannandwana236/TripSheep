package com.vandan.tripsheep.data.resource

import com.vandan.tripsheep.data.local.User

data class UserState(
    val isLoading: Boolean = false,
    val user: User? = null,
    val error: String? = null
)