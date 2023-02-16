package com.tsm.catatantsm.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns._ID
import com.tsm.catatantsm.db.DatabaseContract.NoteColumns.Companion.TABLE_NAME



class NoteHelper(context: Context) {

    private var dataBaseHelper: DatabaseHelper = DatabaseHelper(context)
    private lateinit var database: SQLiteDatabase

    companion object {
        private const val DATABASE_TABLE = TABLE_NAME
        private var INSTANCE: NoteHelper? = null

        fun getInstance(context: Context): NoteHelper =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: NoteHelper(context)
            }
    }

    @Throws(SQLException::class)
    fun open() {
        database = dataBaseHelper.writableDatabase
    }

    fun close() {
        dataBaseHelper.close()

        if (database.isOpen)
            database.close()
    }

    /**
     * Menampilkan data dari semua note yang ada di dalam database
     */
    fun queryAll(): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            null,
            null,
            null,
            null,
            "$_ID ASC"
        )
    }

    /** Mengambil data dari catatan berdasarkan id
     */
    fun queryById(id: String): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            "$_ID = ?",
            arrayOf(id),
            null,
            null,
            null,
            null
        )
    }

    /** Menyimpan data ke dalam database
     */
    fun insert(values: ContentValues?): Long {
        return database.insert(DATABASE_TABLE, null, values)
    }

    /** Update/Mengedit data dalam database
     */
    fun update(id: String, values: ContentValues?): Int {
        return database.update(DATABASE_TABLE, values, "$_ID = ?", arrayOf(id))
    }

    /** Menghapus data yang ada didalam database
     */
    fun deleteById(id: String): Int {
        return database.delete(DATABASE_TABLE, "$_ID = '$id'", null)
    }

}