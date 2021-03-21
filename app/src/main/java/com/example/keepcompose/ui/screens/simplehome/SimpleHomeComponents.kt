package com.example.keepcompose.ui.screens.simplehome

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.keepcompose.model.KeepNote
import com.example.keepcompose.ui.screens.global.SimpleIconButton
import com.example.keepcompose.ui.screens.home.StaggeredVerticalGrid
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.SolidColor

enum class TopBarState {
    IDLE, SEARCH
}

@Composable
fun SimpleNoteGrid(
    modifier: Modifier = Modifier,
    list: List<KeepNote>,
    onNoteClicked: (Int) -> Unit,
    onPerformQuery: (String) -> Unit
) {
    var topBarState by remember { mutableStateOf(TopBarState.IDLE) }

    LazyColumn(content = {
        item {
            //crossFade between top bar and search bar
            Crossfade(
                targetState = topBarState,
                animationSpec = tween(durationMillis = 500)
            ) { state ->
                when (state) {
                    TopBarState.IDLE -> {
                        SimpleTopBar(
                            modifier = Modifier.padding(vertical = 16.dp, horizontal = 20.dp),
                            onSearchClicked = { topBarState = TopBarState.SEARCH }
                        )
                    }
                    TopBarState.SEARCH -> {
                        SimpleSearchBar(
                            modifier = Modifier.padding(vertical = 16.dp, horizontal = 20.dp),
                            onPerformQuery = { onPerformQuery(it) },
                            onCancelClicked = { topBarState = TopBarState.IDLE }
                        )
                    }
                }
            }
        }

        item {
            StaggeredVerticalGrid(
                maxColumnWidth = 280.dp,
                modifier = modifier
            ) {
                list.forEachIndexed { index, keepNote ->
                    SimpleNoteListItem(
                        note = keepNote,
                        onNoteClicked = { id -> onNoteClicked(id) }
                    )
                }
            }
        }
    })
}

@Composable
fun SimpleNoteListItem(
    note: KeepNote,
    onNoteClicked: (Int) -> Unit
) {
    val shape = RoundedCornerShape(12.dp)
    Surface(
        shape = shape,
        color = Color(note.color),
        elevation = 0.dp,
        modifier = Modifier
            .padding(bottom = 8.dp, end = 4.dp, start = 4.dp)
            .clip(shape)
            .clickable { onNoteClicked(note.id) }
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
        ) {
            Text(
                text = note.title,
                style = MaterialTheme.typography.h6.copy(fontSize = 22.sp),
                maxLines = 2,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = note.createdDateFormatted,
                style = MaterialTheme.typography.overline,
                modifier = Modifier.padding(bottom = 8.dp),
                color = Color.Gray
            )

            Text(
                text = note.content,
                style = MaterialTheme.typography.body2,
            )
        }
    }
}

@Composable
fun SimpleTopBar(
    modifier: Modifier = Modifier,
    onSearchClicked: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = "Notes",
            style = MaterialTheme.typography.h4,
            color = MaterialTheme.colors.onSurface,
            fontWeight = FontWeight.Medium
        )
        SimpleIconButton(imageVector = Icons.Rounded.Search) {
            onSearchClicked()
        }
    }
}

@Composable
fun SimpleSearchBar(
    modifier: Modifier = Modifier,
    onPerformQuery: (String) -> Unit,
    onCancelClicked: () -> Unit
) {
    val (text, setText) = remember { mutableStateOf("") }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.fillMaxWidth()
    ) {
        BasicTextField(
            value = text,
            onValueChange = {
                setText(it)
                onPerformQuery(it)
            },
            maxLines = 1,
            singleLine = true,
            textStyle = MaterialTheme.typography.h4.copy(color = MaterialTheme.colors.onSurface),
            cursorBrush = SolidColor(MaterialTheme.colors.onSurface),
            decorationBox = {
                if (text.isEmpty()) {
                    Text(
                        text = "Search..",
                        color = Color.Gray,
                        style = MaterialTheme.typography.h4
                    )
                }
                it()
            }
        )

        SimpleIconButton(imageVector = Icons.Rounded.Close) {
            onCancelClicked()
        }
    }
}

@Composable
fun SimpleFab(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    FloatingActionButton(
        modifier = modifier.padding(bottom = 24.dp),
        onClick = { onClick() },
        backgroundColor = MaterialTheme.colors.primary
    ) {
        Icon(
            imageVector = Icons.Rounded.Add,
            contentDescription = null,
            tint = MaterialTheme.colors.onSurface
        )
    }
}