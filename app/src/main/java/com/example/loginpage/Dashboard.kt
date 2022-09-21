package com.example.loginpage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Dashboard : AppCompatActivity() {
    private lateinit var clock: TextView
    private lateinit var helper: MyDBhelper
    private lateinit var rv:RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        helper = MyDBhelper(applicationContext)
        val db = helper.readableDatabase
        val Text: TextView = findViewById(R.id.textview)
        val ID: String? = intent.getStringExtra("ID")
        val rs = db.rawQuery("SELECT * FROM USERS WHERE USERID = $ID", null)
        getTime()
        if (rs.moveToNext()) {
            Text.text = "Welcome back, " + rs.getString(1)

        }
        //date at the top corner
        rs.close()
        db.close()
        viewPost()
    }

    private fun getTime() {
        val current = LocalDateTime.now()
        clock = findViewById(R.id.clock)
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        val formatted = current.format(formatter)
        clock.text = formatted

    }

    private fun viewPost() {
        val postList =helper.getPost(this)
        val adapter=PostAdapter(this,postList)
        rv=findViewById(R.id.rv)
        rv.layoutManager=LinearLayoutManager(this,RecyclerView.VERTICAL,false) as RecyclerView.LayoutManager
        rv.adapter=adapter
    }

}