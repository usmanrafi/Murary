package com.murary.di.modules

import android.app.Application
import android.content.Context
import com.murary.application.MuraryApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideApplicationContext(application: MuraryApplication): Context =
        application.applicationContext

    @Provides
    @Singleton
    fun provideApplication(application: MuraryApplication): Application {
        return application
    }
}