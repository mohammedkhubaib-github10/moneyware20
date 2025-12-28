package com.example.moneyware20

import android.app.Application
import com.example.di.initKoin
import org.koin.android.ext.koin.androidContext

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Call the helper, passing Android-specific context
        initKoin {
            androidContext(this@MyApplication)  // this@MyApplication  inside lambda is equivalent to MyApplication.this in java
        }
    }
}