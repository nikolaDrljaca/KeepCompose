package com.example.keepcompose.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.DateFormat
import kotlin.random.Random

@Entity(tableName = "keep_note_table")
data class KeepNote (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "content") val content: String,
    val created: Long = System.currentTimeMillis(),
    val color: Long = generateRandomColor()
) {
    val createdDateFormatted: String
        get() = DateFormat.getDateTimeInstance().format(created)

}

private fun generateRandomColor(): Long {
    val list = CardColor::class.sealedSubclasses
    val randomNum = Random.nextInt(5)
    list[randomNum].objectInstance?.let { cardColor ->
        return cardColor.colorValue
    }
    return CardColor.CardOrange.colorValue
}

sealed class CardColor(val colorValue: Long) {
    object CardRed: CardColor(0xffffab91)
    object CardOrange: CardColor(0xffffcc80)
    object CardGreen: CardColor(0xffe6ee9b)
    object CardBlue: CardColor(0xff80deea)
    object CardPink: CardColor(0xfff48fb1)
    object CardPurple: CardColor(0xffcf93d9)
}

val launchNote = KeepNote(
    title = "Welcome Note",
    content = "This is a new app built entirely with Jetpack Compose!\n" +
        "Playing with multiple other apis\n" +
        "The next project will contain some network data"
)

val additionalNote = KeepNote(
    title = "Testing note",
    content = "Doing some testing on initial data"
)