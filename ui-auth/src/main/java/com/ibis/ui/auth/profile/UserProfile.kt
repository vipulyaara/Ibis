@file:Suppress("SameParameterValue")

package com.ibis.ui.auth.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.accompanist.coil.rememberCoilPainter
import com.google.firebase.auth.FirebaseAuth
import com.ibis.ui.auth.R
import com.kafka.ui_common.Screen
import com.kafka.ui_common.navigateTo
import org.rekhta.data.debug

@Composable
fun UserProfile(navController: NavHostController) {
    Scaffold(topBar = {
        TopAppBar(backgroundColor = MaterialTheme.colors.background) {}
    }) {
        LazyColumn {
            item { UserProfileHeader("Vipul Kumar", null) }
            item { Spacer(modifier = Modifier.height(24.dp)) }
            item {
                ProfileMenu(ProfileItems) {
                    FirebaseAuth.getInstance().signOut()
                    navController.navigateTo(Screen.NotesList)
                }
            }
        }
    }
}

@Composable
private fun UserProfileHeader(displayName: String, avatarUrl: String?) {
    debug { "avatar url is $avatarUrl" }
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Row {
                Card(
                    modifier = Modifier
                        .size(108.dp)
                        .align(Alignment.CenterVertically),
                    backgroundColor = MaterialTheme.colors.background,
                    shape = CircleShape,
                    elevation = 12.dp
                ) {
                    Image(
                        modifier = Modifier.fillMaxSize(),
                        painter = rememberCoilPainter(request = avatarUrl?.takeIf { it.isNotEmpty() }
                            ?: R.drawable.img_profile),
                        contentScale = ContentScale.Crop,
                        contentDescription = null
                    )
                }

                Column(
                    modifier = Modifier
                        .padding(start = 24.dp)
                        .align(Alignment.CenterVertically)
                ) {
                    Text(
                        text = displayName.takeIf { it.isNotEmpty() } ?: "Vipul Kumar",
                        style = MaterialTheme.typography.h6
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "23 Favorites",
                        style = MaterialTheme.typography.subtitle2,
                        maxLines = 1
                    )
                }
            }
        }
    }
}
