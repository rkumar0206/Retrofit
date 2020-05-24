package com.example.retrofit

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val jsonPlaceHolderApi= retrofit.create(JsonPlaceHolderApi::class.java)

        val call: Call<List<Post>> = jsonPlaceHolderApi.getPosts()

        call.enqueue(object : Callback<List<Post>> {

            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {

                if(!response.isSuccessful){
                    text_view_result.text = "Code: ${response.code()}"
                    return
                }
                val posts : List<Post>? = response.body()
                posts?.let {

                    for(post in posts){

                        var content = ""
                        content += "ID: ${post.id}\n"
                        content += "User ID: ${post.userId}\n"
                        content += "Title: ${post.title}\n"
                        content+= "Text: ${post.text}\n\n"

                        text_view_result.append(content)
                    }
                }

            }
            override fun onFailure(call: Call<List<Post>>, t: Throwable) {

                text_view_result.text = t.message

            }
        })

    }
}
