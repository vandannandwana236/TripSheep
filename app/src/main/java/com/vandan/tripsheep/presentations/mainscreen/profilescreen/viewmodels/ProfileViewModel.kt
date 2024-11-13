package com.vandan.tripsheep.presentations.mainscreen.profilescreen.viewmodels

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.vandan.tripsheep.data.dto.TripPlanDto
import com.vandan.tripsheep.data.dto.toTripPlan
import com.vandan.tripsheep.data.local.User
import com.vandan.tripsheep.data.remote.TripService
import com.vandan.tripsheep.data.resource.DataResource
import com.vandan.tripsheep.data.resource.TripPlanState
import com.vandan.tripsheep.data.resource.UserState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    @Named("userPreference")
    private val userPreferences: SharedPreferences,
    @Named("userDatabase")
    private val userDatabase:CollectionReference,
    private val tripService: TripService
) :ViewModel() {

    init {
        viewModelScope.launch {
            Log.d("Profile_Check","Getting User")
            getUser()
        }
    }

    private val _user = MutableStateFlow(UserState())
    val user = _user.asStateFlow()




    private suspend fun _getUser(): Flow<DataResource<User>> = flow {
        Log.d("Profile_Check","TTesting User")
        val uid = auth.currentUser?.uid
        emit(DataResource.Loading())
        uid.let {
            if(it != null){
                Log.d("Profile_Check","User Fetched")
                val result =userDatabase.document(it).get().await().toObject(User::class.java)
                emit(DataResource.Success(result))
                Log.d("Profile_Check","User Emitted")
            }
        }
    }.flowOn(Dispatchers.IO)
        .catch{
            emit(DataResource.Error(it.message))
        }

    private suspend fun getUser(){
        _getUser().collect{
            Log.d("Profile_Check","Testing User")
            when(it){
                is DataResource.Error -> _user.value = UserState().copy(error = it.message)
                is DataResource.Loading -> _user.value = UserState().copy(isLoading = true)
                is DataResource.Success -> _user.value = UserState().copy(user = it.data)
            }
        }
    }

    fun logoutUser(){
        auth.signOut()
        Log.d("Logout_Check", "Success")
        userPreferences.edit().clear().apply()
    }

}