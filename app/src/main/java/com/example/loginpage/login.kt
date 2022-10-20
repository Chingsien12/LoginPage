package com.example.loginpage

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class login : AppCompatActivity() {
    private lateinit var name: EditText
    private lateinit var password: EditText
    private lateinit var BtnLogin: Button
    private lateinit var register: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login1)
        val helper = MyDBhelper(applicationContext)
        val db = helper.readableDatabase

        BtnLogin = findViewById(R.id.Loginbutton)
//  login listener
        BtnLogin.setOnClickListener {

            name = findViewById(R.id.editUsername)
            password = findViewById(R.id.editPassword)
            //password.setBackgroundResource(0)
            //create the array list to check the if the database contail those data or not
            val Userlist =
                listOf<String>(name.text.toString().trim(), password.text.toString()).toTypedArray()
            val result = db.rawQuery("SELECT * FROM USERS WHERE EMAIL = ? AND PWD = ?", Userlist)


            if (result.moveToNext()) {
                val intent = Intent(this, Dashboard::class.java).apply {
                    putExtra("ID", result.getString(0))
//
                }
                val sharePref=this.getSharedPreferences("myid", Context.MODE_PRIVATE)
                with (sharePref.edit()){
                    putString("ID",result.getString(0))
                    apply()
                }
                startActivity(intent)
                Toast.makeText(applicationContext,"lOGIN",Toast.LENGTH_SHORT).show()
            } else {
                val layout: View = layoutInflater.inflate(R.layout.custom_toast, null)
                Toast(this).apply {
                    duration = Toast.LENGTH_SHORT
                    setGravity(Gravity.BOTTOM, 0, 100)
                    view = layout
                }.show()
            }
        }

        //register text click listener
        register = findViewById(R.id.register)

        register.setOnClickListener {
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
            //Toast.makeText(this,"OK",Toast.LENGTH_SHORT).show()
        }

    }
    override fun onBackPressed() {
        //do nothing
    }
}