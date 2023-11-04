package com.example.ebookstore

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.sql.SQLException

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "UserComments.db"
        private const val DATABASE_VERSION = 1
        const val TABLE_COMMENTS = "comments"
        const val COLUMN_ID = "id"
        const val COLUMN_USERNAME = "username"
        const val COLUMN_COMMENT = "comment"
        const val COLUMN_DATE = "date"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableSQL = """
            CREATE TABLE $TABLE_COMMENTS (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_USERNAME TEXT,
                $COLUMN_COMMENT TEXT,
                $COLUMN_DATE TEXT
            )
        """.trimIndent()
        try {
            db?.execSQL(createTableSQL)
        } catch (e: SQLException) {
            // Handle the exception, e.g., log an error message or take appropriate action.
            Log.d("Error -->","Database Error")
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_COMMENTS")
        onCreate(db)
    }

    fun addComment(comment: CommentModel) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_USERNAME, comment.username)
        values.put(COLUMN_COMMENT, comment.comment)
        values.put(COLUMN_DATE, comment.date)
        db.insert(TABLE_COMMENTS, null, values)
        db.close()
    }

    fun getAllComments(): List<CommentModel> {
        val commentList = mutableListOf<CommentModel>()
        val query = "SELECT * FROM $TABLE_COMMENTS"
        val db = this.readableDatabase
        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val comment = CommentModel(
                    cursor.getString(1), // Use the first column for the username
                    cursor.getString(2),
                    cursor.getString(3)
                )
                commentList.add(comment)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return commentList
    }
    fun clearAllComments() {
        val db = this.writableDatabase
        db.delete(TABLE_COMMENTS, null, null)
        db.close()
    }

}
