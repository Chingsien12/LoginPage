package com.example.loginpage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Dashboard : AppCompatActivity() {
    private lateinit var clock:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val helper = MyDBhelper(applicationContext)
        val db = helper.readableDatabase
        val Text: TextView =findViewById(R.id.textview)
        val ID: String? = intent.getStringExtra("ID")
        val rs = db.rawQuery("SELECT * FROM USERS WHERE USERID = $ID",null)
        //val ID= intent.getStringExtra("ID")
        if(rs.moveToNext())
        {
            Text.text = "Welcome back, "+rs.getString(1)
        }



        //date at the top corner
        val current =LocalDateTime.now()
        clock=findViewById(R.id.clock)
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        val formatted = current.format(formatter)
        clock.text = formatted
        rs.close()
    }
}