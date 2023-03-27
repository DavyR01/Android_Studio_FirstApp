package com.example.firstapp.data

class Post(
    var titre: String,
    var description: String,
    var image: ByteArray // Avant c'était un INT mais depuis l'implémentation de fun getBytes, on l'a changé en ByteArray, on supprime le postsArray (déclaration des posts) dans HomeActivity (ByteArray similaire à BLOB)
) {
    var id: Int = -1

    constructor(id: Int, titre: String, description: String, image: ByteArray): this(titre, description, image){
        this.id = id // initialisation de l'id
    }
}