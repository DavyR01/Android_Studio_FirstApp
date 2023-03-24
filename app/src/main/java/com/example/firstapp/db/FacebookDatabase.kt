package com.example.firstapp.db;

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.firstapp.data.User

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

    fun addUser(user: User): Boolean {
        // Insérer un nouvel utilisateur dans la db
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(NAME, user.name)
        values.put(EMAIL, user.email)
        values.put(PASSWORD, user.password)

        // insert into users(nom, email, password) values(user.nom, user.email, user.password)
        // les Key sont les noms des colonnes et les values sont les valeurs.
        val result = db.insert(USERS_TABLE_NAME, null, values).toInt()

        db.close()

        return result != -1
    }

    fun findUser(email: String, password: String) : User? {

        val user: User? = null // Le ? indique que le user peut être null sinon il y aura une erreur, on le déclare à null au départ
        val db = this.readableDatabase // Différent de writableDatabase, on cherche ici à savoir si le user et le mot de passe que l'on rentre existe bel et bien dans la db
        val selectionArgs = arrayOf(email, password)
        val cursor = db.query(USERS_TABLE_NAME, null, "$EMAIL=? AND $PASSWORD=?", selectionArgs, null, null, null)
        if (cursor != null) {             // si on trouve un utilisateur, on return le user sinon on retourne un user null à la fin avec db.close()
            if(cursor.moveToFirst()) {
                val id = cursor.getInt(0)
                val name = cursor.getString(1)
                val email = cursor.getString(2)
                val user = User(id, name, email, "")
                return user
            }
        }
        db.close()
        return user


        /*val selectQuery = "SELECT * FROM $USERS_TABLE_NAME WHERE $EMAIL=?, $PASSWORD=?"
        db.rawQuery(selectQuery, arrayOf(email, password))*/

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
