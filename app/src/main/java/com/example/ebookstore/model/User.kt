package com.example.ebookstore.model

class User {
    var name: String? = null
    var email: String? = null
    var pass: String? = null
    var image: String? = null

    // Add a no-argument constructor
    constructor()

    constructor(name: String, email: String, pass: String, image: String) {
        this.name = name
        this.email = email
        this.pass = pass
        this.image = image
    }
}
