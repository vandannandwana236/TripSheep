package com.vandan.tripsheep.presentations.login_signup.viewmodel

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.vandan.tripsheep.data.resource.LoginState
import com.vandan.tripsheep.data.resource.SignUpState
import com.vandan.tripsheep.presentations.login_signup.sign_upscreen.SignUpScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class LoginViewModel @Inject constructor(
    @ApplicationContext context: Context,
    private val auth: FirebaseAuth,
    @Named("userDatabase")
    private val userDatabase: CollectionReference,
    @Named("userPreference")
    private val userPreferences: SharedPreferences
) : ViewModel() {
    private val _loginState = MutableStateFlow(LoginState())
    val loginState = _loginState.asStateFlow()

    private val _signUpState = MutableStateFlow(SignUpState())
    val signUpState = _signUpState.asStateFlow()

    fun loginUser(email: String, password: String) {
        _loginState.value = LoginState().copy(isLoading = true)
        Log.d("Login_Check", "Loading")
        auth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
            _loginState.value = LoginState().copy(isLoading = false, loginSuccess = true)
            auth.currentUser?.uid.let {
                userPreferences.edit().putString("uid", it).apply()
            }

            Log.d("Login_Check", "Success")
        }.addOnFailureListener {
            _loginState.value = LoginState().copy(isLoading = false, loginError = it.message)
            Log.d("Login_Check", "Failure")
        }

    }

    fun signUpUser(name: String, longitude: Double = 0.0, latitude: Double = 0.0, email: String,password: String) {
        _signUpState.value = SignUpState().copy(isLoading = true)
        Log.d("SignUp_Check", "Loading")
        auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {

            auth.currentUser?.uid.let {
                createUserProfile(
                    name = name,
                    email = email,
                    password = password,
                    longitude = longitude,
                    latitude = latitude,
                    uid = it
                )
            }
            Log.d("SignUp_Check", "Success")
        }.addOnFailureListener {
            _signUpState.value = SignUpState().copy(isLoading = false, loginError = it.message)
            Log.d("SignUp_Check", "Failure")
        }

    }

    private fun createUserProfile(name: String, email: String, password: String, longitude: Double, latitude: Double, uid: String?) {
        val user = hashMapOf(
            "name" to name,
            "email" to email,
            "password" to password,
            "longitude" to longitude,
            "latitude" to latitude
        )
        uid.let { uid ->
            if(uid != null) {
                userDatabase.document(uid).set(user).addOnSuccessListener {
                    _signUpState.value = SignUpState().copy(isLoading = false, signUpSuccess = true)
                    Log.d("SignUp_Register_Check", "Success")
                    userPreferences.edit().putString("uid", uid).apply()
                }.addOnFailureListener {
                    _signUpState.value = SignUpState().copy(isLoading = false, loginError = it.message)
                    Log.d("SignUp_Register_Check", it.message.toString())
                }
            }
        }

    }



}