package com.example.vkmessenger

import android.app.Application
import com.vk.sdk.VKSdk

/**
 * Created by Дом on 17.02.2018.
 */
class Application: Application() {
    override fun onCreate() {
        super.onCreate()
        VKSdk.initialize(this)
    }
}