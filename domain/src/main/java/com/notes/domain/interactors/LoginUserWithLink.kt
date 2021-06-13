package com.notes.domain.interactors

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.actionCodeSettings
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import org.ibis.base.AppCoroutineDispatchers
import org.ibis.base.Interactor
import org.rekhta.data.DeepLinks
import org.rekhta.data.daos.UserDao
import org.rekhta.data.entities.User
import javax.inject.Inject

class LoginUserWithLink @Inject constructor(
    private val userDao: UserDao,
    private val appCoroutineDispatchers: AppCoroutineDispatchers
) : Interactor<LoginUserWithLink.Params>() {

    override suspend fun doWork(params: Params) {
        withContext(appCoroutineDispatchers.io) {
            val auth = FirebaseAuth.getInstance()
            val actionCodeSettings = actionCodeSettings {
                url = DeepLinks.domain
                handleCodeInApp = true
                setAndroidPackageName(
                    "com.ibis.notes",
                    true,
                    "1")
            }

           auth.sendSignInLinkToEmail(params.email, actionCodeSettings)
                .addOnCompleteListener { }.await()

            auth.currentUser?.run { User(uid, uid, displayName, "") }?.let {
                userDao.insert(it)
            }
        }
    }

    data class Params(val email: String)
}
