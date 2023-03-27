package com.example.firstapp.db;

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.firstapp.data.Post
import com.example.firstapp.data.User

// La création de la db se fait à l'appel du constructor SQLiteOpenHelper
class FacebookDatabase(mContext: Context /*name: String = DB_NAME, version: Int = DB_VERSION*/) :
    SQLiteOpenHelper(
        mContext, DB_NAME, null, DB_VERSION
    ) {
    override fun onCreate(db: SQLiteDatabase?) {
        // Création des tables de la db
        // les 3 guillemets permettent d écrire une chaine de caractères sans obligation de devoir préciser à chaque fois les retours à la ligne...
        val createTableUser = """                        
            CREATE TABLE $USERS_TABLE_NAME(
                $USER_ID integer PRIMARY KEY,
                $NAME varchar(50),
                $EMAIL varchar(100),
                $PASSWORD varchar(20)                       
            )
            
        """.trimIndent()

        val createTablePosts = """
            CREATE TABLE $POSTS_TABLE_NAME(
                $POST_ID integer PRIMARY KEY,
                $TITLE varchar(100),
                $DESCRIPTION text,
                $IMAGE blob  --format blob-->
            )
            
        """.trimIndent()

        db?.execSQL(createTableUser) // on inscrit un point d'interrogation pour indiquer que la db peut être nulle.
        db?.execSQL(createTablePosts)
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
        val result = db.insert(USERS_TABLE_NAME, null, values)
            .toInt() // On convertit le format LONG retourné par insert en INT

        db.close()

        return result != -1
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // Suppression des anciennes tables et re création des nouveaux
        db?.execSQL("DROP TABLE IF EXISTS $USERS_TABLE_NAME")
        db?.execSQL("DROP TABLE IF EXISTS $POSTS_TABLE_NAME")
        onCreate(db)
    }

    fun findUser(email: String, password: String): User? {

        val user: User? = null // Le ? indique que le user peut être null sinon il y aura une erreur, on le déclare à null au départ
        val db = this.readableDatabase // Différent de writableDatabase, on cherche ici à savoir si le user et le mot de passe que l'on rentre existe bel et bien dans la db
        val selectionArgs = arrayOf(email, password)
        val cursor = db.query(USERS_TABLE_NAME, null, "$EMAIL=? AND $PASSWORD=?", selectionArgs, null, null, null
        )
        /*val selectQuery = "SELECT * FROM $USERS_TABLE_NAME WHERE $EMAIL=?, $PASSWORD=?"
        db.rawQuery(selectQuery, arrayOf(email, password))*/
        if (cursor != null) {             // si on trouve un utilisateur, on return le user sinon on retourne un user null à la fin avec db.close()
            if (cursor.moveToFirst()) {
                val id = cursor.getInt(0)
                val name = cursor.getString(1)
                val email = cursor.getString(2)
                val user = User(id, name, email, "")
                return user
            }
        }
        db.close()
        return user

    }

    fun findPosts(): ArrayList<Post> {
        val posts = ArrayList<Post>()
        val db = readableDatabase
        val selectQuery = "SELECT * FROM $POSTS_TABLE_NAME"

        val cursor = db.rawQuery(selectQuery, null)
        if (cursor != null) {
            // parcours des différents éléments/enregistrements si il y en a grâce à une boucle pour pouvoir ensuite les afficher. Pour le findUser au dessus, on n'avait pas besoin de boucle car on voulait afficher que le 1er élément en question alors que dans ce cas, on souhaite afficher tous les enregistrements.
            if (cursor.moveToFirst()) {
                do {
                    val id = cursor.getInt(cursor.getColumnIndexOrThrow(POST_ID))
                    val titre = cursor.getString(cursor.getColumnIndexOrThrow(TITLE))
                    val description = cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPTION))
                    val image = cursor.getBlob(cursor.getColumnIndexOrThrow(IMAGE))
                    val post = Post(id, titre, description, image)
                    posts.add(post)
                } while (cursor.moveToNext())
            }
        }

        db.close()
        return posts
    }

    fun addPost(post: Post): Boolean {
        val db = writableDatabase

        val values = ContentValues()
        values.put(TITLE, post.titre)
        values.put(DESCRIPTION, post.description)
        values.put(IMAGE, post.image)
        // Se référer au constructeur de la class Post dans dossier data

        val result = db.insert(POSTS_TABLE_NAME, null, values)

        db.close()

        return result != -1L  // Au lieu de mettre toInt() comme dans fun addUser, on écrit un L pour indiquer que l'on va traiter et comparer avec un format LONG.
    }

    /*    static String name = ""     */
    companion object {
        private val DB_NAME = "facebook_db"
        private val DB_VERSION = 2

        // table users
        private val USERS_TABLE_NAME = "users"
        private val USER_ID = "id"
        private val NAME = "name"
        private val EMAIL = "email"
        private val PASSWORD = "password"

        // table posts
        private val POSTS_TABLE_NAME = "posts"
        private val POST_ID = "id"
        private val TITLE = "title"
        private val DESCRIPTION = "description"
        private val IMAGE = "image"
    }
}
