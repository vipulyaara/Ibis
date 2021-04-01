@file:Suppress("unused")

package org.rekhta.data

import timber.log.*

/** Invokes an action if any trees are planted */
inline fun ifPlanted(action: () -> Unit) {
    if (Timber.trees.isNotEmpty()) {
        action()
    }
}

/** Delegates the provided message to [Timber.error] if any trees are planted. */
inline fun errorLog(throwable: Throwable? = null, message: () -> String = { "" }) =
    ifPlanted {
        Timber.error(throwable, message)
    }

/** Delegates the provided message to [Timber.warn] if any trees are planted. */
inline fun w(throwable: Throwable? = null, message: () -> String) =
    ifPlanted {
        Timber.warn(throwable, message)
    }

/** Delegates the provided message to [Timber.info] if any trees are planted. */
inline fun i(throwable: Throwable? = null, message: () -> String) =
    ifPlanted {
        Timber.info(throwable, message)
    }

/** Delegates the provided message to [Timber.debug] if any trees are planted. */
inline fun debug(throwable: Throwable? = null, message: () -> String) =
    ifPlanted {
        Timber.debug(throwable, message)
    }

/** Delegates the provided message to [Timber.verbose] if any trees are planted. */
inline fun v(throwable: Throwable? = null, message: () -> String) =
    ifPlanted {
        Timber.verbose(throwable, message)
    }

/** Delegates the provided message to [Timber.assert] if any trees are planted. */
inline fun wtf(throwable: Throwable? = null, message: () -> String) =
    ifPlanted {
        Timber.assert(throwable, message)
    }

/** Delegates the provided message to [Timber.log] if any trees are planted. */
inline fun log(priority: Int, t: Throwable, message: () -> String) =
    ifPlanted {
        Timber.log(priority, t, message)
    }

fun throwIfDebug(throwable: Throwable?) {
    ifPlanted {
        errorLog(throwable)
        throwable?.let { throw throwable }
    }
}
