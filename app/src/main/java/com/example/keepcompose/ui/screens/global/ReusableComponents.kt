package com.example.keepcompose.ui.screens.global

import android.graphics.Paint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight

@Composable
fun SimpleIconButton(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    backgroundColor: Color = MaterialTheme.colors.secondary,
    onClick: () -> Unit,
) {
    val shape = RoundedCornerShape(percent = 30)
    Surface(
        shape = shape,
        modifier = modifier
            .clip(shape = shape)
            .clickable { onClick() },
        color = backgroundColor,
        contentColor = MaterialTheme.colors.onSurface
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = null,
            modifier = Modifier.padding(12.dp)
        )
    }
}

@Composable
fun SimpleAlertDialog(
    openDialog: Boolean,
    title: String,
    text: String,
    positiveButtonText: String,
    negativeButtonText: String,
    onClick: () -> Unit,
    onDismiss: () -> Unit
) {
    if (openDialog) {
        AlertDialog(
            backgroundColor = MaterialTheme.colors.background,
            shape = RoundedCornerShape(12.dp),
            onDismissRequest = { onDismiss() },
            title = {
                Text(
                    text = title,
                    style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colors.onSurface
                )
            },
            text = {
                Text(
                    text = text,
                    style = MaterialTheme.typography.body2,
                    color = MaterialTheme.colors.onSurface
                )
            },
            buttons = {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    SimpleTextButton(
                        text = negativeButtonText, onClick = { onDismiss() }
                    )

                    Spacer(modifier = Modifier.padding(horizontal = 16.dp))

                    SimpleTextButton(
                        text = positiveButtonText,
                        onClick = {
                            onClick()
                            onDismiss()
                        }
                    )
                }
            }
        )
    }
}

@Composable
fun SimpleTextButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
) {
    val shape = RoundedCornerShape(percent = 30)
    Surface(
        shape = shape,
        modifier = modifier
            .clip(shape = shape)
            .clickable { onClick() }
            .width(72.dp),
        color = MaterialTheme.colors.secondary,
        contentColor = MaterialTheme.colors.onSurface
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                text = text,
                style = MaterialTheme.typography.button,
                modifier = Modifier.padding(vertical = 12.dp, horizontal = 16.dp),
                color = MaterialTheme.colors.onSurface,
                fontSize = 16.sp
            )
        }
    }
}