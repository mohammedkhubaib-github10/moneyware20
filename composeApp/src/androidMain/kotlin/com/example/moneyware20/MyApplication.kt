package com.example.moneyware20

import android.app.Application
import com.example.di.androidAuthModule
import com.example.di.androidDataStoreModule
import com.example.di.androidViewModelModule
import com.example.di.initKoin
import com.example.di.notificationModule
import com.example.di.smsModule
import org.koin.android.ext.koin.androidContext

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Call the helper, passing Android-specific context
        initKoin {
            androidContext(this@MyApplication)
            modules(
                androidAuthModule, notificationModule, smsModule, androidDataStoreModule,
                androidViewModelModule
            )
            // this@MyApplication  inside lambda is equivalent to MyApplication.this in java
        }
    }
}