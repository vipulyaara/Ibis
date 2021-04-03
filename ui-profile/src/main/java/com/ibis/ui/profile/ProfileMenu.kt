package com.ibis.ui.profile

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.window.Popup
import com.ibis.ui.profile.ProfileMenuItem.Companion.itemIdUnknown
import compose.icons.FeatherIcons
import compose.icons.feathericons.Info
import compose.icons.feathericons.PlusSquare
import compose.icons.feathericons.Settings

@Composable
fun ProfileMenu() {
    Popup {
        Surface {
            LazyColumn {
                items(menuItems) {

                }
            }
        }
    }
}

private val menuItems = listOf(
    ProfileMenuItem.Item(itemIdUnknown, "Enable auto adding titles", FeatherIcons.PlusSquare, false),
    ProfileMenuItem.Item(itemIdUnknown, "Settings",  FeatherIcons.Settings, false),
    ProfileMenuItem.Item(itemIdUnknown, "About us",  FeatherIcons.Info, false),
)

sealed class ProfileMenuItem {
    data class Item(
        val itemId: String,
        val title: String,
        val imageVector: ImageVector,
        val isEnabled: Boolean
    ) : ProfileMenuItem()

    data class SectionTitle(val title: String) : ProfileMenuItem()
    object Divider : ProfileMenuItem()

    companion object {
        val itemIdPoets = "poets"
        val itemIdSendFeedback = "sendFeedback"
        val itemIdAboutUs = "sendFeedback"
        val itemIdLogout = "logout"
        val itemIdImageShayri = "imageShayri"
        val itemIdUnknown = "unknown"
    }
}
