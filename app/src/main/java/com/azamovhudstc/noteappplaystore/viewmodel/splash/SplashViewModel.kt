package com.azamovhudstc.noteappplaystore.viewmodel.splash

import androidx.lifecycle.MutableLiveData

interface SplashViewModel {
    fun openHomeScreen()
    var openHomeScreenLiveData:MutableLiveData<Unit>
}