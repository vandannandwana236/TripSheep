package com.vandan.tripsheep.data.local

data class User(
    val name:String,
    val email:String,
    val password:String,
    val longitude:Double=0.0,
    val latitude:Double=0.0
){
    constructor() : this("", "", "",0.0,0.0)
}