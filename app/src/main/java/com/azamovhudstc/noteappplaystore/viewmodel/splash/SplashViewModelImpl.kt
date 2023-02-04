package com.azamovhudstc.noteappplaystore.viewmodel.splash

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModelImpl @Inject constructor():SplashViewModel,ViewModel() {
    override var openHomeScreenLiveData: MutableLiveData<Unit> = MutableLiveData()

    override fun openHomeScreen() {
        viewModelScope.launch {
            delay(1500)
            openHomeScreenLiveData.value=Unit
        }
    }
}