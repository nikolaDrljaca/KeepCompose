package com.example.keepcompose.ui.screens.simplehome

import androidx.compose.foundation.layout.padding
import androidx.compose.material.FabPosition
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.keepcompose.model.KeepNote
import androidx.compose.runtime.getValue

@Composable
fun SimpleHome(
    viewModel: HomeViewModel = viewModel(),
    onAddNewSimpleNote: () -> Unit,
    onNoteClicked: (Int) -> Unit
) {
    val noteList by viewModel.viewState.collectAsState()
    LaunchedEffect(key1 = "key") {
        viewModel.onEventTriggered(HomeViewModel.HomeEvents.RefreshList)
    }
    SimpleHomeScreen(
        onAddNewSimpleNote = { onAddNewSimpleNote() },
        onNoteClicked = { noteId -> onNoteClicked(noteId) },
        keepNoteList = noteList.listOfNotes,
        onPerformQuery = { query -> viewModel.onEventTriggered(HomeViewModel.HomeEvents.QueryList(query)) }
    )
}

@Composable
fun SimpleHomeScreen(
    onAddNewSimpleNote: () -> Unit,
    onNoteClicked: (Int) -> Unit,
    keepNoteList: List<KeepNote>,
    onPerformQuery: (String) -> Unit
) {
    Scaffold(
        floatingActionButton = { SimpleFab(onClick = { onAddNewSimpleNote() }) },
        floatingActionButtonPosition = FabPosition.End,
    ) {
        SimpleNoteGrid(
            modifier = Modifier.padding(16.dp),
            list = keepNoteList,
            onNoteClicked = { noteId ->
                onNoteClicked(noteId)
            },
            onPerformQuery = { onPerformQuery(it) }
        )
    }
}