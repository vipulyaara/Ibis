package com.ibis.ui.auth.profile

import androidx.compose.foundation.Image
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

@Composable
fun ProfileMenu(menuItems: List<ProfileMenuItem>, onItemClicked: (ProfileMenuItem.Item) -> Unit) {
    menuItems.forEach {
        when (it) {
            is ProfileMenuItem.Item -> ProfileMenuItem(
                itemItem = it,
                onItemClicked = onItemClicked
            )
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
        Image(
            modifier = Modifier.size(28.dp),
            painter = itemItem.icon,
            contentDescription = null
        )
        Text(
            modifier = Modifier.padding(horizontal = 24.dp),
            text = itemItem.title,
            style = MaterialTheme.typography.subtitle2
        )
    }
}
