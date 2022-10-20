package com.example.loginpage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {
    private lateinit var helper: MyDBhelper
    private lateinit var searchView: EditText
    private lateinit var searchbtn: Button
    private lateinit var rv: RecyclerView
    private lateinit var state: Spinner
    private lateinit var stateSpinnerData: String
    private lateinit var resetbtn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //set the toolbar title
        supportActionBar?.title = "Search page"
        supportActionBar?.subtitle =
            LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
        //access db
        helper = MyDBhelper(applicationContext)
        //set resource get string
        val stateString = resources.getStringArray(R.array.state)
        //register ID
        state = findViewById(R.id.state)
        searchView = findViewById(R.id.searchView)
        searchbtn = findViewById(R.id.search)
        resetbtn = findViewById(R.id.reset)

        state.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                stateSpinnerData = stateString[p2]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
        searchbtn.setOnClickListener {
            if (searchView.text.toString() == "" && stateSpinnerData == ".....") {
                viewPost()
            } else if (searchView.text.toString() != "" && stateSpinnerData == ".....") {
                viewpostSearch(searchView.text.toString())
            } else {
                viewpostSearchState(searchView.text.toString(), stateSpinnerData)
            }

        }

        reset.setOnClickListener {
            reset()
        }

    }

    private fun viewpostSearch(Title: String) {
        val postList = helper.getPostsearch(this, Title)
        val adapter = PostAdapter(this, postList)
        rv = findViewById(R.id.rv)
        rv.layoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false) as RecyclerView.LayoutManager
        rv.adapter = adapter
    }

    private fun viewPost() {
        val postList = helper.getPost(this)
        val adapter = PostAdapter(this, postList)
        rv = findViewById(R.id.rv)
        rv.layoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false) as RecyclerView.LayoutManager
        rv.adapter = adapter
    }

    private fun viewpostSearchState(Title: String, state: String) {
        val postList = helper.getPostsearchState(this, Title, state)
        val adapter = PostAdapter(this, postList)
        rv = findViewById(R.id.rv)
        rv.layoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false) as RecyclerView.LayoutManager
        rv.adapter = adapter
    }

    private fun reset() {
        searchView.setText("")
        state.setSelection(0)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.account_option, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.options ->{
                val i= Intent(this,UserAccount::class.java);
                startActivity(i)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}