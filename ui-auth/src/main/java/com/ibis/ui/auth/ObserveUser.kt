package com.ibis.ui.auth

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import org.ibis.base.AppCoroutineDispatchers
import org.ibis.base.SubjectInteractor
import org.rekhta.data.daos.UserDao
import org.rekhta.data.entities.User
import javax.inject.Inject

class ObserveUser @Inject constructor(
    private val userDao: UserDao,
    private val dispatchers: AppCoroutineDispatchers
) : SubjectInteractor<Unit, User?>() {

    override suspend fun createObservable(params: Unit): Flow<User?> {
        return userDao.observeUser().flowOn(dispatchers.io)
    }
}
