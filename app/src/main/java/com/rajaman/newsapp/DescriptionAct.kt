package com.rajaman.newsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_description.*

class DescriptionAct : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description)

        val i = intent.extras
        val title = i?.get("Title").toString()
        val image = i?.get("img").toString()
        val desc = i?.get("desc").toString()

       Glide.with(this).load(image).into(DesImageView)
        descTitle.text = title
        descNews.text = desc
    }
}