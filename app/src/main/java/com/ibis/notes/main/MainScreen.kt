package com.ibis.notes.main

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.insets.ExperimentalAnimatedInsets
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.systemuicontroller.LocalSystemUiController
import com.google.accompanist.systemuicontroller.rememberAndroidSystemUiController
import com.ibis.notes.ROUTE_NOTES_LIST
import com.ibis.notes.ROUTE_NOTE_DETAIL
import com.ibis.notes.Screen
import com.ibis.notes.note.NoteList
import com.ibis.notes.note_detail.NoteDetail

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@ExperimentalAnimatedInsets
@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val systemUiController = rememberAndroidSystemUiController()
    CompositionLocalProvider(
        LocalSystemUiController provides systemUiController
    ) {
        LocalSystemUiController.current.setSystemBarsColor(MaterialTheme.colors.background)
        MainNavigation(modifier.statusBarsPadding())
    }
}

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@ExperimentalAnimatedInsets
@Composable
fun MainNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    Surface(modifier = modifier, color = Color.Transparent) {
        NavHost(navController, startDestination = Screen.NotesList.route) {
            composable(ROUTE_NOTES_LIST) {
                NoteList(navController)
            }
            composable(ROUTE_NOTE_DETAIL) {
                NoteDetail()
            }
        }
    }
}
