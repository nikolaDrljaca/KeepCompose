package com.example.keepcompose.ui.screens.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.keepcompose.database.KeepNoteDatabase
import com.example.keepcompose.model.KeepNote
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailViewModel(
    app: Application,
    private val savedState: SavedStateHandle
): AndroidViewModel(app) {
    private val dao = KeepNoteDatabase.getDatabase(app, viewModelScope).keepNoteDao()
    private val noteIdFromArgs = savedState.get<Int>("noteId")

    private val _viewState = MutableStateFlow(DetailViewState())
    val viewState = _viewState.asStateFlow()

    private lateinit var currentNote: KeepNote

    init {
        noteIdFromArgs?.let { id ->
            if (id > 0) {
                getKeepNote(id)
                viewModelScope.launch {
                    currentNote = dao.getKeepNote(id)
                }
            }
        }
    }

    fun onEventTriggered(event: DetailEvents) {
        when(event) {
            is DetailEvents.UpdateTitle -> {
                _viewState.value = _viewState.value.copy(title = event.title)
            }
            is DetailEvents.UpdateContent -> {
                _viewState.value = _viewState.value.copy(content = event.content)
            }
            is DetailEvents.SaveNote -> {
                if (!viewState.value.isTitleEmpty) {
                    saveNote()
                }
            }
            is DetailEvents.DeleteNote -> {
                deleteNote()
            }
        }
    }

    fun isEditingEnabled(): Boolean = noteIdFromArgs!! <= 0

    private fun saveNote() = viewModelScope.launch {
        //if noteIdFromArgs is -1 then the note is new, else update
        if (noteIdFromArgs!! > 0) {
            dao.insert(dao.getKeepNote(noteIdFromArgs).copy(
                content = _viewState.value.content,
                title =  _viewState.value.title
            ))
        } else {
            _viewState.value.let {
                dao.insert(KeepNote(content = it.content, title = it.title))
            }
        }
    }

    private fun deleteNote() = viewModelScope.launch {
        if (noteIdFromArgs!! > 0) dao.delete(currentNote)
    }

    private fun getKeepNote(id: Int) = viewModelScope.launch {
        val note = dao.getKeepNote(id)
        _viewState.value = _viewState.value.copy(
            content = note.content,
            title = note.title,
            date = note.createdDateFormatted,
        )
    }

    sealed class DetailEvents {
        data class UpdateTitle(val title: String): DetailEvents()
        data class UpdateContent(val content: String): DetailEvents()
        object SaveNote: DetailEvents()
        object DeleteNote: DetailEvents()
    }

    data class DetailViewState(
        val content: String = "",
        val title: String = "",
        val date: String = "",
    ) {
        val isTitleEmpty = title.isBlank() //if true the note is new
    }
}