package com.example.democontentprovider.ContentProvider

import android.content.ContentProvider
import android.content.ContentResolver
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.sqlite.SQLiteQueryBuilder
import android.net.Uri
import com.example.democontentprovider.Model.TableSample
import com.example.democontentprovider.SQLite.MyDatabaseHepler

class MyContentProvider: ContentProvider() {

    private lateinit var dbHelper: MyDatabaseHepler

    companion object {
        // provider name
        val AUTHORITY = "com.example.demo.content.provider"
        val PATH_TABLE = "work"
        val PATH_COL = "work/#"
        val CONTENT_URL = "content://$AUTHORITY/$PATH_TABLE"
        val CONTENT_URI = Uri.parse(CONTENT_URL)

        val table_code = 1
        val col_code = 2

        private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
            addURI(AUTHORITY, PATH_TABLE, table_code)
            addURI(AUTHORITY, PATH_COL, col_code)
        }
    }

    override fun onCreate(): Boolean {
        dbHelper = MyDatabaseHepler(context!!)
        return dbHelper?.let { true }?: false
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArg: Array<out String>?,
        sortOder: String?
    ): Cursor? {
        val db = dbHelper.writableDatabase
        val queryBuilder = SQLiteQueryBuilder()
        queryBuilder.tables = TableSample.TABLE_NAME

        when(uriMatcher.match(uri)) {
            table_code -> {
                val cursor = queryBuilder.query(db, projection, selection, selectionArg, null, null, sortOder)
                cursor.setNotificationUri(context!!.contentResolver, uri)
                return cursor
            }
            else -> throw IllegalArgumentException("Unsupported URI: $uri")
        }

        val cursor = db.query(
            TableSample.TABLE_NAME,
            projection,
            selection,
            selectionArg,
            null,
            null,
            sortOder
        )
        return cursor
    }

    override fun getType(uri: Uri): String? {
        return when(uriMatcher.match(uri)) {
            1 -> ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + AUTHORITY
            2 -> ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + AUTHORITY
            else -> throw IllegalArgumentException("Unsupported URI: $uri")
        }
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val db = dbHelper.writableDatabase
        val rowId: Long = db.insert(TableSample.TABLE_NAME, null, values)
        context!!.contentResolver.notifyChange(uri, null)
        return Uri.withAppendedPath(CONTENT_URI, rowId.toString())
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        val db = dbHelper.writableDatabase
        val count: Int = db.delete(TableSample.TABLE_NAME, selection, selectionArgs)
        context!!.contentResolver.notifyChange(uri, null)
        return count
    }

    override fun update(uri: Uri, value: ContentValues?, selection: String?, selectionArgs: Array<out String>?): Int {
        val db = dbHelper.writableDatabase
        val count: Int = db.update(TableSample.TABLE_NAME, value, selection, selectionArgs)
        context!!.contentResolver.notifyChange(uri, null)
        return count
    }
}