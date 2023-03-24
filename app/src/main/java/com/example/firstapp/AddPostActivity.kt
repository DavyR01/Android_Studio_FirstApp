package com.example.firstapp

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import java.net.URI

class AddPostActivity : AppCompatActivity() {

    // Référencement de toutes les views de la page add_post_activity

    lateinit var btnSave: Button
    lateinit var editTitle: EditText
    lateinit var editDescription: EditText
    lateinit var imagePost: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_post)


        btnSave = findViewById(R.id.btnSave)
        editTitle = findViewById(R.id.editTitle)
        editDescription = findViewById(R.id.editDescription)
        imagePost = findViewById(R.id.imagePost)

        // Insertion d'une image lors de l'ajout d'un post, ne pas oublier de mettre imagePost en mode Tools(mode Préview) et non Android car sinon l'image téléchargé ne s'affichera pas correctement.

        imagePost.setOnClickListener {
            // Ouvrir gallery : On va utiliser un Intent implicite, càd que l'on va être dirigé en dehors de l'application elle-même.
            val intentImg = Intent(Intent.ACTION_GET_CONTENT)
            intentImg.type = "image/*"
            startActivityForResult(intentImg, 100) // startActivityForResult et non startActivity car on a besoin d'un résultat une fois que l'on a sélectionné notre image (code de retour/requete:100 par exemple).
        }

        btnSave.setOnClickListener {
            // Il faudra reconvertir l'image càd le bitmap décodé et le recodé en bytecode et non en inputstream car la db ne prend pas en compte les inputstream pour les fichiers et images
            // TODO : Enregistrer dans la base de données le post avec l'image !
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 100 && resultCode == RESULT_OK) {
            val uri: Uri? = data?.data

            val inputStream = contentResolver.openInputStream(uri!!) // fichier de retour inputStream, on le lit à travers le contentResolver, et il va ouvrir un menu InputStream, les !! permettent de décapsuler l'uri qui peut être null.
            val bitmap = BitmapFactory.decodeStream(inputStream) // On a besoin de décoder cette image, ce stream en bitmap.
            imagePost.setImageBitmap(bitmap) // On appelle notre image en faisant passer le bitmap
        }
    }

}