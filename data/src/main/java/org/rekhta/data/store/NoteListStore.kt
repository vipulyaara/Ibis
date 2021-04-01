//package org.rekhta.data.store
//
//import com.dropbox.android.external.store4.Fetcher
//import com.dropbox.android.external.store4.SourceOfTruth
//import com.dropbox.android.external.store4.Store
//import com.dropbox.android.external.store4.StoreBuilder
//import dagger.Module
//import dagger.Provides
//import dagger.hilt.InstallIn
//import dagger.hilt.components.SingletonComponent
//import org.rekhta.data.daos.HomepageDao
//import org.rekhta.data.daos.NotesDao
//import org.rekhta.data.entities.Homepage
//import org.rekhta.data.entities.HomepageEntity
//import org.rekhta.data.entities.Note
//import org.threeten.bp.LocalDate
//import org.threeten.bp.format.DateTimeFormatter
//import javax.inject.Singleton
//
//typealias HomepageStore = Store<Unit, List<Note>>
//
//@InstallIn(SingletonComponent::class)
//@Module
//class HomepageStoreModule {
//
//    @Singleton
//    @Provides
//    @ExperimentalStdlibApi
//    fun provideHomepageStore(
//        notesDao: NotesDao
//    ): HomepageStore {
//        val lastFetchDate = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE)
//
//        return StoreBuilder.from(
//            fetcher = Fetcher.of { languageCode: Int ->
//                homepageDataSource.fetchHomepage(languageCode, lastFetchDate).dataOrThrow()
//            },
//            sourceOfTruth = SourceOfTruth.Companion.of(
//                reader = { homepageDao.observeHomepage(it) },
//                writer = { _, homepage: HomepageEntity -> homepageDao.insert(homepage) },
//                delete = { homepageDao.delete(it) },
//                deleteAll = homepageDao::deleteAll
//            )
//        ).build()
//    }
//}
