package com.example.loginpage

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Dashboard : AppCompatActivity() {
    private lateinit var helper: MyDBhelper
    private lateinit var rv: RecyclerView
    private lateinit var search:FloatingActionButton
    private lateinit var mAddFab: FloatingActionButton
    private lateinit var mAddpost: FloatingActionButton
    private lateinit var mViewPost: FloatingActionButton
    private lateinit var viewPost: TextView
    private lateinit var addPost: TextView
    private lateinit var swipe:SwipeRefreshLayout
    private var isAllFabsVisible: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        //set the toolbar title
        supportActionBar?.title = "Dashboard"
        supportActionBar?.subtitle = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
        //datebase
        helper = MyDBhelper(applicationContext)
        val db = helper.readableDatabase
        val Text: TextView = findViewById(R.id.textview)
        val ID: String? = intent.getStringExtra("ID")
        val rs = db.rawQuery("SELECT * FROM USERS WHERE USERID = $ID", null)
        if (rs.moveToNext()) {
            Text.text = "Welcome back, " + rs.getString(1)

        }
        //date at the top corner
        rs.close()
        db.close()
        viewPost()
        //swipe register ID
        swipe=findViewById(R.id.swiperefresh)
        //register the FAB with their ID
        search=findViewById(R.id.search_fab)
        mAddFab = findViewById(R.id.add_fab)
        mAddpost = findViewById(R.id.add_post_fab)
        mViewPost = findViewById(R.id.view_post_fab)
        //register the text view with their ID
        viewPost = findViewById(R.id.viewPost)
        addPost = findViewById(R.id.addPost)
        //set the action to view .gone
        mAddpost.visibility = View.GONE
        mViewPost.visibility = View.GONE

        viewPost.visibility = View.GONE
        addPost.visibility = View.GONE

        //set on click listener
        mAddFab.setOnClickListener {
            if (!isAllFabsVisible) {
                mAddpost.show()
                mViewPost.show()
                viewPost.visibility = View.VISIBLE
                addPost.visibility = View.VISIBLE
                isAllFabsVisible = true
            } else {
                mAddpost.visibility = View.GONE
                mViewPost.visibility = View.GONE
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



        swipe.setOnRefreshListener {
            viewPost()
            swipe.isRefreshing=false
        }

        search.setOnClickListener {
            val i=Intent(this,MainActivity::class.java)
            startActivity(i)
        }





    }

    private fun viewPost() {
        val postList = helper.getPost(this)
        val adapter = PostAdapter(this, postList)
        rv = findViewById(R.id.rv)
        rv.layoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false) as RecyclerView.LayoutManager
        rv.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.account_option, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.options -> gotointent()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun gotointent()
    {
        var i=Intent(this,Account::class.java)
        startActivity(i)
    }
}