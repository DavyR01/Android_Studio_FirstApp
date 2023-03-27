package com.example.firstapp.data

class Post(
    var titre: String,
    var description: String,
    var image: ByteArray, // Avant c'était un INT mais depuis l'implémentation de fun getBytes, on l'a changé en ByteArray, on supprime le postsArray (déclaration des posts) dans HomeActivity (ByteArray similaire à BLOB)
    val jaime: Int = 0  // Valeur par défaut sera 0 pour les likes au départ
) {
    var id: Int = -1

    constructor(id: Int, titre: String, description: String, image: ByteArray, jaime: Int): this(titre, description, image, jaime){
        this.id = id // initialisation de l'id
    }
}