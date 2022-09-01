package com.androiddevs.shoppinglisttestingyt.di

import android.content.Context
import androidx.room.Room
import com.androiddevs.shoppinglisttestingyt.data.local.ShoppingItemDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Named

@Module
@InstallIn(ApplicationComponent::class)
object TestAppModule {

    @Provides
    @Named("test_db") // if we didn't annotate with @Named, for test classes it will take the dependency from app module
    fun provideInMemoryDb(@ApplicationContext context: Context) =
        // inMemoryDatabase -> saves only in RAM not in permanent storage
        Room.inMemoryDatabaseBuilder(context, ShoppingItemDatabase::class.java)
            .allowMainThreadQueries()
            .build()
}
