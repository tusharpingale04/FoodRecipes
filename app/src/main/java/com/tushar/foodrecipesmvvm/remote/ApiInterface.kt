package com.tushar.foodrecipesmvvm.remote

import com.tushar.foodrecipesmvvm.model.Recipes
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    //https://www.food2fork.com/api/search?key=YOUR_API_KEY&page=2
    companion object {
        const val BASE_URL:String = "https://www.food2fork.com/api/"
        const val API_KEY:String = "b9379e8013f47f89e5a7f89b7212f9ac"
    }

    @GET("search")
    fun getRecipesAsync(@Query("page") page:String) : Deferred<Recipes>

}