package com.murary.di.builders

import com.murary.features.artists.ArtistsActivity
import com.murary.di.annotations.ActivityScope
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class ActivityBuilder {

    @ActivityScope
    @ContributesAndroidInjector
    abstract fun bindArtistsActivity(): ArtistsActivity
}
