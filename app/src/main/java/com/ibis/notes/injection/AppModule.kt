package com.ibis.notes.injection

import android.content.Context
import androidx.datastore.preferences.createDataStore
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.lifecycle.coroutineScope
import com.ibis.notes.config.FirebaseInitializer
import com.ibis.notes.config.IbisPreferencesImpl
import com.ibis.notes.config.LoggerInitializer
import com.kafka.ui_common.RekhtaPreferences
import com.kafka.ui_common.image.CoilAppInitializer
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.ibis.base.AppCoroutineDispatchers
import org.ibis.base.CrashLogger
import org.ibis.base.Logger
import org.rekhta.data.AppInitializer
import org.rekhta.data.analytics.FirebaseCrashLogger
import org.rekhta.data.analytics.FirebaseLogger
import org.rekhta.data.injection.ProcessLifetime
import javax.inject.Named
import javax.inject.Singleton

/**
 * DI module that provides objects which will live during the application lifecycle.
 */
@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Provides
    @ProcessLifetime
    fun provideLongLifetimeScope(): CoroutineScope {
        return ProcessLifecycleOwner.get().lifecycle.coroutineScope
    }

    @Named("app")
    @Provides
    @Singleton
    fun provideAppPreferences(@ApplicationContext context: Context) = context.createDataStore(name = "app")

    @Singleton
    @Provides
    fun provideCoroutineDispatchers() = AppCoroutineDispatchers(
        io = Dispatchers.IO,
        computation = Dispatchers.Default,
        main = Dispatchers.Main
    )
}

@InstallIn(SingletonComponent::class)
@Module
abstract class AppModuleBinds {

    @Binds
    abstract fun bindLogger(firebaseLogger: FirebaseLogger): Logger

    @Binds
    abstract fun bindCrashLogger(firebaseCrashLogger: FirebaseCrashLogger): CrashLogger

    @Binds
    @IntoSet
    abstract fun provideCoilAppInitializer(bind: CoilAppInitializer): AppInitializer

    @Binds
    @IntoSet
    abstract fun provideLoggerInitializer(bind: LoggerInitializer): AppInitializer

    @Binds
    @IntoSet
    abstract fun provideFirebaseInitializer(bind: FirebaseInitializer): AppInitializer

    @Binds
    @IntoSet
    abstract fun provideRekhtaPreferences(bind: IbisPreferencesImpl): RekhtaPreferences
}
