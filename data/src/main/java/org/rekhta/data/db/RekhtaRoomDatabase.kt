package org.rekhta.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import org.rekhta.data.daos.UserDao
import org.rekhta.data.daos.NotesDao
import org.rekhta.data.entities.Note
import org.rekhta.data.entities.User

@Database(
    entities = [Note::class, User::class],
    version = 1
)
//@TypeConverters(
//    RekhtaTypeConverters::class
//)
abstract class RekhtaRoomDatabase : RoomDatabase() {
    abstract fun notesDao(): NotesDao
    abstract fun userDao(): UserDao
}
