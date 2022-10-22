package com.example.loginpage

import android.app.DatePickerDialog
import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.Gravity
import android.view.View
import android.widget.*
import com.example.loginpage.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.li_post.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class editPage : AppCompatActivity() {
    private var userid: String? = ""
    private var pid: String? = ""
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
    private lateinit var update: Button
    private lateinit var applicationSpinnerData: String
    private lateinit var contractSpinnerData: String
    private lateinit var stateSpinnerData: String
    private lateinit var date: String
    private lateinit var binding: ActivityMainBinding
    private lateinit var currentDate: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_page)
        //set up the title
        supportActionBar?.title = "Update page"
        supportActionBar?.subtitle = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
        //define the regex Phone
        val phonePattern = """^0[0-8]\d{8}${'$'}""".toRegex()
        //set up the ID
        update=findViewById(R.id.update)
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
        //set up the intent
        userid = intent.getStringExtra("ID")
        pid = intent.getStringExtra("PID")
        //set up the current date
        currentDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
        //setup binding
        binding = ActivityMainBinding.inflate(layoutInflater)

        cdate.setOnClickListener {

            setDate()
        }
        //set up the database
        val helper = MyDBhelper(applicationContext)
        //val db = helper.writableDatabase


        val holder: Post = helper.getSpecificPostUpdate(this, pid)
        //set resource get string
        val stateString = resources.getStringArray(R.array.state)
        val applicationString = resources.getStringArray(R.array.time)
        val contractString = resources.getStringArray(R.array.contract)
        //APPLICAITON
        when (holder.application) {
            "Part Time" -> application.setSelection(2)
            "Full Time" -> application.setSelection(1)
        }
        //CONTRACT
        when (holder.contract) {
            "On-Going" -> contract.setSelection(1)
            "Fixed Time" -> contract.setSelection(2)
        }
        //split the string
        val delim = "."
        val list = holder.location.split(delim)
        //Toast.makeText(this, list[1], Toast.LENGTH_SHORT).show()
        when (list[1]) {
            "VIC" -> state.setSelection(1)
            "QLD" -> state.setSelection(2)
            "NSW" -> state.setSelection(3)
            "TAS" -> state.setSelection(4)
            "NT" -> state.setSelection(5)
            "WA" -> state.setSelection(6)
            "SA" -> state.setSelection(7)
        }
        //Toast.makeText(this, list[0], Toast.LENGTH_SHORT).show()
        //set the text to the Edit text
        title.setText(holder.title)
        desc.setText(holder.description)
        email.setText(holder.email)
        cdate.setText(holder.date)
        phone.setText(holder.phone)
        postcode.setText(list[2])
        street.setText(list[0])


        //setup the array string
        val stateStringDate=resources.getStringArray(R.array.state)
        val applicationStringData=resources.getStringArray(R.array.time)
        val contractStringData=resources.getStringArray(R.array.contract)
        //set up the spinner data
        application.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                applicationSpinnerData = applicationStringData[p2]
                //Toast.makeText(this@AddPost,applicationSpinnerData , Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }

        //set up the contract data
        contract.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                contractSpinnerData = contractStringData[p2]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
        //set up the state data
        state.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                stateSpinnerData = stateStringDate[p2]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        //validation
        update.setOnClickListener {
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
            }else
            {
                val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
                val inputDate: LocalDate = LocalDate.parse(cdate.text, formatter)
                val systemDate: LocalDate = LocalDate.parse(currentDate, formatter)
                when {
                    inputDate.isAfter(systemDate) -> {
                        cdate.error = null
                    }
                    inputDate.isBefore(systemDate) -> {
                        allCheck++
                        cdate.error = "You must not enter the past date!"
                    }

                }
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
                            postcode.error=null
                        } else {
                            allCheck++
                            postcode.error = "For VIC must start with 3xxx or 8xxx"
                        }

                        "NSW" -> if(postcode.text[0].toString() == "1" || postcode.text[0].toString() == "2")
                        {
                            postcode.error=null
                        }else
                        {
                            allCheck++
                            postcode.error = "For NSW must start with 1xxx or 2xxx"
                        }

                        "QLD" -> if(postcode.text[0].toString() == "4" || postcode.text[0].toString() == "9")
                        {
                            postcode.error=null
                        }else
                        {
                            allCheck++
                            postcode.error = "For QLD must start with 4xxx or 9xxx"
                        }

                        "SA"->if(postcode.text[0].toString() == "5")
                        {
                            postcode.error=null
                        }else
                        {
                            allCheck++
                            postcode.error = "For SA must start with 5xxx"
                        }
                        "WA" ->if(postcode.text[0].toString() == "6")
                        {
                            postcode.error=null
                        }else
                        {
                            allCheck++
                            postcode.error = "For NSW must start with 6xxx"
                        }
                        "TAS"->if(postcode.text[0].toString() == "7")
                        {
                            postcode.error=null
                        }else
                        {
                            allCheck++
                            postcode.error = "For NSW must start with 7xxx"
                        }

                        "NT"->if(postcode.text[0].toString() == "0" )
                        {
                            postcode.error=null
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
                val layout: View = layoutInflater.inflate(R.layout.custom_toast_update, null)
                Toast(this).apply {
                    duration = Toast.LENGTH_SHORT
                    setGravity(Gravity.CENTER, 0, 0)
                    view = layout
                }.show()
                //CONCAT THE street address
                var conStreet:String=street.text.toString()+"."+stateSpinnerData+"."+postcode.text.toString()
                //Toast.makeText(this@AddPost, conStreet, Toast.LENGTH_SHORT).show()
                //update into the database
                //Toast.makeText(this, userid, Toast.LENGTH_SHORT).show()
                var contentValues=ContentValues()
                    contentValues.put("EMAIL",email.text.toString())
                    contentValues.put("TITLE",title.text.toString())
                    contentValues.put("DES",desc.text.toString())
                    contentValues.put("LOCATION",conStreet)
                    contentValues.put("APPLICATION",applicationSpinnerData)
                    contentValues.put("CONTRACT",contractSpinnerData)
                    contentValues.put("DATE",cdate.text.toString())
                    contentValues.put("PHONE",phone.text.toString())
                val db=MyDBhelper(applicationContext).writableDatabase

                db.update("POST",contentValues, "PID="+holder.pid,null)

            } else if (error != "") {
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
            }

        }
    }

    //set date
    private fun setDate()
    {
        val datePicker= Calendar.getInstance() //get time form the calender
        val date= DatePickerDialog.OnDateSetListener{
                view:DatePicker?,year:Int,month:Int,dayOfMonth:Int ->
            datePicker[Calendar.YEAR]=year
            datePicker[Calendar.MONTH]=month
            datePicker[Calendar.DAY_OF_MONTH]=dayOfMonth
            //crete the format for the date
            val dateformat="dd-MM-yyyy"
            val simpleDateFormat= SimpleDateFormat(dateformat, Locale.getDefault())
            date=simpleDateFormat.format(datePicker.time)
            //Toast.makeText(this,date,Toast.LENGTH_LONG).show()
            cdate.setText(date)
            //binding.textView.text=simpleDateFormat.format(datePicker.time)
        }
        DatePickerDialog(
            this@editPage,date,
            datePicker[Calendar.YEAR],
            datePicker[Calendar.MONTH],
            datePicker[Calendar.DAY_OF_MONTH]
        ).show()
    }
}