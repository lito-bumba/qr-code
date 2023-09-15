package com.bumba.qrcode

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.platform.LocalContext
import com.bumba.qrcode.di.AppModule
import com.bumba.qrcode.presentation.NavScreen

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val context = LocalContext.current
            NavScreen(appModule = AppModule(context))
        }
    }
}