package com.example.ebookstore

class bookModel(title : String?, CoverImg:Int, desc :String?,rating: String?, genre : String? , authorName :String? , publishedYear : String?) {
    private var title:String
    private var genre:String
    private var CoverImg:Int
    private var desc:String
    private var rating:String
    private var authorName:String
    private var publishedYear:String

    init {
        this.title = title!!
        this.genre = genre!!
        this.CoverImg = CoverImg!!
        this.desc  = desc!!
        this.rating = rating!!
        this.authorName = authorName!!
        this.publishedYear = publishedYear!!
    }
    fun getTitle():String?{
        return title;
    }

    fun getGenre():String?{
        return genre
    }
    fun getCoverImg():Int?{
        return CoverImg
    }
    fun getDesc():String?{
        return desc;
    }

    fun getRating():String?{
        return rating
    }
    fun getAuthorName():String?{
        return authorName
    }
    fun getYear():String?{
        return publishedYear
    }

}