package com.vandan.tripsheep.presentations.mainscreen.homescreen.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vandan.tripsheep.data.dto.toTripPackage
import com.vandan.tripsheep.data.remote.TripService
import com.vandan.tripsheep.data.resource.DataResource
import com.vandan.tripsheep.data.resource.TripPackageState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val tripService: TripService
):ViewModel(){

    private val _tripPackageState = MutableStateFlow(TripPackageState())
    val tripPackageState = _tripPackageState.asStateFlow()

    init {
        viewModelScope.launch {
            getTripPackages()
        }
    }

    private suspend fun _getTripPackages(): Flow<DataResource<TripPackageState>> = flow {

        emit(DataResource.Loading())

        val result = tripService.getPackages()
        emit(DataResource.Success(TripPackageState().copy(tripPackages = result.map { it.toTripPackage() })))

    }.catch {
            emit(DataResource.Error(it.message.toString()))
        }

    private suspend fun getTripPackages(){
        _getTripPackages().onEach {
            when(it){
                is DataResource.Error -> {_tripPackageState.value = TripPackageState(error = it.message.toString())}
                is DataResource.Loading -> {_tripPackageState.value = TripPackageState(isLoading = true)}
                is DataResource.Success -> {
                    if(it.data != null){
                        _tripPackageState.value = TripPackageState(tripPackages = it.data.tripPackages)
                    }
                }
            }
        }.collect()
    }

}