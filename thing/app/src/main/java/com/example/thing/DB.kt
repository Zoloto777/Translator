package com.example.thing

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DB(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "dictionary.db"
        const val DATABASE_VERSION = 1
        const val TABLE_NAME = "words"
        const val COLUMN_ID = "_id"
        const val COLUMN_WORD = "word"
        const val COLUMN_TRANSLATION = "translation"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = ("CREATE TABLE $TABLE_NAME (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_WORD TEXT, " +
                "$COLUMN_TRANSLATION TEXT)")
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insertWord(word: String, translation: String): Long {
        val contentValues = ContentValues()
        contentValues.put(COLUMN_WORD, word)
        contentValues.put(COLUMN_TRANSLATION, translation)

        return writableDatabase.insert(TABLE_NAME, null, contentValues)
    }

    fun getWords(): Cursor {
        val query = "SELECT * FROM $TABLE_NAME"
        return readableDatabase.rawQuery(query, null)
    }
}
