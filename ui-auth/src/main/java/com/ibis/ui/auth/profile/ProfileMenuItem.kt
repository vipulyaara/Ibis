package com.ibis.ui.auth.profile

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.ibis.ui.auth.R
import com.ibis.ui.auth.profile.ProfileMenuItem.Companion.itemIdLogout

val ProfileItems
    @Composable get() = listOf(
        ProfileMenuItem.Item(
            itemIdLogout,
            "Logout",
            painterResource(id = R.drawable.ic_ibis),
            false
        ),
    )

sealed class ProfileMenuItem {
    data class Item(
        val itemId: String,
        val title: String,
        val icon: Painter,
        val isEnabled: Boolean
    ) : ProfileMenuItem()

    data class SectionTitle(val title: String) : ProfileMenuItem()
    object Divider : ProfileMenuItem()

    companion object {
        const val itemIdLogout = "logout"
    }
}
