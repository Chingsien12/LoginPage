package com.example.loginpage

import android.content.ContentValues
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Patterns
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class Register : AppCompatActivity() {
    private lateinit var register: Button
    private lateinit var name: EditText
    private lateinit var password: EditText
    private lateinit var Cpassword: EditText
    private lateinit var DOB: EditText
    private lateinit var language: EditText
    private lateinit var skill: EditText
    private lateinit var email: EditText
    private lateinit var phone: EditText
    private lateinit var work: EditText
    private lateinit var address: EditText
    private lateinit var loginlink:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        val helper = MyDBhelper(applicationContext)
        val db = helper.readableDatabase

        //button register
        register = findViewById(R.id.Register)
        register.setOnClickListener {
            name = findViewById(R.id.edName)
            password = findViewById(R.id.edPassword)
            Cpassword = findViewById(R.id.edConPassword)
            DOB = findViewById(R.id.DOB)
            language = findViewById(R.id.language)
            skill = findViewById(R.id.skill)
            email = findViewById(R.id.email)
            phone = findViewById(R.id.phone)
            work = findViewById(R.id.History)
            address = findViewById(R.id.address)
            var error: String = ""

            //regex check
            val regexName = """^[a-zA-Z\s]*$""".toRegex()
            val regexPhonenumber = """^0[0-8]\d{8}${'$'}""".toRegex()
            // Allcheck = name.text.toString().isNotEmpty()
            //for checking name if it is null
            if (name.text.toString().isEmpty()) {
                error += "name field must not be empty\n"
                name.setBackgroundResource(R.drawable.error)
            } else {
                if (!regexName.matches(input = name.text.toString())) {
                    error += "name field must only contain character and space\n"
                    name.setBackgroundResource(R.drawable.error)
                } else {
                    name.setBackgroundResource(R.drawable.noerror)
                }
            }
            if (email.text.toString().isEmpty()) {
                error += "Email field must not be empty\n"
                email.setBackgroundResource(R.drawable.error)
            } else {
                if (!Patterns.EMAIL_ADDRESS.matcher(email.text.toString()).matches()) {
                    error += "Email field must contain ****@****.com\n"
                    email.setBackgroundResource(R.drawable.error)
                } else {
                    val list = listOf<String>(email.text.toString()).toTypedArray()
                    val rs = db.rawQuery("SELECT * FROM USERS WHERE EMAIL = ?",list)
                    if (rs.moveToNext())
                    {
                        error += "Email has already taken\n"
                        email.setBackgroundResource(R.drawable.error)
                    }else
                    email.setBackgroundResource(R.drawable.noerror)
                }
            }


//
//
            if (password.text.toString() == "") {
                error += "Password must not be empty\n"
                password.setBackgroundResource(R.drawable.error)
            } else {
                password.setBackgroundResource(R.drawable.noerror)
            }

            if (Cpassword.text.toString() == "") {
                error += "Confirm Password must not be empty\n"
                Cpassword.setBackgroundResource(R.drawable.error)
            } else {
                if ((password.text.toString().trim() != Cpassword.text.toString().trim())) {
                    error += "Password and confirm password must be the same\n"
                    password.setBackgroundResource(R.drawable.error)
                } else {
                    Cpassword.setBackgroundResource(R.drawable.noerror)
                }
            }

            //validate the skill and language
            if (skill.text.toString() == "") {
                error += "Skill field must not be empty\n"
                skill.setBackgroundResource(R.drawable.error)
            } else {
                skill.setBackgroundResource(R.drawable.noerror)
            }

            if (language.text.toString() == "") {
                error += "Language field must not be empty\n"
                language.setBackgroundResource(R.drawable.error)
            } else {
                language.setBackgroundResource(R.drawable.noerror)
            }

            //DOB
            if (DOB.text.toString() == "") {
                error += "Date of birth field must not be empty\n"
                DOB.setBackgroundResource(R.drawable.error)
            } else {
                DOB.setBackgroundResource(R.drawable.noerror)
            }
            if (!regexPhonenumber.matches(input = phone.text.toString())) {
                error += "Phone number must not be empty and follow this format 01234567890\n"
                phone.setBackgroundResource(R.drawable.error)
            } else {
                phone.setBackgroundResource(R.drawable.noerror)
            }
//address
            if (address.text.toString() == "") {
                error += "Address field must not be empty\n"
                address.setBackgroundResource(R.drawable.error)
            } else {
                address.setBackgroundResource(R.drawable.noerror)
            }


            if (error == "") {
                val layout: View = layoutInflater.inflate(R.layout.custom_toast_form, null)
                Toast(this).apply {
                    duration = Toast.LENGTH_SHORT
                    setGravity(Gravity.CENTER, 0, 0)
                    view = layout
                }.show()
                //insert into the database
                var values = ContentValues()
                values.put("UNAME", name.text.toString())
                values.put("PWD", password.text.toString())
                values.put("DOB",DOB.text.toString())
                values.put("SKILL",skill.text.toString())
                values.put("LANGUAGE",language.text.toString())
                values.put("EMAIL",email.text.toString())
                values.put("PHONE",phone.text.toString())
                values.put("ADDRESS",address.text.toString())
                values.put("HISTORY",work.text.toString())
                db.insert("USERS", null, values)

                reset()
            } else {
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
            }

        }

        //link to login page
        loginlink=findViewById(R.id.Login)
        loginlink.setOnClickListener{
            val intent = Intent(this, login::class.java)
            startActivity(intent)
        }
    }

    private fun reset() {
        name.setText("")
        password.setText("")
        Cpassword.setText("")
        DOB.setText("")
        skill.setText("")
        language.setText("")
        email.setText("")
        phone.setText("")
        work.setText("")
        address.setText("")
    }
}