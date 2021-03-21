package com.example.keepcompose.ui.screens.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.keepcompose.ui.screens.detail.DetailViewModel.*

@Composable
fun Detail(
    viewModel: DetailViewModel = viewModel(),
    onBackArrowPressed: () -> Unit
) {
    val viewState by viewModel.viewState.collectAsState()

    DetailScreen(
        state = viewState,
        editEnabled = viewModel.isEditingEnabled(),
        onBackArrowPressed = { onBackArrowPressed() },
        onSaveClicked = { viewModel.onEventTriggered(DetailEvents.SaveNote) },
        onTitleChanged = { title -> viewModel.onEventTriggered(DetailEvents.UpdateTitle(title)) },
        onContentChanged = { content -> viewModel.onEventTriggered(DetailEvents.UpdateContent(content)) },
        onDeleteNoteClicked = {
            viewModel.onEventTriggered(DetailEvents.DeleteNote)
            onBackArrowPressed()
        }
    )
}

@Composable
fun DetailScreen(
    //note: KeepNote?,
    state: DetailViewState,
    editEnabled: Boolean,
    onBackArrowPressed: () -> Unit,
    onSaveClicked: () -> Unit,
    onTitleChanged: (String) -> Unit,
    onContentChanged: (String) -> Unit,
    onDeleteNoteClicked: () -> Unit,
) {
    var isEditEnabled by remember { mutableStateOf(editEnabled) }

    Scaffold( //snackbar
        backgroundColor = MaterialTheme.colors.background,
        modifier = Modifier.fillMaxHeight()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {

            SimpleDetailTopBar(
                modifier = Modifier.padding(vertical = 16.dp, horizontal = 20.dp),
                buttonEnabled = isEditEnabled,
                onBackClicked = { onBackArrowPressed() },
                onSaveClicked = { onSaveClicked() },
                onChangeButtonState = { isEditEnabled = !isEditEnabled },
                onDeleteNoteClicked = { onDeleteNoteClicked() }
            )

            SimpleNoteDetails(
                modifier = Modifier.padding(horizontal = 20.dp),
                editEnabled = isEditEnabled,
                state = state,
                onTitleChanged = { onTitleChanged(it) },
                onContentChanged = { onContentChanged(it) }
            )
        }
    }
}