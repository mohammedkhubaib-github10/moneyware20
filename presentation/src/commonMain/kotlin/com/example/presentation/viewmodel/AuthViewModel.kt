package com.example.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.authentication.authentication

class AuthViewModel : ViewModel() {

    suspend fun authenticate(): String? {
        return authentication()
    }
}