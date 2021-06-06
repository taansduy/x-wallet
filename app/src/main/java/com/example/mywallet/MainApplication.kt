package com.example.mywallet

import android.app.Application
import com.example.mywallet.di.appModule
import com.example.mywallet.di.localModule
import com.example.mywallet.di.remoteModule
import com.example.mywallet.di.repositoryModule
import com.example.mywallet.util.PrefsUtil
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.module.Module

class MainApplication : Application(){

    companion object {
        lateinit var instance: MainApplication
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        startKoin {
            androidLogger()
            androidContext(this@MainApplication)
            modules(getModule())
        }
        PrefsUtil.init(instance)
    }

    private fun getModule(): List<Module> {
        val moduleList = arrayListOf<Module>()
        moduleList.addAll(listOf(remoteModule, repositoryModule,appModule, localModule))
        return moduleList
    }
}