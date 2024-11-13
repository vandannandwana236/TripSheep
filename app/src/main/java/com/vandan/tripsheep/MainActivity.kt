package com.vandan.tripsheep

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.vandan.tripsheep.navigations.navgraph.Navigation
import com.vandan.tripsheep.ui.theme.TripSheepTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TripSheepTheme {
                val navHostController = rememberNavController()
                val userPreferences = getSharedPreferences("USER_PREFERENCE", MODE_PRIVATE)
                val uid = userPreferences.getString("uid", null)
                uid.let {
                    Navigation(navHostController = navHostController, uid = uid)
                }
            }
        }
    }
}
