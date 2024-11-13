package com.vandan.tripsheep.navigations.navgraph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.vandan.tripsheep.navigations.screens.Screens
import com.vandan.tripsheep.presentations.login_signup.loginscreen.LoginScreen
import com.vandan.tripsheep.presentations.login_signup.sign_upscreen.SignUpScreen
import com.vandan.tripsheep.presentations.mainscreen.MainScreen
import com.vandan.tripsheep.presentations.mainscreen.homescreen.trip_package_screen.TripPackageScreen
import com.vandan.tripsheep.presentations.mainscreen.profilescreen.views.ProfileScreen
import com.vandan.tripsheep.presentations.splashscreen.views.GetStartedScreen
import com.vandan.tripsheep.presentations.mainscreen.searchscreen.views.SearchScreen
import com.vandan.tripsheep.presentations.splashscreen.views.SplashScreen

@Composable
fun Navigation(navHostController: NavHostController, uid: String?){

   NavHost(navController = navHostController, startDestination = if(uid==null) Screens.SplashScreen.route else Screens.HomeScreen.route) {

      composable(Screens.HomeScreen.route){
         MainScreen(uid = uid, navHostController = navHostController)
      }

      composable(Screens.SearchScreen.route){
         SearchScreen(navHostController = navHostController)
      }

      composable(Screens.ProfileScreen.route){
         ProfileScreen(navHostController=navHostController)
      }
       composable(Screens.SplashScreen.route) {
           SplashScreen(navHostController)
       }

       composable(Screens.LoginScreen.route) {
           LoginScreen(navHostController = navHostController)
       }

       composable(Screens.SignUpScreen.route) {
           SignUpScreen(navHostController=navHostController)
       }

       composable(Screens.GetStartedScreen.route) {
           GetStartedScreen(navHostController=navHostController)
       }

       composable(Screens.TripPackageScreen.route+"/{tripPackageId}") {
           val tripPackageId = it.arguments?.getString("tripPackageId")
           if(tripPackageId!=null) {
               TripPackageScreen(tripPackageId = tripPackageId.toLong())
           }
       }


   }

}