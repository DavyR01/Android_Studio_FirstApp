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
        val postsArray = listOf("Post 1", "Post 2", "Post 3", "Post 4", "Post 5")
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, postsArray)
        listPosts.adapter = adapter


        // 2. Afficher l'email dans le tvHello
/*        tvHello.text = "Bienvenue : $email"*/

    }
}