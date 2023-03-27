package com.example.firstapp

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import com.example.firstapp.data.Post
import com.example.firstapp.db.FacebookDatabase

class PostsAdapter( // Cette classe hérite de ArrayAdapter et pour cela, on doit lui passer des paramètres et ainsi créer le constructor
    var mContext: Context,
    var resource: Int,
    var values: ArrayList<Post>

) : ArrayAdapter<Post>(mContext, resource, values) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        // référencement et déclaration des variables.
        val post = values[position] //         val post = values.get(position)
        val itemView = LayoutInflater.from(mContext).inflate(resource, parent, false)
        // LayoutInflater est un convertisseur d'XML en view
        val tvTitre = itemView.findViewById<TextView>(R.id.tvTitre)
        val tvDescription = itemView.findViewById<TextView>(R.id.tvDescription)
        val imagePost = itemView.findViewById<ImageView>(R.id.imagePost)
        val imageShowPopup = itemView.findViewById<ImageView>(R.id.imageShowPopup)
        val tvLikes = itemView.findViewById<TextView>(R.id.tvLikes)

        tvTitre.text = post.titre
        tvDescription.text = post.description
        val bitmap = getBitmap(post.image)
//        imagePost.setImageResource(post.image) // Ce n'est plus une imageResource mais un BitArray à présent
        imagePost.setImageBitmap(bitmap)
        tvLikes.text = "${post.jaime} j'aime" // Concaténation de 2 strings, on aurait pu écrire : tvLikes.text = post.jaime.toString() + "j'aime"

        val db = FacebookDatabase(mContext)

        imageShowPopup.setOnClickListener {
            val popupMenu = PopupMenu(mContext, imageShowPopup)
            popupMenu.menuInflater.inflate(R.menu.list_popup_menu, popupMenu.menu)

            // Nous sommes désormais dans un contexte d'adapter :
            popupMenu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.itemShow -> {
                        Intent(mContext, PostsDetailsActivity::class.java).also {
                            it.putExtra("titre", post.titre)
                            mContext.startActivity(it)
                        }
                    }
                    R.id.itemDelete -> {
                        val resultDelete = db.deletePost(post.id)
                        if (resultDelete){
                            values.removeAt(position)
                            notifyDataSetChanged()
                        } else {
                            Toast.makeText(mContext, "Erreur de suppression", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                true
            }
            popupMenu.show()
        }

        tvLikes.setOnClickListener {
            // TODO: Incrémenter le compteur de j'aimes"
            db.incrementLikes(post)

            val incrementedLikes = post.jaime+1
            tvLikes.text = "${incrementedLikes} j'aime"   // Permet de MAJ les likes sans devoir rafraichir la page

        }

        return itemView
    }

    // Fonction getBitmap : Conversion image ByteArray -> Bitmap pour pouvoir être affiché lors d'un GET
    fun getBitmap(byteArray: ByteArray): Bitmap {
        val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
        return bitmap
    }
}