package com.example.noteapp

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DBHolder(
    context: Context?

) : SQLiteOpenHelper(context, "NotesD.db", null, 2) {

    var sqlLiteDatabase: SQLiteDatabase = writableDatabase

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "NotesD"
        private const val TABLE_NOTES = "notes"

        private const val KEY_ID = "_id"
        private const val KEY_NOTE = "Note"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        if (db != null) {
            db.execSQL("CREATE TABLE $TABLE_NOTES($KEY_ID INTEGER PRIMARY KEY, $KEY_NOTE TEXT)")


        }
    }

    override fun onUpgrade(dp: SQLiteDatabase?, p1: Int, p2: Int) {
        dp!!.execSQL("DROP TABLE IF EXISTS notes")


        onCreate(dp)
    }

    fun addNote(note: String): Long {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put("$KEY_NOTE", note)

        var status = db.insert("$TABLE_NOTES", null, cv)
        return status
    }

    @SuppressLint("Range")


    fun getNotesArray(): ArrayList<Note> {

        val db = this.readableDatabase

        var cursor = db.rawQuery("SELECT * FROM notes", null)
        val myNotes = arrayListOf<Note>()
        if (cursor!!.moveToFirst()) {
            myNotes.add(
                Note(
                    cursor.getString(cursor.getColumnIndex(KEY_NOTE)),
                    cursor.getInt(cursor.getColumnIndex(KEY_ID))
                )
            )
            while (cursor.moveToNext()) {
                myNotes.add(
                    Note(
                        cursor.getString(cursor.getColumnIndex(KEY_NOTE)),
                        cursor.getInt(cursor.getColumnIndex(KEY_ID))
                    )
                )
            }
            cursor.close()

        }


        return myNotes
    }


    fun updateNote(note: Note): Boolean {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(KEY_NOTE, note.noteDes)
        val _success = db.update(TABLE_NOTES, cv, "$KEY_ID =?", arrayOf(note.id.toString())).toLong()
        db.close()
        return Integer.parseInt("$_success") != -1
    }

    fun deleteNote(Note_id: Int): Boolean {
        val db = this.writableDatabase
        val _success = db.delete(TABLE_NOTES, "$KEY_ID =?", arrayOf(Note_id.toString())).toLong()
        db.close()
        return Integer.parseInt("$_success") != -1
    }


}