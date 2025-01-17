package com.vandan.tripsheep.data.resource

sealed class DataResource<T>(val data:T? = null, val message:String? = null) {
    class Loading<T>(data: T? = null):DataResource<T>(data)
    class Success<T>(data: T?):DataResource<T>(data)
    class Error<T>(message: String?,data: T? = null):DataResource<T>(data,message)
}