package com.notes.domain.interactors

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import org.ibis.base.AppCoroutineDispatchers
import org.ibis.base.Interactor
import org.rekhta.data.daos.UserDao
import org.rekhta.data.entities.User
import javax.inject.Inject

class RegisterUser @Inject constructor(
    private val userDao: UserDao,
    private val appCoroutineDispatchers: AppCoroutineDispatchers
) : Interactor<RegisterUser.Params>() {

    override suspend fun doWork(params: Params) {
        withContext(appCoroutineDispatchers.io) {
            val auth = FirebaseAuth.getInstance()
            auth.createUserWithEmailAndPassword(params.email, params.password)
                .addOnCompleteListener {}.await()
            auth.currentUser?.run { User(uid, uid, displayName, "") }?.let {
                userDao.insert(it)
            }
        }
    }

    data class Params(val email: String, val password: String)
}