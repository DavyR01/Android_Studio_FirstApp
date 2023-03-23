package com.example.firstapp

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.firstapp.data.Post

class HomeActivity : AppCompatActivity() {

    lateinit var listPosts: ListView // Variable déclaré en globale désormais pour pouvoir la réutiliser dans une autre méthode.
    var postsArray = ArrayList<Post>()
    lateinit var adapter: PostsAdapter // les : permettent d'indiquer le type de la variable.

    // Méthode pour créer et afficher les 5 posts avec des descriptions, titre et images distincts.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

/*        val tvHello = findViewById<TextView>(R.id.tvHello)*/

        // 1. Récupérer l'email envoyé par l'activityMain
        val email = intent.getStringExtra("email")


        listPosts = findViewById(R.id.listPosts)
        postsArray = arrayListOf(
            Post(
                "Post 1", "une description 1 du post 1 qui va être affiché ici", R.drawable.image1
            ), Post("Post 2", "une deuxième description", R.drawable.image2), Post(
                "Post 3",
                "une description 3 du post 3 qui sera inscrite là dessus !!!!!",
                R.drawable.image3
            ), Post(
                "Post 4",
                "une description 4 du post 4 qui sera également présente et affiché ici",
                R.drawable.image4
            ), Post(
                "Post 5",
                "the last description 5 du post 5 avec une description 5 !!!!",
                R.drawable.image5
            )
        )
        adapter = PostsAdapter(this, R.layout.item_post, postsArray)
/*      val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, postsArray)*/
        listPosts.adapter = adapter

        listPosts.setOnItemClickListener(AdapterView.OnItemClickListener { adapterView, view, position, id ->
/*            Toast.makeText(this, "Position : $position", Toast.LENGTH_SHORT).show()*/
            val clikedPost = postsArray[position]
            Intent(this, PostsDetailsActivity::class.java).also {
                it.putExtra("titre", clikedPost.titre)
                startActivity(it)
            }
        })

        // OnItemClickListener est une classe anonyme et n'est pas obligatoire
        // setOnItemClickListener est une expression lambda.
        // 2. Afficher l'email dans le tvHello
/*        tvHello.text = "Bienvenue : $email"*/

        registerForContextMenu(listPosts) // Permet d'afficher le context menu en laissant le clique dessus
    } // fin onCreate

    // Méthode pour créer et afficher les options des menus (Add, Config, Se déconnecter)
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    // Méthode pour créer une action lors du clique des options
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.itemAdd -> {
                Toast.makeText(this, "Add new post", Toast.LENGTH_SHORT).show()
            }
            R.id.itemConfig -> {
                Toast.makeText(this, "App Configuration", Toast.LENGTH_SHORT).show()
            }
            R.id.itemLogout -> {
                // Afficher un dialogue de confirmation
                showLogoutConfirmDialog()
                /*finish()*/
            }
        }
        return super.onOptionsItemSelected(item)
    }

    // Méthode pour créer une fenêtre de dialogue de confirmation lors d'un clique sur 'Se déconnecter'
    fun showLogoutConfirmDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Confirmation !!")
        builder.setMessage("Etes-vous sûr de vouloir quitter l'application ??")
        builder.setPositiveButton("Oui") { dialogInterface: DialogInterface, id: Int ->

            val editor = getSharedPreferences("app_state", Context.MODE_PRIVATE).edit()
            editor.remove("is_authentificated") // On pourrait l'écrire : 'editor.putBoolean()' afin de switcher le Boolean
            editor.apply()
            finish()
        }
        builder.setNegativeButton("Non") { dialogInterface, id: Int ->
            dialogInterface.dismiss()
        }
        builder.setNeutralButton("Annuler") { dialogInterface, id: Int ->
            dialogInterface.dismiss()
        }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()
    }

}

// Méthode pour créer et afficher le contextMenu lorsque l'on reste appuyer sur un post ...

/*    override fun onCreateContextMenu(
        menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        menuInflater.inflate(R.menu.list_popup_menu, menu)
        super.onCreateContextMenu(menu, v, menuInfo)
    }

    // Méthode pour créer une action lors du clique sur les contextes du menu (Afficher, Supprimer)
    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info: AdapterContextMenuInfo = item.menuInfo as AdapterContextMenuInfo
        val position: Int = info.position
        when (item.itemId) {
            R.id.itemShow -> {
                val clikedPost = postsArray[position]
                Intent(this, PostsDetailsActivity::class.java).also {
                    it.putExtra("titre", clikedPost.titre)
                    startActivity(it)
                }
            }
            R.id.itemDelete -> {
                postsArray.removeAt(position)
                adapter.notifyDataSetChanged()
            }
        }
        return super.onContextItemSelected(item)
    }*/

