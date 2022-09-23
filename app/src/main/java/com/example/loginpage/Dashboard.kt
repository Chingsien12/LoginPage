package com.example.loginpage

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Dashboard : AppCompatActivity() {
    private lateinit var clock: TextView
    private lateinit var helper: MyDBhelper
    private lateinit var rv: RecyclerView
    private lateinit var mAddFab: FloatingActionButton
    private lateinit var mAddpost: FloatingActionButton
    private lateinit var mViewPost: FloatingActionButton
    private lateinit var mRefresh:FloatingActionButton
    private lateinit var restart:TextView
    private lateinit var viewPost: TextView
    private lateinit var addPost: TextView
    private var isAllFabsVisible: Boolean = false
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

        //register the FAB with their ID
        mAddFab = findViewById(R.id.add_fab)
        mAddpost = findViewById(R.id.add_post_fab)
        mViewPost = findViewById(R.id.view_post_fab)
        mRefresh=findViewById(R.id.restart_fab)
        //register the text view with their ID
        restart=findViewById(R.id.restart)
        viewPost = findViewById(R.id.viewPost)
        addPost = findViewById(R.id.addPost)
        //set the action to view .gone
        mAddpost.visibility = View.GONE
        mViewPost.visibility = View.GONE
        mRefresh.visibility=View.GONE
        restart.visibility=View.GONE
        viewPost.visibility = View.GONE
        addPost.visibility = View.GONE

        //set on click listener
        mAddFab.setOnClickListener {
            if (!isAllFabsVisible) {
                mAddpost.show()
                mViewPost.show()
                mRefresh.show()
                restart.visibility=View.VISIBLE
                viewPost.visibility = View.VISIBLE
                addPost.visibility = View.VISIBLE
                isAllFabsVisible = true
            } else {
                mAddpost.visibility = View.GONE
                mViewPost.visibility = View.GONE
                mRefresh.visibility=View.GONE
                restart.visibility=View.GONE
                viewPost.visibility = View.GONE
                addPost.visibility = View.GONE
                isAllFabsVisible = false
            }

        }

        //set another onclicklistener
        mViewPost.setOnClickListener {
            val intent = Intent(this, ViewOwnPost::class.java).apply {
                putExtra("ID", ID)
            }
            startActivity(intent)
        }

        mAddpost.setOnClickListener {
            val intent = Intent(this, AddPost::class.java).apply {
                putExtra("ID", ID)
            }
            startActivity(intent)
        }

        mRefresh.setOnClickListener {
            val mIntent = intent
            finish()
            startActivity(mIntent)
        }
    }

    private fun getTime() {
        val current = LocalDateTime.now()
        clock = findViewById(R.id.clock)
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        val formatted = current.format(formatter)
        clock.text = formatted

    }

    private fun viewPost() {
        val postList = helper.getPost(this)
        val adapter = PostAdapter(this, postList)
        rv = findViewById(R.id.rv)
        rv.layoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false) as RecyclerView.LayoutManager
        rv.adapter = adapter
    }

}