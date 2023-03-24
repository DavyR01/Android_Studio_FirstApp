package com.example.firstapp.data

class Post(
    var titre: String,
    var description: String,
    var image: ByteArray // Avant c'est un INT mais depuis l'implémentation de fun getBytes, on l'a changé en ByteArray, on supprime le postsArray (déclaration des posts) dans HomeActivity (ByteArray similaire à BLOB)
)