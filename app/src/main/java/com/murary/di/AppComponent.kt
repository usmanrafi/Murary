package com.murary.di

import com.murary.application.MuraryApplication
import com.murary.di.builders.ActivityBuilder
import com.murary.di.modules.AppModule
import com.murary.di.modules.NetworkModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


@Singleton
@Component(
    modules = [AppModule::class,
        AndroidSupportInjectionModule::class,
        NetworkModule::class,
        ActivityBuilder::class]
)

interface AppComponent : AndroidInjector<MuraryApplication> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<MuraryApplication>() {
        abstract override fun build(): AppComponent
    }
}