package com.ibis.notes.main

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.insets.ExperimentalAnimatedInsets
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ibis.note.detail.CreateNote
import com.ibis.note.detail.NoteDetailScreen
import com.ibis.notes.home.NoteListScreen
import com.kafka.ui_common.ROUTE_CREATE_NOTE
import com.kafka.ui_common.ROUTE_NOTES_LIST
import com.kafka.ui_common.ROUTE_NOTE_DETAIL
import com.kafka.ui_common.Screen

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@ExperimentalAnimatedInsets
@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    rememberSystemUiController().setSystemBarsColor(MaterialTheme.colors.background)
    MainNavigation(modifier.statusBarsPadding())
}

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@ExperimentalAnimatedInsets
@Composable
fun MainNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    Surface(modifier = modifier) {
        NavHost(navController, startDestination = Screen.NotesList.route) {
            composable(ROUTE_NOTES_LIST) {
                NoteListScreen(navController)
            }
            composable(ROUTE_CREATE_NOTE) {
                CreateNote(navController)
            }
            composable(ROUTE_NOTE_DETAIL) {
                NoteDetailScreen()
            }
        }
    }
}
