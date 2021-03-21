package com.example.keepcompose.ui.screens.simplehome

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.keepcompose.database.KeepNoteDatabase
import com.example.keepcompose.model.KeepNote
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.*

class HomeViewModel(app: Application) : AndroidViewModel(app) {
    private val dao = KeepNoteDatabase.getDatabase(app, viewModelScope).keepNoteDao()

    private val _viewState = MutableStateFlow(HomeViewState())
    val viewState = _viewState.asStateFlow()

    fun onEventTriggered(event: HomeEvents) {
        when (event) {
            is HomeEvents.RefreshList -> {
                refreshList()
            }
            is HomeEvents.QueryList -> {
                queryList(event.query)
            }
        }
    }

    private fun queryList(query: String) = viewModelScope.launch {
        _viewState.value = _viewState.value.copy(
            listOfNotes = dao.getNoteList().filter {
                it.title.toLowerCase(Locale.ROOT).contains(query.toLowerCase(Locale.ROOT)) or
                    it.content.toLowerCase(Locale.ROOT).contains(query.toLowerCase(Locale.ROOT))
            }
        )
    }

    private fun refreshList() = viewModelScope.launch {
        _viewState.value = _viewState.value.copy(listOfNotes = dao.getNoteList())
    }

    sealed class HomeEvents {
        object RefreshList : HomeEvents()
        data class QueryList(val query: String) : HomeEvents()
    }

    data class HomeViewState(
        val listOfNotes: List<KeepNote> = emptyList(),
        val searchQuery: String = ""
    ) {
        val isListEmpty = listOfNotes.isEmpty()
    }
}