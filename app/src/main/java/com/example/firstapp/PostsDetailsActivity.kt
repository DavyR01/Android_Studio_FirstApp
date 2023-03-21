package com.example.firstapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class PostsDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_posts_details)

        val tvTitre = findViewById<TextView>(R.id.tvTitre)
        val titre = intent.getStringExtra("titre")
        tvTitre.text = titre
        // Va servir à afficher le titre du post dans la page

        supportActionBar!!.title = titre
        /* On appose 2 points d'exclamation pour indiquer que la valeur peut etre nulle.
        * Sert à afficher le titre du post dans l'action bar */

    }
}