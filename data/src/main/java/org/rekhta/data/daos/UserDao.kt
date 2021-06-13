package org.rekhta.data.daos

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import org.rekhta.data.entities.User

@Dao
abstract class UserDao : EntityDao<User>() {
    @Query("SELECT * FROM user")
    abstract fun observeUser(): Flow<User>

    @Query("DELETE FROM user")
    abstract suspend fun delete()

    @Query("DELETE FROM user")
    abstract suspend fun deleteAll()
}
