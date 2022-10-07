package com.example.loginpage

import android.app.DatePickerDialog
import android.content.ContentValues
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.EditText
import android.util.Patterns
import android.view.Gravity
import android.view.View
import android.widget.*
import java.util.*
import androidx.appcompat.app.AppCompatActivity
import com.example.loginpage.databinding.ActivityMainBinding
import java.text.SimpleDateFormat


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
    private lateinit var binding: ActivityMainBinding
    private lateinit var date:String
    //private lateinit var calender:ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        val helper = MyDBhelper(applicationContext)
        val db = helper.readableDatabase
        //register ID
        DOB = findViewById(R.id.DOB)
        //DOB click
        DOB.setOnClickListener {
            setDate()
        }
        //button register
        register = findViewById(R.id.Register)
        register.setOnClickListener {
            name = findViewById(R.id.edName)
            password = findViewById(R.id.edPassword)
            Cpassword = findViewById(R.id.edConPassword)

            language = findViewById(R.id.language)
            skill = findViewById(R.id.skill)
            email = findViewById(R.id.email)
            phone = findViewById(R.id.phone)
            work = findViewById(R.id.History)
            address = findViewById(R.id.address)

            //using binding
            binding=ActivityMainBinding.inflate(layoutInflater)



            //calender=findViewById(R.id.calender)
            var error: String = ""

            //regex check
            val regexName = """^[a-zA-Z\s]*$""".toRegex()
            val regexPhonenumber = """^0[0-8]\d{8}${'$'}""".toRegex()
            // Allcheck = name.text.toString().isNotEmpty()
            //for checking name if it is null
            if (name.text.toString().isEmpty()) {
                error += "name field must not be empty\n"
                name.error=getString(R.string.erronameEmpty)
            } else {
                name.error=null
                if (!regexName.matches(input = name.text.toString())) {
                    error += "name field must only contain character and space\n"
                    name.error=getString(R.string.erronamePattern)
                } else {
                    name.error=null
                }
            }

                //checking email
            if (email.text.toString().isEmpty()) {
                error += "Email field must not be empty\n"
                email.error=getString(R.string.erroemailEmpty)
            } else {
                if (!Patterns.EMAIL_ADDRESS.matcher(email.text.toString()).matches()) {
                    error += "Email field must contain ****@****.com\n"
                    email.error=getString(R.string.errorEmailPattern)
                } else {
                    val list = listOf<String>(email.text.toString()).toTypedArray()
                    val rs = db.rawQuery("SELECT * FROM USERS WHERE EMAIL = ?",list)
                    if (rs.moveToNext())
                    {
                        error += "Email has already taken\n"
                        email.error=getString(R.string.errorEmailTaken)
                    }else
                    email.error=null
                }
            }


//
            //checking the password
            if (password.text.toString() == "") {
                error += "Password must not be empty\n"
                password.error=getString(R.string.errorPassEmpty)
            } else {
                password.error=null
            }

            if (Cpassword.text.toString() == "") {
                error += "Confirm Password must not be empty\n"
                Cpassword.error=getString(R.string.errorCPassEmpty)
            } else {
                if ((password.text.toString().trim() != Cpassword.text.toString().trim())) {
                    error += "Password and confirm password must be the same\n"
                    Cpassword.error=getString(R.string.errorPassSame)
                } else {
                    Cpassword.error=null
                }
            }

            //validate the skill and language
            if (skill.text.toString() == "") {
                error += "Skill field must not be empty\n"
                skill.error=getString(R.string.errorSkillEmpty)
            } else {
                skill.error=null
            }

            if (language.text.toString() == "") {
                error += "Language field must not be empty\n"
                language.error=getString(R.string.errorLanguageEmpty)
            } else {
                language.error=null
            }

            //DOB
            if (DOB.text.toString() == "") {
                error += "Date of birth field must not be empty\n"
                DOB.error=getString(R.string.errorDOB)
            } else {
                DOB.error=null
            }
            if (!regexPhonenumber.matches(input = phone.text.toString())) {
                error += "Phone number must not be empty and follow this format 01234567890\n"
                phone.error=getString(R.string.errorPhonePattern)
            } else {
                phone.error=null
            }
//address
            if (address.text.toString() == "") {
                error += "Address field must not be empty\n"
                address.error=getString(R.string.errorAddressEmpty)
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
                Toast.makeText(this, "Please check the error!", Toast.LENGTH_SHORT).show()
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


    private fun setDate()
    {
        val datePicker=Calendar.getInstance() //get time form the calender
        val date= DatePickerDialog.OnDateSetListener{
                view:DatePicker?,year:Int,month:Int,dayOfMonth:Int ->
            datePicker[Calendar.YEAR]=year
            datePicker[Calendar.MONTH]=month
            datePicker[Calendar.DAY_OF_MONTH]=dayOfMonth
            //crete the format for the date
            val dateformat="dd-MM-yyyy"
            val simpleDateFormat= SimpleDateFormat(dateformat,Locale.getDefault())
            date=simpleDateFormat.format(datePicker.time)
            //Toast.makeText(this,date,Toast.LENGTH_LONG).show()
            DOB.setText(date)
            //binding.textView.text=simpleDateFormat.format(datePicker.time)
        }
        DatePickerDialog(
            this@Register,date,
            datePicker[Calendar.YEAR],
            datePicker[Calendar.MONTH],
            datePicker[Calendar.DAY_OF_MONTH]
        ).show()
    }
}