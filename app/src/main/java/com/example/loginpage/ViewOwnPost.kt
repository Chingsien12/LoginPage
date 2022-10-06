package com.example.loginpage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ViewOwnPost : AppCompatActivity() {
    private lateinit var helper: MyDBhelper
    private lateinit var rV: RecyclerView
    private var userid: String? = ""
    private lateinit var close: ImageView

    //private lateinit var tool:FloatingActionButton
    private lateinit var delete: FloatingActionButton
    private lateinit var edit: FloatingActionButton

    private lateinit var textID: TextView
    private lateinit var deleteText: TextView
    private lateinit var editText: TextView
    private var isAllFabsVisible: Boolean = false
    private lateinit var swipe: SwipeRefreshLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_own_post)
        //title action bar
        supportActionBar?.title = "Your own post"
        supportActionBar?.subtitle =
            LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
        //set up intent
        userid = intent.getStringExtra("ID")
        //register to the ID
        //tool=findViewById(R.id.tool)
        //delete=findViewById(R.id.delete_fab)
        //edit=findViewById(R.id.edit_fab)
        textID = findViewById(R.id.ID)
        //deleteText=findViewById(R.id.deleteText)
        //editText=findViewById(R.id.editText)
        swipe = findViewById(R.id.swiperefresh)
        //id.text=userid
        helper = MyDBhelper(applicationContext)
        val db = helper.readableDatabase

        db.close()
        viewPost()
        //hide the image

        //hide all of button
//        delete.visibility= View.GONE
//        edit.visibility=View.GONE
//
//
//        editText.visibility=View.GONE
//        deleteText.visibility=View.GONE

//        tool.setOnClickListener {
//            if (!isAllFabsVisible) {
//                edit.show()
//                delete.show()
//                editText.visibility = View.VISIBLE
//                deleteText.visibility = View.VISIBLE
//                isAllFabsVisible = true
//            } else {
//                edit.visibility = View.GONE
//                delete.visibility=View.GONE
//                editText.visibility = View.GONE
//                deleteText.visibility = View.GONE
//                isAllFabsVisible = false
//            }
//        }
//        edit.setOnClickListener {
//            if(textID.text=="")
//            {
//                Toast.makeText(this, "Please click on on of the post!", Toast.LENGTH_SHORT).show()
//            }else
//            {
//                val i=Intent(this,editPage::class.java).apply {
//                    putExtra("PID",textID.text.toString())
//                    putExtra("ID",userid)
//                }
//                startActivity(i)
//            }
//        }
//
//
//        delete.setOnClickListener {
//            if(textID.text=="")
//            {
//                Toast.makeText(this, "Please click on on of the post!", Toast.LENGTH_SHORT).show()
//            }else
//            {
//
//                val builder=AlertDialog.Builder(this)
//                builder.setMessage("Are you sure want to delete this data?")
//                builder.setCancelable(true)
//                builder.setPositiveButton("Yes"){dialog,_->
//                    dialog.dismiss()
//                    MyDBhelper(applicationContext).deleteData(textID.text.toString())
//                    viewPost()
//                    textID.text=""
//                }
//                builder.setNegativeButton("No"){dialog,_->
//                    dialog.dismiss()
//
//                }
//                val alert=builder.create()
//                alert.show()
//                //db.delete()
//            }
//        }
        swipe.setOnRefreshListener {
            viewPost()
            swipe.isRefreshing = false
            textID.text = ""

        }
    }

    private fun viewPost() {
        val postList = helper.getSpecificPost(this, userid)
        val adapter = PostAdapterUser(this, postList)
        rV = findViewById(R.id.Rv)
        rV.layoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rV.adapter = adapter

        adapter.setOnclickItem {
            textID.text = it.pid.toString()

            val dialogButton = BottomSheetDialog(this)
            val view = layoutInflater.inflate(R.layout.dialog_layout, null)
            edit = view.findViewById(R.id.edit_fab)
            delete = view.findViewById(R.id.delete_fab)
            close = view.findViewById(R.id.close)
            dialogButton.setContentView(view)
            dialogButton.show()
            close.setOnClickListener {
                dialogButton.dismiss()
            }

            edit.setOnClickListener {
                val i = Intent(this, editPage::class.java).apply {
                    putExtra("PID", textID.text.toString())
                    putExtra("ID", userid)
                }
                startActivity(i)
                dialogButton.dismiss()
            }
            delete.setOnClickListener {
                val builder = AlertDialog.Builder(this)
                builder.setMessage("Are you sure want to delete this data?")
                builder.setCancelable(true)
                builder.setPositiveButton("Yes") { dialog, _ ->
                    dialog.dismiss()
                    MyDBhelper(applicationContext).deleteData(textID.text.toString())
                    viewPost()
                    textID.text = ""
                    dialogButton.dismiss()
                }
                builder.setNegativeButton("No") { dialog, _ ->
                    dialog.dismiss()

                }
                val alert = builder.create()
                alert.show()
            }
        }
    }
}