package com.example.keepsilent

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class LocationApplication : Application()

//After that we create one package with module class  in which we define which dependency we want to inject