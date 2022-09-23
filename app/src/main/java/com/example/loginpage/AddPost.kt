package com.example.loginpage

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.Gravity
import android.view.View
import android.widget.*
import kotlin.properties.Delegates

class AddPost : AppCompatActivity() {
    private lateinit var title: EditText
    private lateinit var desc: EditText
    private lateinit var email: EditText
    private lateinit var cdate: EditText
    private lateinit var phone: EditText
    private lateinit var application: Spinner
    private lateinit var contract: Spinner
    private lateinit var street: EditText
    private lateinit var state: Spinner
    private lateinit var postcode: EditText
    private lateinit var post: Button
    private lateinit var reset: Button
    private lateinit var applicationSpinnerData: String
    private lateinit var contractSpinnerData: String
    private lateinit var stateSpinnerData: String
    private  var id:String?=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_post)
        //set up database
        val helper = MyDBhelper(applicationContext)
        val db = helper.readableDatabase
        //set resource get string
        val stateString = resources.getStringArray(R.array.state)
        val applicationString = resources.getStringArray(R.array.time)
        val contractString = resources.getStringArray(R.array.contract)
        //set up button
        reset = findViewById(R.id.reset)
        post = findViewById(R.id.post)
        //get the intent
        id=intent.getStringExtra("ID")
        //Toast.makeText(this@AddPost, id.toString(), Toast.LENGTH_SHORT).show()
        //set on click listener on reset

        reset.setOnClickListener {
            reset()
        }
        //define the regex Phone
        val phonePattern = """^0[0-8]\d{8}${'$'}""".toRegex()
        //register with their ID
        title = findViewById(R.id.Title)
        desc = findViewById(R.id.Desc)
        email = findViewById(R.id.email)
        cdate = findViewById(R.id.Cdate)
        phone = findViewById(R.id.phone)
        application = findViewById(R.id.time)
        contract = findViewById(R.id.contract)
        street = findViewById(R.id.Street)
        state = findViewById(R.id.state)
        postcode = findViewById(R.id.postcode)

        //set up the spinner data

        application.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                applicationSpinnerData = applicationString[p2]
                //Toast.makeText(this@AddPost,applicationSpinnerData , Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }

        //set up the contract data
        contract.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                contractSpinnerData = contractString[p2]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
        //set up the state data
        state.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                stateSpinnerData = stateString[p2]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        //set another btton to post the job
        post.setOnClickListener {
            //Toast.makeText(this@AddPost,applicationSpinnerData , Toast.LENGTH_SHORT).show()
            //set up point to check the validation
            var allCheck = 0
            var error: String = ""
            //checking if the title is empty or not
            if (title.text.toString().isEmpty()) {
                allCheck++
                title.error = getString(R.string.errorTitle)
            }
            //checking the desc
            if (desc.text.toString().isEmpty()) {
                allCheck++
                desc.error = getString(R.string.errorDesc)
            }
            //checking the email
            if (email.text.toString().isEmpty()) {
                allCheck++
                email.error = getString(R.string.errorEmail)
            } else {
                if (!Patterns.EMAIL_ADDRESS.matcher(email.text.toString()).matches()) {
                    allCheck++
                    email.error = getString(R.string.errorEmailPattern)
                }
            }

            //cheking date
            if (cdate.text.toString().isEmpty()) {
                allCheck++
                cdate.error = getString(R.string.errordate)
            }
            //checking the phone
            if (phone.text.toString().isEmpty()) {
                allCheck++
                phone.error = getString(R.string.errorPhoneEmpty)
            } else {
                if (!phonePattern.matches(input = phone.text.toString())) {
                    allCheck++
                    phone.error = getString(R.string.errorPhonePattern)
                }
            }

            //checking the street
            if (street.text.toString().isEmpty()) {
                allCheck++
                street.error = getString(R.string.errorStreet)
            }

            //checking the postcode
            if (postcode.text.toString().isEmpty()) {
                allCheck++
                postcode.error = getString(R.string.errorPostcode)
            }


            //checking the dropdown button
            if (applicationSpinnerData == ".....") {
                error += "You must select one of the Application time!\n"
            }
            if (contractSpinnerData == ".....") {
                error += "You must select on of Contract!\n"
            }
            if (stateSpinnerData == ".....") {
                error += "You must select on of the state!"
            } else {
                if(postcode.text.toString().isNotEmpty())
                {
                    when (stateSpinnerData) {
                        "VIC" -> if (postcode.text[0].toString() == "3" || postcode.text[0].toString() == "8") {

                        } else {
                            allCheck++
                            postcode.error = "For VIC must start with 3xxx or 8xxx"
                        }

                        "NSW" -> if(postcode.text[0].toString() == "1" || postcode.text[0].toString() == "2")
                        {

                        }else
                        {
                            allCheck++
                            postcode.error = "For NSW must start with 1xxx or 2xxx"
                        }

                        "QLD" -> if(postcode.text[0].toString() == "4" || postcode.text[0].toString() == "9")
                        {

                        }else
                        {
                            allCheck++
                            postcode.error = "For QLD must start with 4xxx or 9xxx"
                        }

                        "SA"->if(postcode.text[0].toString() == "5")
                        {

                        }else
                        {
                            allCheck++
                            postcode.error = "For SA must start with 5xxx"
                        }
                        "WA" ->if(postcode.text[0].toString() == "6")
                        {

                        }else
                        {
                            allCheck++
                            postcode.error = "For NSW must start with 6xxx"
                        }
                        "TAS"->if(postcode.text[0].toString() == "7")
                        {

                        }else
                        {
                            allCheck++
                            postcode.error = "For NSW must start with 7xxx"
                        }

                        "NT"->if(postcode.text[0].toString() == "0" )
                        {

                        }else
                        {
                            allCheck++
                            postcode.error = "For NSW must start with 0xxx"
                        }
                    }
                }else
                {
                    postcode.error = getString(R.string.errorPostcode)
                }

            }

            if (allCheck == 0 && error == "") {
                val layout: View = layoutInflater.inflate(R.layout.custom_toast_form, null)
                Toast(this).apply {
                    duration = Toast.LENGTH_SHORT
                    setGravity(Gravity.CENTER, 0, 0)
                    view = layout
                }.show()
                //CONCAT THE street address
                var conStreet:String=street.text.toString()+"."+stateSpinnerData+"."+postcode.text.toString()
                //Toast.makeText(this@AddPost, conStreet, Toast.LENGTH_SHORT).show()
                //insert into the database
                val values = ContentValues()
                values.put("USERID", id)
                values.put("EMAIL",email.text.toString())
                values.put("TITLE",title.text.toString())
                values.put("DES",desc.text.toString())
                values.put("LOCATION",conStreet)
                values.put("APPLICATION",applicationSpinnerData)
                values.put("CONTRACT",contractSpinnerData)
                values.put("DATE",cdate.text.toString())
                values.put("PHONE",phone.text.toString())
                db.insert("POST", null, values)


                reset()
            } else if (error != "") {
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
            }

        }


    }


    private fun reset() {
        title.setText("")
        desc.setText("")
        email.setText("")
        cdate.setText("")
        phone.setText("")
        postcode.setText("")
        application.setSelection(0)
        contract.setSelection(0)
        state.setSelection(0)
        street.setText("")

    }
}