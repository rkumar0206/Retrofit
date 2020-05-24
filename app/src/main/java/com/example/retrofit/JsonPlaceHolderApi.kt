package com.example.retrofit

import retrofit2.Call
import retrofit2.http.*

interface JsonPlaceHolderApi {

    /**
     * First Method: For Getting the post only for all userId we use the below first method
     * Second Method: And For getting the post for specific userId we use Query
     * Third Method: Adding some More Queries in third method
     * Fourth Method: adding multiple queries for more than one user userId
     */

    @GET("posts")
    fun getPosts(): Call<List<Post>>

    //@Query will automatically put ? sign and = sign like: /posts?userId=1
    @GET("posts")
    fun getPosts(@Query("userId") userId: Int): Call<List<Post>>

    @GET("posts")
    fun getPosts(
        @Query("userId") userId: Int,
        @Query("_sort") sort: String,
        @Query("_order") order: String

    ): Call<List<Post>>

    @GET("posts")
    fun getPosts(
        @QueryMap parameters: Map<String, String>
    ): Call<List<Post>>


    /**
     * First Method: For Getting the comments only for id 2
     * Second Method: And For getting the comments for any id
     * Third Method: Getting the comments from url
     */
    @GET("posts/2/comments")
    fun getComments(): Call<List<Comment>>

    @GET("posts/{id}/comments")
    fun getComments(@Path("id") postId: Int): Call<List<Comment>>

    @GET
    fun getComments(@Url url: String): Call<List<Comment>>
}