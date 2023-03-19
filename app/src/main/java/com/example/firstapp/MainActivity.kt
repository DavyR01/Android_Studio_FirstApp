package com.example.firstapp    // Chemin où est situé notre MainActivity.kt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity() {
    /* La classe MainActivity hérite de la classe AppCompatActivity qui nous permet d'avoir toutes les fonctionnalités, les attributs qui ns permettent d'intéragir avec l'interface web (XML). */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
/*
        setContentView(R.layout.auth_page_fb_constraint_layout)
*/
/*
        setContentView(R.layout.auth_page_fb_linear_layout)
*/
        setContentView(R.layout.auth_page_fb_relative_layout)

        val connect = findViewById<Button>(R.id.connect)
        connect.setOnClickListener(View.OnClickListener { view: View ->
            println("Hello, je suis le bouton connect")
        })
/*        connect.setOnClickListener{
            println("Hello, je suis le bouton connect ${it.id} !!")
        }*/
    }
}

/*
View.OnClickListener est une lambda expression, c est une classe anonyme et on peut ne pas l écrire.*/
