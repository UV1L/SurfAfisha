package com.example.surfafisha.DB

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class FilmsDB(context: Context, name: String) : SQLiteOpenHelper(
    context, name, null, 1
) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            "CREATE TABLE $databaseName ("
                    + "id integer primary key autoincrement,"
                    + "filmId integer,"
                    + "favorite integer" + ");"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }
}