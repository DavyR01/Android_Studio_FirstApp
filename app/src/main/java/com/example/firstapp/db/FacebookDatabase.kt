package com.example.firstapp.db;

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

// La création de la db se fait à l'appel du constructor SQLiteOpenHelper
class FacebookDatabase(mContext: Context, /*name: String = DB_NAME, version: Int = DB_VERSION*/) : SQLiteOpenHelper(
    mContext,
    DB_NAME,
    null,
    DB_VERSION
) {
    override fun onCreate(db: SQLiteDatabase?) {
        // Création des tables de la db
        // les 3 guillemets permettent d écrire une chaine de caractères sans obligation de devoir préciser à chaque fois les retours à la ligne...
        val createTableUser = """                        
            CREATE TABLE users(
                $USER_ID integer PRIMARY KEY,
                $NAME varchar(50),
                $EMAIL varchar(100),
                $PASSWORD varchar(20)                       
            )
        """.trimIndent()
        db?.execSQL(createTableUser) // on inscrit un point d'interrogation pour indiquer que la db peut être nulle.
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // Suppression des anciennes tables
        db?.execSQL("DROP TABLE IF EXISTS $USERS_TABLE_NAME")
        onCreate(db)

        // re création des nouveaux
    }

    /*    static String name = ""     */
    companion object {
        private val DB_NAME = "facebook_db"
        private val DB_VERSION = 1
        private val USERS_TABLE_NAME = "users"
        private val USER_ID = "id"
        private val NAME = "name"
        private val EMAIL = "email"
        private val PASSWORD = "password"
    }
}
