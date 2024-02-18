package com.example.democontentprovider.SQLite

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.net.Uri
import com.example.democontentprovider.Model.TableSample


class MyDatabaseHepler(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        // If you change the database schema, you must increment the database version.
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "my_works.db"
    }

    var SQL_CREATE_ENTRIES = "CREATE TABLE ${TableSample.TABLE_NAME} (" +
            "${TableSample.COL_ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
            "${TableSample.COL_CONTENT} TEXT," +
            "${TableSample.COL_DATE} TEXT" +
            ");"

    private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${TableSample.TABLE_NAME}"

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }
    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }
}
