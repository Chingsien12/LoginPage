package com.example.loginpage

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class Account : AppCompatActivity() {
    private lateinit var id:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)
        val sharedPref = this.getSharedPreferences("myid", Context.MODE_PRIVATE)
        val ID: String = sharedPref.getString("ID", "0").toString()
        id=findViewById(R.id.idshow)
        id.text=ID
        val helper = MyDBhelper(applicationContext)
        val holder: ArrayList<account> = helper.getUser(this, ID)
    }
}