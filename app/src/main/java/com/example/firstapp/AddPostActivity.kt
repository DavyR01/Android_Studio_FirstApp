package com.example.firstapp

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.firstapp.data.Post
import com.example.firstapp.db.FacebookDatabase
import java.io.ByteArrayOutputStream

class AddPostActivity : AppCompatActivity() {

    // Référencement de toutes les views de la page add_post_activity
    lateinit var btnSave: Button
    lateinit var editTitle: EditText
    lateinit var editDescription: EditText
    lateinit var imagePost: ImageView

    var bitmap: Bitmap? = null
    lateinit var db: FacebookDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_post)

        db = FacebookDatabase(this) // On instancie la db situé dans FacebookDatabase en passant le context this de notre activity. On initialise la db

        btnSave = findViewById(R.id.btnSave)
        editTitle = findViewById(R.id.editTitle)
        editDescription = findViewById(R.id.editDescription)
        imagePost = findViewById(R.id.imagePost)

        val galleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { data ->

            val inputStream = contentResolver.openInputStream(data!!) // fichier de retour inputStream, on le lit à travers le contentResolver, et il va ouvrir un menu InputStream, les !! permettent de décapsuler l'uri qui peut être null.
            bitmap = BitmapFactory.decodeStream(inputStream) // On a besoin de décoder cette image, ce stream en bitmap.
            imagePost.setImageBitmap(bitmap) // On appelle notre image en faisant passer le bitmap
        }

        // Insertion d'une image lors de l'ajout d'un post, ne pas oublier de mettre imagePost en mode Tools(mode Preview) et non Android car sinon l'image téléchargée ne s'affichera pas correctement.

        imagePost.setOnClickListener {
            // Ouvrir gallery : On va utiliser un Intent implicite, càd que l'on va être dirigé en dehors de l'application elle-même.
            galleryLauncher.launch("image/*")
        }

        btnSave.setOnClickListener {
            // TODO : Enregistrer dans la base de données le post avec l'image !
            // Il faudra reconvertir l'image càd le bitmap décodé et le recodé en bytecode et non en inputstream car la db ne prend pas en compte les inputstream pour les fichiers et images

            // récupérer les différentes valeurs...
            val titre = editTitle.text.toString()
            val description = editDescription.text.toString()
            if(titre.isEmpty() || description.isEmpty() ||bitmap == null) {
                Toast.makeText(this, R.string.error_empty_fields, Toast.LENGTH_SHORT).show()
            }
            val imagesBlob: ByteArray = getBytes(bitmap!!)

            val post = Post(titre, description, imagesBlob)

            db.addPost(post)

            // Supprimer le formulaire
            editTitle.setText("")
            editDescription.setText("")
            bitmap = null

            finish()    // finish () permet de diriger directement vers l'activity précédente, càd HomeActivity
            // Lorsque l'on a finit d'ajouter un post, on sera redirigé vers la HomeActivity et pour que la page se mette à jour correctement, on va créer un fonction onResume (HomeActivity), on ne repassera donc pas par onCreate. C'est tout un cycle de vie d'une activity. Grossomodo, on commence par onCreate, après onResume puis onStart...
        }
    } // fin onCreate

    // Fonction getBytes : Conversion image bitmap -> ByteArray pour être traité par la database lors d'un POST
    // Conversion image ByteArray -> bitmap lorsque l'on voudra l'afficher lors d'un GET
    fun getBytes(bitmap: Bitmap): ByteArray {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream)
        return stream.toByteArray()
    }
}


// Ancienne configuration avec utilisation de 'startActivityForResult' de l'insertion d'image mais qui est dépréciée par android studio.
// On utilisera donc un galleryLauncher

/*    imagePost.setOnClickListener {
        // Ouvrir gallery : On va utiliser un Intent implicite, càd que l'on va être dirigé en dehors de l'application elle-même.
        val intentImg = Intent(Intent.ACTION_GET_CONTENT)
        intentImg.type = "image/*"
        startActivityForResult(intentImg, 100) // startActivityForResult et non startActivity car on a besoin d'un résultat une fois que l'on a sélectionné notre image (code de retour/requete:100 par exemple).
    }*/

/*    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 100 && resultCode == RESULT_OK) {
            val uri: Uri? = data?.data

            val inputStream = contentResolver.openInputStream(uri!!) // fichier de retour inputStream, on le lit à travers le contentResolver, et il va ouvrir un menu InputStream, les !! permettent de décapsuler l'uri qui peut être null.
            val bitmap = BitmapFactory.decodeStream(inputStream) // On a besoin de décoder cette image, ce stream en bitmap.
            imagePost.setImageBitmap(bitmap) // On appelle notre image en faisant passer le bitmap
        }
    }*/