package com.example.keepcompose.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.keepcompose.model.KeepNote
import com.example.keepcompose.model.additionalNote
import com.example.keepcompose.model.launchNote
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [KeepNote::class], version = 5, exportSchema = false)
public abstract class KeepNoteDatabase: RoomDatabase() {

    abstract fun keepNoteDao(): KeepNoteDao

    companion object {
        @Volatile
        private var INSTANCE: KeepNoteDatabase? = null
        fun getDatabase(context: Context, scope: CoroutineScope): KeepNoteDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) return tempInstance
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    KeepNoteDatabase::class.java,
                    "keep_note_database"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(KeepNoteDatabaseCallback(scope))
                    .build()

                INSTANCE = instance
                return instance
            }
        }
    }

    private class KeepNoteDatabaseCallback(
        private val scope: CoroutineScope
    ): RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    val dao = database.keepNoteDao()
                    dao.insert(launchNote)
                    dao.insert(additionalNote)
                }
            }
        }
    }
}