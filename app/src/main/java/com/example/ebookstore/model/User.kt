package com.example.ebookstore.model

class User(private var Name:String, private var Email:String,private var Password:String){
    fun getName():String{
        return Name;
    }

    fun getEmail():String{
        return Email
    }
    fun getPassword():String{
        return Password
    }
}
