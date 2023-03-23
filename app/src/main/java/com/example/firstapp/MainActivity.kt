package com.example.firstapp    // Chemin où est situé notre MainActivity.kt

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.firstapp.db.FacebookDatabase
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {
    /* La classe MainActivity hérite de la classe AppCompatActivity qui nous permet d'avoir toutes les fonctionnalités, les attributs qui ns permettent d'intéragir avec l'interface web (XML). */

    lateinit var sharedPreferences: SharedPreferences // Une variable non nul déclarée avec le mot clé lateinit lors de sa déclaration peut être initialisée plus tard partout dans votre code
    lateinit var db: FacebookDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.auth_page_fb_relative_layout)

        sharedPreferences = this.getSharedPreferences("app_state", Context.MODE_PRIVATE)
        db = FacebookDatabase(this)

        val isAutentificated = sharedPreferences.getBoolean("is_authentificated", false)
        // Afin de vérifier la valeur du boolean is_authentificated, on va se rendre dans Device File Explorer et le chemin /data/data/com.example.firstapp/shared_prefs/app_state.xml
        val emailSharedPreferences = sharedPreferences.getString("email", null)

        if (isAutentificated) {              // On vérifie si l'utilisateur s'est déja connecté auparavant pour le diriger directement vers la homePage sans devoir rentrer de nouveau le identifiants
            Intent(this, HomeActivity::class.java).also {
                it.putExtra("email", emailSharedPreferences)
                startActivity(it)
            }
        }

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

                    // Enregistrer dans sharedPreferences le boolean isAuthentificated
                    val editor = sharedPreferences.edit()
                    editor.putBoolean("is_authentificated", true)
                    editor.putString("email", txtEmail)
                    editor.apply() // apply fonctionne en asynchrone, càd qu'il passera à l'étape suivant meme si l'instruction n'est pas traité entièrement contrairement à editor.commit()

                    error.visibility =
                        View.GONE    // Presque similaire à .INVISIBLE sauf que l'espace nécessaire à son affichage sera 'dismiss' lorsque l'erreur ne doit pas être affichée.
                    // Intent Explicite : Permet de naviguer entre plusieurs activity : Intent dirige vers une nouvelle activity
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
