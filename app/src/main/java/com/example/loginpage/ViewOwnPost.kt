package com.example.loginpage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ViewOwnPost : AppCompatActivity() {
    private lateinit var id:TextView
    private lateinit var helper: MyDBhelper
    private lateinit var rV: RecyclerView
    private var userid: String?=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_own_post)
        userid = intent.getStringExtra("ID")
        //id.text=userid
        helper = MyDBhelper(applicationContext)
        val db = helper.readableDatabase

        db.close()
        viewPost()

    }

    private fun viewPost() {
        val postList = helper.getSpecificPost(this,userid)
        val adapter = PostAdapterUser(this, postList)
        rV = findViewById(R.id.Rv)
        rV.layoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false) as RecyclerView.LayoutManager
        rV.adapter = adapter
    }
}