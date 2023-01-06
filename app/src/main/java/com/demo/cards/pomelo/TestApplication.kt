package com.demo.cards.pomelo

import android.app.Application
import com.demo.cards.pomelo.data.entities.UserTokenBody
import com.demo.cards.pomelo.data.repositories.UserTokenRepository
import com.demo.cards.pomelo.di.MainModule
import com.pomelo.cards.Configuration
import com.pomelo.cards.PomeloCards
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin

class TestApplication : Application() {

    private val repository: UserTokenRepository by inject()

    override fun onCreate() {
        super.onCreate()
        startAppKoin()
        PomeloCards.register(
            configuration = Configuration {
                repository.getUserToken(UserTokenBody(BuildConfig.EMAIL))
            },
            context = this
        )
    }

    private fun startAppKoin() {
        val modules = listOf(MainModule.initModule())
        GlobalContext.getOrNull()?.apply {
            loadKoinModules(modules)
        } ?: startKoin {
            androidLogger()
            androidContext(this@TestApplication)
            modules(modules)
        }
    }
}
