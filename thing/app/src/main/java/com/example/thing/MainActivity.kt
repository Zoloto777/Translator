package com.example.thing

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var dbHelper: DB
    private val locale = Locale("ru") // for Russian locale

        @SuppressLint("SetTextI18n")
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)

            dbHelper = DB(this)

            val wordEditText = findViewById<EditText>(R.id.editTextWord)
            val translationEditText = findViewById<EditText>(R.id.editTextTranslation)
            val addButton = findViewById<Button>(R.id.buttonAdd)
            val displayTextView = findViewById<TextView>(R.id.textViewDisplay)

            addButton.setOnClickListener {
                val word = wordEditText.text.toString().trim()
                val translation = translationEditText.text.toString().trim()

                if (word.isNotEmpty() && translation.isNotEmpty()) {
                    val insertedId = dbHelper.insertWord(word, translation)

                    if (insertedId != -1L) {
                        displayTextView.text = "Word added successfully!"
                    } else {
                        displayTextView.text = "Failed to add word."
                    }
                } else {
                    displayTextView.text = "Please enter both word and translation."
                }
            }

            showWords()
        }

        private fun showWords() {
            val cursor = dbHelper.getWords()
            val displayTextView = findViewById<TextView>(R.id.textViewDisplay)

            val stringBuilder = StringBuilder()
            while (cursor.moveToNext()) {
                val word = cursor.getString(cursor.getColumnIndexOrThrow(DB.COLUMN_WORD))
                val translation = cursor.getString(cursor.getColumnIndexOrThrow(DB.COLUMN_TRANSLATION))
                stringBuilder.append("$word: $translation\n")
            }

            displayTextView.text = stringBuilder.toString()
        }
    }




