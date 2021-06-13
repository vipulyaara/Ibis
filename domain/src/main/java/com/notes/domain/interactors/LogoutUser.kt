package com.notes.domain.interactors

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.withContext
import org.ibis.base.AppCoroutineDispatchers
import org.ibis.base.Interactor
import org.rekhta.data.daos.UserDao
import javax.inject.Inject

class LogoutUser @Inject constructor(
    private val userDao: UserDao,
    private val appCoroutineDispatchers: AppCoroutineDispatchers
) : Interactor<LogoutUser.Params>() {

    override suspend fun doWork(params: Params) {
        withContext(appCoroutineDispatchers.io) {
            FirebaseAuth.getInstance().signOut()
            userDao.deleteAll()
        }
    }

    data class Params(val email: String, val password: String)
}
