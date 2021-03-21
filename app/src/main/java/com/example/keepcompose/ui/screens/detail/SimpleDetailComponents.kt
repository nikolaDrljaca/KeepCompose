package com.example.keepcompose.ui.screens.detail

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.keepcompose.ui.screens.global.SimpleAlertDialog
import com.example.keepcompose.ui.screens.global.SimpleIconButton

private enum class ButtonState {
    IDLE, EDIT
}

@Composable
fun SimpleDetailTopBar(
    modifier: Modifier = Modifier,
    buttonEnabled: Boolean,
    onBackClicked: () -> Unit,
    onSaveClicked: () -> Unit,
    onChangeButtonState: () -> Unit,
    onDeleteNoteClicked: () -> Unit
) {
    var buttonState by if (buttonEnabled) {
        remember { mutableStateOf(ButtonState.EDIT) }
    } else {
        remember { mutableStateOf(ButtonState.IDLE) }
    }

    var openDialog by remember { mutableStateOf(false) }

    //gives access to clearFocus method to clear the keyboard
    val focusManager = LocalFocusManager.current

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        SimpleIconButton(imageVector = Icons.Rounded.ArrowBack) { onBackClicked() }

        Row(verticalAlignment = Alignment.CenterVertically) {
            SimpleIconButton(
                modifier = Modifier.padding(end = 8.dp),
                imageVector = Icons.Rounded.Delete
            ) {
                openDialog = true
            }

            SimpleAlertDialog(
                openDialog = openDialog,
                title = "Confirm",
                text = "This action is not reversible. Are you sure you wish to delete?",
                positiveButtonText = "Yes",
                negativeButtonText = "No",
                onClick = { onDeleteNoteClicked() },
                onDismiss = { openDialog = false }
            )

            Crossfade(
                targetState = buttonState,
                animationSpec = tween(durationMillis = 500)
            ) { state ->
                when (state) {
                    ButtonState.IDLE -> {
                        SimpleIconButton(imageVector = Icons.Rounded.Edit) {
                            onChangeButtonState()
                            buttonState = ButtonState.EDIT
                        }
                    }
                    ButtonState.EDIT -> {
                        SimpleIconButton(
                            imageVector = Icons.Rounded.Done,
                            backgroundColor = Color(0xff00c853)
                        ) {
                            onSaveClicked() //save note in db
                            onChangeButtonState() // change button state
                            focusManager.clearFocus() //closes the keyboard
                            buttonState = ButtonState.IDLE
                        }
                    }
                }
            }
        }

    }
}

@Composable
fun SimpleNoteDetails(
    modifier: Modifier = Modifier,
    state: DetailViewModel.DetailViewState,
    editEnabled: Boolean,
    onTitleChanged: (String) -> Unit,
    onContentChanged: (String) -> Unit,
) {
    val titleTextStyle = MaterialTheme.typography.h4.copy(
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colors.onSurface
    )
    val contentTextStyle =
        MaterialTheme.typography.body1.copy(color = MaterialTheme.colors.onSurface)

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        BasicTextField(
            value = state.title,
            onValueChange = {
                onTitleChanged(it)
            },
            textStyle = titleTextStyle,
            modifier = Modifier
                .padding(bottom = 16.dp)
                .fillMaxWidth(),
            cursorBrush = SolidColor(MaterialTheme.colors.onSurface),
            enabled = editEnabled,
            decorationBox = {
                if (state.title.isBlank()) {
                    Text(
                        text = "Title",
                        color = Color.Gray,
                        style = titleTextStyle
                    )
                }
                it()
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                capitalization = KeyboardCapitalization.Sentences
            )
        )

        if (state.date.isNotBlank()) {
            Text(
                text = state.date,
                style = MaterialTheme.typography.overline,
                modifier = Modifier.padding(bottom = 8.dp, top = 8.dp),
                color = Color.Gray
            )
        }

        BasicTextField(
            value = state.content,
            onValueChange = {
                onContentChanged(it)
            },
            textStyle = contentTextStyle,
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp),
            cursorBrush = SolidColor(MaterialTheme.colors.onSurface),
            enabled = editEnabled,
            decorationBox = {
                if (state.content.isBlank()) {
                    Text(
                        text = "Type something...",
                        color = Color.Gray,
                        style = contentTextStyle
                    )
                }
                it()
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                capitalization = KeyboardCapitalization.Sentences
            )
        )
    }
}