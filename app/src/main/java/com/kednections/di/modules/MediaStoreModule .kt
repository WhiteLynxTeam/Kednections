package com.kednections.di.modules

import android.content.ContentResolver
import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class MediaStoreModule {
    @Provides
    fun provideContentResolver(context: Context): ContentResolver =
        context.contentResolver
}