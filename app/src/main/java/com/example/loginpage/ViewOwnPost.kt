package com.example.loginpage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ViewOwnPost : AppCompatActivity() {

    private lateinit var helper: MyDBhelper
    private lateinit var rV: RecyclerView
    private var userid: String?=""
    private lateinit var tool:FloatingActionButton
    private lateinit var delete:FloatingActionButton
    private lateinit var edit:FloatingActionButton
    private lateinit var refresh:FloatingActionButton
    private lateinit var textID:TextView
    private lateinit var refreshText:TextView
    private lateinit var deleteText:TextView
    private lateinit var editText:TextView
    private var isAllFabsVisible: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_own_post)
        userid = intent.getStringExtra("ID")
        //register to the ID
        tool=findViewById(R.id.tool)
        delete=findViewById(R.id.delete_fab)
        edit=findViewById(R.id.edit_fab)
        refresh=findViewById(R.id.refresh_fab)
        textID=findViewById(R.id.ID)
        refreshText=findViewById(R.id.refreshText)
        deleteText=findViewById(R.id.deleteText)
        editText=findViewById(R.id.editText)

        //id.text=userid
        helper = MyDBhelper(applicationContext)
        val db = helper.readableDatabase

        db.close()
        viewPost()
        //hide all of button
        delete.visibility= View.GONE
        edit.visibility=View.GONE
        refresh.visibility=View.GONE

        refreshText.visibility=View.GONE
        editText.visibility=View.GONE
        deleteText.visibility=View.GONE

        tool.setOnClickListener {
            if (!isAllFabsVisible) {
                refresh.show()
                edit.show()
                delete.show()
                refreshText.visibility=View.VISIBLE
                editText.visibility = View.VISIBLE
                deleteText.visibility = View.VISIBLE
                isAllFabsVisible = true
            } else {
                refresh.visibility = View.GONE
                edit.visibility = View.GONE
                delete.visibility=View.GONE
                refreshText.visibility=View.GONE
                editText.visibility = View.GONE
                deleteText.visibility = View.GONE
                isAllFabsVisible = false
            }
        }
        edit.setOnClickListener {
            if(textID.text=="")
            {
                Toast.makeText(this, "Please click on on of the post!", Toast.LENGTH_SHORT).show()
            }else
            {
                var i=Intent(this,editPage::class.java).apply {
                    putExtra("PID",textID.text.toString())
                    putExtra("ID",userid)
                }
                startActivity(i)
            }
        }

        refresh.setOnClickListener {
            val mIntent = intent
            finish()
            startActivity(mIntent)
        }

        delete.setOnClickListener {
            if(textID.text=="")
            {
                Toast.makeText(this, "Please click on on of the post!", Toast.LENGTH_SHORT).show()
            }else
            {

                val builder=AlertDialog.Builder(this)
                builder.setMessage("Are you sure want to delete this data?")
                builder.setCancelable(true)
                builder.setPositiveButton("Yes"){dialog,_->
                    dialog.dismiss()
                    MyDBhelper(applicationContext).deleteData(textID.text.toString())
                }
                builder.setNegativeButton("No"){dialog,_->
                    dialog.dismiss()

                }
                val alert=builder.create()
                alert.show()
                //db.delete()
            }
        }

    }

    private fun viewPost() {
        val postList = helper.getSpecificPost(this,userid)
        val adapter = PostAdapterUser(this, postList)
        rV = findViewById(R.id.Rv)
        rV.layoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rV.adapter = adapter

        adapter.setOnclickItem {
            textID.text=it.pid.toString()
        }
    }
}