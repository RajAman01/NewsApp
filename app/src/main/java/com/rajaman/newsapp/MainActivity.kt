package com.rajaman.newsapp

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.card.view.*
import org.json.JSONArray
import org.json.JSONObject
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class MainActivity : AppCompatActivity() {

    val url =
        "https://newsapi.org/v2/top-headlines?country=in&category=business&apiKey=be049a822c124902b57b8456b2e49697"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val queue = Volley.newRequestQueue(this)
        val jsonOb = JsonObjectRequest(Request.Method.GET, url, null,
            Response.Listener {response ->

                val arrOfNews = response.getJSONArray("articles")
                mainListView.adapter = NewsAdapter(this, arrOfNews)
            },
            Response.ErrorListener {
                Toast.makeText(this, "Network Not Available", Toast.LENGTH_LONG).show()
            })
        queue.add(jsonOb)
    }

    inner class NewsAdapter(context: Context, arrOfNews :JSONArray) :BaseAdapter(){
        val  newsArr = arrOfNews
        val mContext = context

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val news = newsArr[position] as JSONObject
            val layoutInflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val newsCard = layoutInflater.inflate(R.layout.card, null, true)
            val author = news.getString("author")
            val title = news.getString("title")
            val imgPath = news.get("urlToImage").toString()
            val desc = news.get("description").toString()

            newsCard.titleTextView.text = author
            newsCard.descriptionTextView.text = title
            Glide.with(mContext)
                .load(imgPath)
                .into(newsCard.imageView)

            newsCard.setOnClickListener {
                Toast.makeText(mContext, author, Toast.LENGTH_SHORT).show()
                val intent = Intent(mContext,DescriptionAct::class.java)
                intent.putExtra("Title",title)
                intent.putExtra("img",imgPath)
                intent.putExtra("desc",desc)
                startActivity(intent)
            }

            return newsCard
        }

        override fun getItem(position: Int): Any {
            return newsArr[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return newsArr.length()
        }

    }
}
