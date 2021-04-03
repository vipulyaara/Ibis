package com.ibis.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kafka.ui_common.theme.textPrimary
import com.kafka.ui_common.widgets.IconResource

@Composable
fun ProfileMenu(menuItems: List<ProfileMenuItem>, onItemClicked: (ProfileMenuItem.Item) -> Unit) {
    menuItems.forEach {
        when (it) {
            is ProfileMenuItem.Item -> ProfileMenuItem(
                itemItem = it,
                onItemClicked = onItemClicked
            )
            is ProfileMenuItem.SectionTitle -> ProfileMenuItemSectionHeader(title = it.title)
            ProfileMenuItem.Divider -> Divider(
                modifier = Modifier
                    .padding(vertical = 8.dp, horizontal = 24.dp)
                    .background(MaterialTheme.colors.textPrimary)
                    .height(2.dp)
            )
        }
    }
}

@Composable
fun ProfileMenuItem(itemItem: ProfileMenuItem.Item, onItemClicked: (ProfileMenuItem.Item) -> Unit) {
    Row(
        modifier = Modifier
            .clickable { onItemClicked(itemItem) }
            .padding(horizontal = 24.dp, vertical = 16.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconResource(
            modifier = Modifier.size(28.dp),
            resourceId = itemItem.icon
        )
        Text(
            modifier = Modifier.padding(horizontal = 24.dp),
            text = itemItem.title,
            style = MaterialTheme.typography.subtitle1
        )
    }
}

@Composable
fun ProfileMenuItemSectionHeader(title: String) {
    Text(
        modifier = Modifier.padding(12.dp),
        text = title,
        style = MaterialTheme.typography.caption,
    )
}
