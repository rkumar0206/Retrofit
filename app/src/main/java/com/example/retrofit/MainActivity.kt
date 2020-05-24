package com.example.retrofit

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var jsonPlaceHolderApi: JsonPlaceHolderApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")  // the '/' in last is important
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi::class.java)

        //getPosts()

        //getComments()

        createPost()
    }

    private fun getPosts() {

        val parameters: MutableMap<String, String> = HashMap()
        parameters["userId"] = "1"
        parameters["_sort"] = "id"
        parameters["_order"] = "desc"


        /* val call: Call<List<Post>> = jsonPlaceHolderApi.getPosts(4, "id", "desc")*/

        val call: Call<List<Post>> = jsonPlaceHolderApi.getPosts(parameters)
        call.enqueue(object : Callback<List<Post>> {

            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {

                if (!response.isSuccessful) {
                    text_view_result.text = "Code: ${response.code()}"
                    return
                }
                val posts: List<Post>? = response.body()
                posts?.let {

                    for (post: Post in posts) {

                        var content = ""
                        content += "ID: ${post.id}\n"
                        content += "User ID: ${post.userId}\n"
                        content += "Title: ${post.title}\n"
                        content += "Text: ${post.text}\n\n"

                        text_view_result.append(content)
                    }
                }

            }

            override fun onFailure(call: Call<List<Post>>, t: Throwable) {

                text_view_result.text = t.message

            }
        })

    }

    private fun createPost() {



        /*val post = Post(23, null, "New Title", "New Text")*/
        /*  val call = jsonPlaceHolderApi.createPost(post)*/

        val fields: MutableMap<String, String> = HashMap()
        fields["userId"] = "23"
        fields["title"] = "New Title"
        fields["text"]  = "NewText"

        /* val call = jsonPlaceHolderApi.createPost(23, "New Title", "New Text")*/

        val call = jsonPlaceHolderApi.createPost(fields)

        call.enqueue(object : Callback<Post> {
            override fun onFailure(call: Call<Post>, t: Throwable) {
                text_view_result.text = t.message
            }

            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                if (!response.isSuccessful) {
                    text_view_result.text = "Code: ${response.code()}"
                    return
                }

                val postResponse = response.body()

                postResponse?.let {

                    var content = ""
                    content += "Code: ${response.code()}\n"
                    content += "ID: ${postResponse.id}\n"
                    content += "User ID: ${postResponse.userId}\n"
                    content += "Title: ${postResponse.title}\n"
                    content += "Text: ${postResponse.text}\n\n"

                    text_view_result.append(content)
                }
            }
        })
    }

    private fun getComments() {

        /*val call = jsonPlaceHolderApi.getComments()*/
        /*val call = jsonPlaceHolderApi.getComments(3)*/
        val call = jsonPlaceHolderApi.getComments("posts/2/comments")

        call.enqueue(object : Callback<List<Comment>> {
            override fun onFailure(call: Call<List<Comment>>, t: Throwable) {

                text_view_result.text = t.message
            }

            override fun onResponse(call: Call<List<Comment>>, response: Response<List<Comment>>) {

                if (!response.isSuccessful) {
                    text_view_result.text = "Code: ${response.code()}"
                    return
                }
                val comments: List<Comment>? = response.body()
                comments?.let {

                    for (comment in comments) {

                        var content = ""
                        content += "ID: ${comment.id}\n"
                        content += "Post ID: ${comment.postId}\n"
                        content += "Name: ${comment.name}\n"
                        content += "Email: ${comment.email}\n"
                        content += "Text: ${comment.text}\n\n"

                        text_view_result.append(content)
                    }
                }

            }


        })


    }


}
