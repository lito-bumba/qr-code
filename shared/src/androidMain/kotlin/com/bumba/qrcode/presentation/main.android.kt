package com.bumba.qrcode.presentation

import androidx.compose.runtime.Composable
import com.bumba.qrcode.di.AppModule
import com.bumba.qrcode.presentation.navigation.NavScreen

@Composable
fun MainView(appModule: AppModule) = NavScreen(appModule)