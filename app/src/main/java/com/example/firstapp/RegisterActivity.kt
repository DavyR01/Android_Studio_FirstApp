package com.example.firstapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.firstapp.data.User
import com.example.firstapp.db.FacebookDatabase

class RegisterActivity : AppCompatActivity() {

    lateinit var  db: FacebookDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        db = FacebookDatabase(this)
        val editName = findViewById<EditText>(R.id.editName)
        val editEmail = findViewById<EditText>(R.id.editEmail)
        val editPassword = findViewById<EditText>(R.id.editPassword)
        val editConfirmPassword = findViewById<EditText>(R.id.editConfirmPassword)
        val btnSave = findViewById<Button>(R.id.btnSave)
        val tvError = findViewById<TextView>(R.id.tvError)

        btnSave.setOnClickListener {
            tvError.visibility = View.INVISIBLE
            tvError.text = ""

            val name = editName.text.toString()
            val email = editEmail.text.toString()
            val password = editPassword.text.toString()
            val confirmPassword = editConfirmPassword.text.toString()

            // check if null
            if(name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty())
            {
                tvError.text = getString(R.string.error_empty_fields)
                tvError.visibility = View.VISIBLE
            } else {
                if (password != confirmPassword)
                {
                    tvError.text = getString(R.string.passwords_different)
                    tvError.visibility = View.VISIBLE
                }
                else {
                    val user = User(name, email, password)
                    val isInserted = db.addUser(user)
                    if (isInserted) {
                        Toast.makeText(this, getString(R.string.success_register), Toast.LENGTH_SHORT).show()
                        Intent(this, HomeActivity::class.java).also {
                            it.putExtra("email", email)
                            startActivity(it)
                        }
                    }
                }
            }
        }

    }
}