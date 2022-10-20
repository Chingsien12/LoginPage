package com.example.loginpage

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

class MyDBhelper(context: Context):SQLiteOpenHelper(context,"CORE1",null,1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE USERS (USERID INTEGER PRIMARY KEY AUTOINCREMENT, UNAME TEXT, PWD TEXT,DOB TEXT, SKILL TEXT,LANGUAGE TEXT, EMAIL TEXT, PHONE TEXT,ADDRESS TEXT, HISTORY TEXT )")
        //db?.execSQL("CREATE TABLE JOBS ( PID INTEGER PRIMARY KEY AUTOINCREMENT, TITLE TEXT, DESCRIPTION TEXT, CONTRACT TEXT, POSITION TEXT, LOCATION TEXT, EMAIL TEXT, PHONE_NUMBER TEXT )")
        db?.execSQL("CREATE TABLE POST(PID INTEGER PRIMARY KEY AUTOINCREMENT, USERID INTEGER,EMAIL TEXT, TITLE TEXT, DES TEXT, LOCATION TEXT, APPLICATION TEXT, CONTRACT TEXT, DATE TEXT,PHONE TEXT, FOREIGN KEY (USERID) REFERENCES USERS(USERID))")
        db?.execSQL("INSERT INTO USERS (UNAME,PWD,DOB,SKILL,LANGUAGE,EMAIL,PHONE,ADDRESS, HISTORY) VALUES ('CHINGSIEN LY','CHINGSIEN12','26/05/2001','IT MANAGER','KHMER, ENGLISH','Chingsien09@gmail.com','0449219024','85 peel str, kew VIC 3101','Work at Smart company')")
        db?.execSQL("INSERT INTO USERS (UNAME,PWD,DOB,SKILL,LANGUAGE,EMAIL,PHONE,ADDRESS, HISTORY) VALUES ('LyLY','CHINGSIEN12','26/05/2001','IT MANAGER','KHMER, ENGLISH, THAI','lyly@gmail.com','0449219024','85 peel str, kew VIC 3101','Work at Smart company')")

        //db?.execSQL("INSERT INTO JOBS (TITLE,DESCRIPTION,CONTRACT,POSITION,LOCATION,EMAIL,PHONE_NUMBER) VALUES ('IT MANAGER','MANAGER SMALL GROUP OF THE PEOPLE','ON-GOING','FULL TIME','24 WAKEDFIELD STR, HAWTHORN VIC 3122','EDU@SWIN.EDU.AU',' 0449219024')")
        db?.execSQL("INSERT INTO post(TITLE,EMAIL,USERID,DES,LOCATION,APPLICATION,CONTRACT,DATE,PHONE) VALUES ('IT manager','Chingsien09@gmail.com',1,'manage the group','24 WAKEFIELD STR,Hawthron.VIC.3122','Full Time','On-Going','12/12/22','0449219024')")
        db?.execSQL("INSERT INTO post(TITLE,EMAIL,USERID,DES,LOCATION,APPLICATION,CONTRACT,DATE,PHONE) VALUES ('IT support','lylymeng@gmail.com',1,'manage the group','85 peel str,kew.NSW.3101','Part Time','On-Going','12/12/23','012421235')")
        db?.execSQL("INSERT INTO post(TITLE,EMAIL,USERID,DES,LOCATION,APPLICATION,CONTRACT,DATE,PHONE) VALUES ('Retail','Chingsien762@gmail.com',2,'manage the group of Rewtail','71 riversdale str,hawthorn.QLD.4000','Full Time','Fixed Time','12/12/24','012966121')")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

     fun getPost(context: Context):ArrayList<Post>
    {
        val qry="SELECT * from POST"
        val db=this.readableDatabase
        val cursor=db.rawQuery(qry,null)
        val postArrayList=ArrayList<Post>()

        if(cursor.count==0)
        {
            Toast.makeText(context, "No result found!", Toast.LENGTH_SHORT).show()
        }else
        {
            while (cursor.moveToNext())
            {
                val eachLineHolder=Post() // object model
                eachLineHolder.pid=cursor.getInt(0)
                eachLineHolder.uid=cursor.getInt(1)
                eachLineHolder.email=cursor.getString(2)
                eachLineHolder.title=cursor.getString(3)
                eachLineHolder.description=cursor.getString(4)
                eachLineHolder.location=cursor.getString(5)
                eachLineHolder.application=cursor.getString(6)
                eachLineHolder.contract=cursor.getString(7)
                eachLineHolder.date=cursor.getString(8)
                eachLineHolder.phone=cursor.getString(9)
                postArrayList.add(eachLineHolder)
            }
        }
        cursor.close()
        db.close()
        return postArrayList
    }

    fun getSpecificPost(context: Context,id:String?):ArrayList<Post>
    {
        val qry="SELECT * from POST WHERE USERID = $id"
        val db=this.readableDatabase
        val cursor=db.rawQuery(qry,null)
        val postArrayList=ArrayList<Post>()

        if(cursor.count==0)//count the row
        {
            Toast.makeText(context, "No result found!", Toast.LENGTH_SHORT).show()
        }else
        {
            while (cursor.moveToNext())
            {
                val eachLineHolder=Post() // object model
                eachLineHolder.pid=cursor.getInt(0)
                eachLineHolder.uid=cursor.getInt(1)
                eachLineHolder.email=cursor.getString(2)
                eachLineHolder.title=cursor.getString(3)
                eachLineHolder.description=cursor.getString(4)
                eachLineHolder.location=cursor.getString(5)
                eachLineHolder.application=cursor.getString(6)
                eachLineHolder.contract=cursor.getString(7)
                eachLineHolder.date=cursor.getString(8)
                eachLineHolder.phone=cursor.getString(9)
                postArrayList.add(eachLineHolder)
            }
        }
        cursor.close()
        db.close()
        return postArrayList
    }

    fun getSpecificPostUpdate(context: Context,pid:String?): Post
    {
        val qry="SELECT * from POST WHERE PID = $pid"
        val db=this.readableDatabase
        val cursor=db.rawQuery(qry,null)
        //val postArrayList=ArrayList<Post>()
        val eachLineHolder=Post() // object model
        if(cursor.count==0)
        {
            Toast.makeText(context, "No result found!", Toast.LENGTH_SHORT).show()
        }else
        {
            while (cursor.moveToNext())
            {

                eachLineHolder.pid=cursor.getInt(0)
                eachLineHolder.uid=cursor.getInt(1)
                eachLineHolder.email=cursor.getString(2)
                eachLineHolder.title=cursor.getString(3)
                eachLineHolder.description=cursor.getString(4)
                eachLineHolder.location=cursor.getString(5)
                eachLineHolder.application=cursor.getString(6)
                eachLineHolder.contract=cursor.getString(7)
                eachLineHolder.date=cursor.getString(8)
                eachLineHolder.phone=cursor.getString(9)
                //postArrayList.add(eachLineHolder)
            }
        }
        cursor.close()
        db.close()
        return eachLineHolder
    }

    fun deleteData(id:String)
    {
        val db=this.writableDatabase

        db.delete("POST","PID=$id",null)
    }
//get post search for the search bar
    fun getPostsearch(context: Context,title:String):ArrayList<Post>
    {
        //Toast.makeText(context, "this is toast from DB $title", Toast.LENGTH_SHORT).show()
        val qry="SELECT * from POST WHERE TITLE LIKE '%$title%'"
//        val qry="SELECT * from POST WHERE TITLE='$title'"
        val db=this.readableDatabase
        val cursor=db.rawQuery(qry,null)
        val postArrayList=ArrayList<Post>()

        if(cursor.count==0)
        {
            Toast.makeText(context, "No result found!", Toast.LENGTH_SHORT).show()
        }else
        {
            while (cursor.moveToNext())
            {
                val eachLineHolder=Post() // object model
                eachLineHolder.pid=cursor.getInt(0)
                eachLineHolder.uid=cursor.getInt(1)
                eachLineHolder.email=cursor.getString(2)
                eachLineHolder.title=cursor.getString(3)
                eachLineHolder.description=cursor.getString(4)
                eachLineHolder.location=cursor.getString(5)
                eachLineHolder.application=cursor.getString(6)
                eachLineHolder.contract=cursor.getString(7)
                eachLineHolder.date=cursor.getString(8)
                eachLineHolder.phone=cursor.getString(9)
                postArrayList.add(eachLineHolder)
            }
        }
        cursor.close()
        db.close()
        return postArrayList
    }

    fun getPostsearchState(context: Context,title:String,state:String):ArrayList<Post>
    {
        //Toast.makeText(context, "this is toast from DB $title", Toast.LENGTH_SHORT).show()
        val qry="SELECT * from POST WHERE TITLE LIKE '%$title%' AND LOCATION LIKE '%$state%'"
        val db=this.readableDatabase
        val cursor=db.rawQuery(qry,null)
        val postArrayList=ArrayList<Post>()

        if(cursor.count==0)
        {
            Toast.makeText(context, "No result found!", Toast.LENGTH_SHORT).show()
        }else
        {
            while (cursor.moveToNext())
            {
                val eachLineHolder=Post() // object model
                eachLineHolder.pid=cursor.getInt(0)
                eachLineHolder.uid=cursor.getInt(1)
                eachLineHolder.email=cursor.getString(2)
                eachLineHolder.title=cursor.getString(3)
                eachLineHolder.description=cursor.getString(4)
                eachLineHolder.location=cursor.getString(5)
                eachLineHolder.application=cursor.getString(6)
                eachLineHolder.contract=cursor.getString(7)
                eachLineHolder.date=cursor.getString(8)
                eachLineHolder.phone=cursor.getString(9)
                postArrayList.add(eachLineHolder)
            }
        }
        cursor.close()
        db.close()
        return postArrayList
    }

    fun getUser(context: Context,ID:String):account
    {
        val qry="SELECT * from USERS WHERE USERID=$ID"
        val db=this.readableDatabase
        val cursor=db.rawQuery(qry,null)
        val eachLineHolder=account() // object model
        if(cursor.count==0)
        {
            Toast.makeText(context, "No result found!", Toast.LENGTH_SHORT).show()
        }else
        {
            while (cursor.moveToNext())
            {

                eachLineHolder.userid=cursor.getInt(0)
                eachLineHolder.uname=cursor.getString(1)
                eachLineHolder.dob=cursor.getString(3)
                eachLineHolder.skill=cursor.getString(4)
                eachLineHolder.language=cursor.getString(5)
                eachLineHolder.email=cursor.getString(6)
                eachLineHolder.phone=cursor.getString(7)
                eachLineHolder.address=cursor.getString(8)
                eachLineHolder.history=cursor.getString(9)
            }
        }
        cursor.close()
        db.close()
        return eachLineHolder
    }

    fun getUserNumPost(context: Context,ID:String):Int
    {
        val qry="SELECT * from POST WHERE USERID=$ID"
        val db=this.readableDatabase
        val cursor=db.rawQuery(qry,null)
        val eachLineHolder=account() // object model
        if(cursor.count==0)
        {
            return cursor.count
        }
        cursor.close()
        db.close()
        return cursor.count
    }
}