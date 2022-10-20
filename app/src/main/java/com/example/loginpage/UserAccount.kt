package com.example.loginpage

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_user_account.*

class UserAccount : AppCompatActivity() {
    private lateinit var helper: MyDBhelper
    private lateinit var skill:TextView
    private lateinit var language:TextView
    private lateinit var email:TextView
    private lateinit var phone:TextView
    private lateinit var history:TextView
    private lateinit var totalpost:TextView
    private lateinit var userList:account

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_account)

        //datebase
        helper = MyDBhelper(applicationContext)
        //reister ID to the layout
        skill=findViewById(R.id.skill)
        language=findViewById(R.id.language)
        email=findViewById(R.id.email)
        phone=findViewById(R.id.phone)
        history=findViewById(R.id.work)
        totalpost=findViewById(R.id.totalpost)
        //put string
        var prompt:String="Posts"
        //use sharepreference
        val sharedPref = this.getSharedPreferences("myid", Context.MODE_PRIVATE)
        val id: String = sharedPref.getString("ID", "no").toString()
        userList = helper.getUser(this, id)
        var numPost=helper.getUserNumPost(this,id)
        name.text=userList.uname
        skill.text=userList.skill
        language.text=userList.language
        email.text=userList.email
        phone.text=userList.phone
        history.text=userList.history
        totalpost.text=numPost.toString()+" "+prompt
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.logout_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout ->{
                val i= Intent(this,login::class.java);
                startActivity(i)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}