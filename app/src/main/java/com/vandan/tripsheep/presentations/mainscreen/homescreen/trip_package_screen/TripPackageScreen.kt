package com.vandan.tripsheep.presentations.mainscreen.homescreen.trip_package_screen

import android.util.Log
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vandan.tripsheep.presentations.mainscreen.homescreen.viewmodels.HomeScreenViewModel

@Composable
fun TripPackageScreen(modifier: Modifier = Modifier,tripPackageId:Long) {
    Log.d("tripPackageId",tripPackageId.toString())
    val HomeScreenViewModel = hiltViewModel<HomeScreenViewModel>()
    val tripPackageState = HomeScreenViewModel.tripPackageState.collectAsStateWithLifecycle().value

    Scaffold {ip->
        val innerpadding = ip
        BoxWithConstraints(modifier = Modifier
            .fillMaxSize()
            .padding(innerpadding)){
            val maxHeight = maxHeight
            val maxWidth = maxWidth
            if(tripPackageState.tripPackages != null){

                Text(text = tripPackageState.tripPackages.find { it.tripPackageId == tripPackageId }?.packageName.toString())
                
            }

        }

    }

}