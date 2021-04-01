package org.rekhta.data.injection

import android.app.Application
import android.os.Debug
import androidx.room.Room
import androidx.room.RoomDatabase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.rekhta.data.db.RekhtaRoomDatabase
import javax.inject.Singleton

const val databaseName = "notes.db"

@InstallIn(SingletonComponent::class)
@Module
class RoomDatabaseModule {
    @Singleton
    @Provides
    fun provideDatabase(application: Application): RekhtaRoomDatabase {
        val builder = Room.databaseBuilder(application, RekhtaRoomDatabase::class.java, databaseName)
        if (Debug.isDebuggerConnected()) {
            builder.allowMainThreadQueries()
        }
        return builder.build()
    }
}

@InstallIn(SingletonComponent::class)
@Module
abstract class DatabaseModuleBinds {
    @Singleton
    @Binds
    abstract fun provideRoomDatabase(bind: RekhtaRoomDatabase): RoomDatabase
}

@InstallIn(SingletonComponent::class)
@Module
class DatabaseDaoModule {

    @Provides
    fun provideNotesDao(db: RekhtaRoomDatabase) = db.notesDao()
}
