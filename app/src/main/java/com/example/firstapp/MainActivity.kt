package com.example.firstapp    // Chemin où est situé notre MainActivity.kt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    /* La classe MainActivity hérite de la classe AppCompatActivity qui nous permet d'avoir toutes les fonctionnalités, les attributs qui ns permettent d'intéragir avec l'interface web (XML). */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.auth_page_fb_relative_layout)

        val connect = findViewById<Button>(R.id.connect)
        val email = findViewById<EditText>(R.id.email)
        val password = findViewById<EditText>(R.id.password)
        val error = findViewById<TextView>(R.id.error)

        connect.setOnClickListener {
/*          println("Hello, je suis le bouton connect ${it.id} !!")*/
            Toast.makeText(this, "vous avez cliqué sur le bouton connect !", Toast.LENGTH_SHORT)
                .show()

            val txtEmail = email.text.toString()
            val txtPassword = password.text.toString()

            if (txtEmail.trim().isEmpty() || txtPassword.trim().isEmpty()) {
                error.text = "Vous devez remplir tous les champs"
                error.visibility = View.VISIBLE
            } else {
                val correctEmail = "davy@gmail.com"
                val correctPassword = "azerty"
                if (correctEmail == txtEmail && correctPassword == txtPassword) {
                    email.setText("")
                    password.setText("")
                    error.visibility = View.GONE
                    // Intent Explicite : Permet de naviguer entre plusieurs activity
                    Intent(this, HomeActivity::class.java).also {
                        it.putExtra("email", txtEmail)
                        startActivity(it)
                    }
/*                    val intentToHomeActivity: Intent = Intent(this, HomeActivity::class.java).also {
                        intentToHomeActivity.putExtra("email", txtEmail)
                        startActivity(intentToHomeActivity)
                    }*/
                } else {
                    error.text = "Email ou password n'est pas correct !"
                    error.visibility = View.VISIBLE
                }
            }
        }


    }
}

/*
     connect.setOnClickListener(View.OnClickListener { view: View ->
            println("Hello, je suis le bouton connect !!")
        })
View.OnClickListener est une lambda expression, c est une classe anonyme et on peut ne pas l écrire.*/
