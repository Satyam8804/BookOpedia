//package com.example.ebookstore.dataBase
//
//import android.content.ContentValues
//import android.content.Context
//import android.database.sqlite.SQLiteDatabase
//import android.database.sqlite.SQLiteOpenHelper
//import android.widget.Toast
//
//class DatabaseHelper(context: Context):SQLiteOpenHelper(context,DB_NAME,null,DB_VERSION,) {
//    companion object{
//        private val DB_NAME="Books"
//        private val DB_VERSION=1
//        private val Table_NAME="Books_table"
//        private val id=0
//        private val title=""
//        private val genre=""
//        private val coverImg=""
//        private val desc:String = ""
//        private val rating:String=""
//        private val authorName:String=""
//        private val publishedYear:String=""
//        private val link:String=""
//    }
//
//    override fun onCreate(p0: SQLiteDatabase?) {
//        TODO("Not yet implemented")
//        val createTable = "CREATE TABLE $Table_NAME($id INTEGER PRIMARY KEY,$title STRING,$genre STRING,$coverImg STRING,$desc STRING,$rating STRING,$authorName STRING,$publishedYear STRING,$link STRING)"
//        p0?.execSQL(createTable)
//    }
//
//    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
//        TODO("Not yet implemented")
//    }
//     fun addBook(book:bookModel):Boolean {
//         val db = this.writableDatabase
//         val values = ContentValues()
//         values.put(authorName, book.getAuthorName())
//         values.put(desc, book.getDesc())
//         values.put(title, book.getTitle())
//         values.put(rating, book.getRating())
//         values.put(publishedYear, book.getYear())
//         values.put(genre, book.getGenre())
//         values.put(link, book.getLink())
//         values.put(coverImg, book.getCoverImg())
//         val success = db.insert(Table_NAME, null, values)
//         return (success != -1L)
//     }
//
//
//
//
//}