package com.example.firstapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

/*        val tvHello = findViewById<TextView>(R.id.tvHello)*/

        // 1. Récupérer l'email envoyé par l'activityMain
        val email = intent.getStringExtra("email")


        val listPosts = findViewById<ListView>(R.id.listPosts)
        val postsArray = arrayListOf(
            Post("Post 1", "une description 1 du post 1 qui va être affiché ici", R.drawable.image1),
            Post("Post 2", "une deuxième description", R.drawable.image2),
            Post("Post 3", "une description 3 du post 3 qui sera inscrite là dessus !!!!!", R.drawable.image3),
            Post("Post 4", "une description 4 du post 4 qui sera également présente et affiché ici", R.drawable.image4),
            Post("Post 5", "the last description du post 5 avec une description 5 !!!!", R.drawable.image5)
        )
        val adapter = PostsAdapter(this, R.layout.item_post, postsArray)
/*      val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, postsArray)*/
        listPosts.adapter = adapter


        // 2. Afficher l'email dans le tvHello
/*        tvHello.text = "Bienvenue : $email"*/

    }
}