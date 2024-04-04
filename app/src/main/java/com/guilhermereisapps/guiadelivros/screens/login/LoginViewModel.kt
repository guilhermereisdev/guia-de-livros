package com.guilhermereisapps.guiadelivros.screens.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    //    val loadingState = MutableStateFlow(LoadingState.IDLE)
    private val auth: FirebaseAuth = Firebase.auth
    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    fun signInWithEmailAndPassword(
        email: String,
        password: String,
        navigateToHomeScreen: () -> Unit,
    ) =
        viewModelScope.launch {
            try {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d(
                                "GuiaDeLivros",
                                "signInWithEmailAndPassword: Deu certo! ${task.result}"
                            )
                            navigateToHomeScreen()
                        } else {
                            Log.d("GuiaDeLivros", "signInWithEmailAndPassword: ${task.result}")
                        }
                    }
            } catch (ex: Exception) {
                Log.d("GuiaDeLivros", "signInWithEmailAndPassword: ${ex.message}")
            }
        }

    fun createUserWithEmailAndPassword(
        email: String,
        password: String,
        navigateToHomeScreen: () -> Unit,
    ) {
        if (_loading.value == false) {
            _loading.value = true
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val displayName = task.result.user?.email?.split("@")?.get(0)
                        createUser(displayName)
                        navigateToHomeScreen()
                    } else {
                        Log.d("GuiaDeLivros", "createUserWithEmailAndPassword: ${task.result}")
                    }
                }
            _loading.value = false
        }
    }

    private fun createUser(displayName: String?) {
        val userId = auth.currentUser?.uid.toString()
        val user = mutableMapOf<String, Any>()

        user["user_id"] = userId
        user["display_name"] = displayName.toString()

        FirebaseFirestore.getInstance()
            .collection("users")
//            .document(userId)
//            .set(user)
            .add(user)
    }
}
