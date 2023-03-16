package com.example.firstapp    // Chemin où est situé notre MainActivity.kt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    /* La classe MainActivity hérite de la classe AppCompatActivity qui nous permet d'avoir toutes les fonctionnalités, les attributs qui ns permettent d'intéragir avec l'interface web (XML). */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}