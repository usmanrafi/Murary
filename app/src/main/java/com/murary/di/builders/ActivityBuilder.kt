package com.murary.di.builders

import com.murary.di.annotations.ActivityScope
import com.murary.features.albums.view.AlbumsActivity
import com.murary.features.artists.view.ArtistsActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class ActivityBuilder {

    @ActivityScope
    @ContributesAndroidInjector
    abstract fun bindArtistsActivity(): ArtistsActivity

    @ActivityScope
    @ContributesAndroidInjector
    abstract fun bindAlbumsActivity(): AlbumsActivity
}
