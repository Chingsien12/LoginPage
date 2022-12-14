package com.example.loginpage

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

class MyDBhelper(context: Context):SQLiteOpenHelper(context,"CORE1",null,1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE USERS (USERID INTEGER PRIMARY KEY AUTOINCREMENT, UNAME TEXT, PWD TEXT,DOB TEXT, SKILL TEXT,LANGUAGE TEXT, EMAIL TEXT, PHONE TEXT,ADDRESS TEXT, HISTORY TEXT )")
        //db?.execSQL("CREATE TABLE JOBS ( PID INTEGER PRIMARY KEY AUTOINCREMENT, TITLE TEXT, DESCRIPTION TEXT, CONTRACT TEXT, POSITION TEXT, LOCATION TEXT, EMAIL TEXT, PHONE_NUMBER TEXT )")
        db?.execSQL("CREATE TABLE POST(PID INTEGER PRIMARY KEY AUTOINCREMENT, USERID INTEGER,EMAIL TEXT, TITLE TEXT, DES TEXT, LOCATION TEXT, APPLICATION TEXT, CONTRACT TEXT, DATE TEXT, FOREIGN KEY (USERID) REFERENCES USERS(USERID))")
        db?.execSQL("INSERT INTO USERS (UNAME,PWD,DOB,SKILL,LANGUAGE,EMAIL,PHONE,ADDRESS, HISTORY) VALUES ('CHINGSIEN LY','CHINGSIEN12','26/05/2001','IT MANAGER','KHMER, ENGLISH','Chingsien09@gmail.com','0449219024','85 peel str, kew VIC 3101','Work at Smart company')")
        db?.execSQL("INSERT INTO USERS (UNAME,PWD,DOB,SKILL,LANGUAGE,EMAIL,PHONE,ADDRESS, HISTORY) VALUES ('LyLY','CHINGSIEN12','26/05/2001','IT MANAGER','KHMER, ENGLISH, THAI','lyly@gmail.com','0449219024','85 peel str, kew VIC 3101','Work at Smart company')")

        //db?.execSQL("INSERT INTO JOBS (TITLE,DESCRIPTION,CONTRACT,POSITION,LOCATION,EMAIL,PHONE_NUMBER) VALUES ('IT MANAGER','MANAGER SMALL GROUP OF THE PEOPLE','ON-GOING','FULL TIME','24 WAKEDFIELD STR, HAWTHORN VIC 3122','EDU@SWIN.EDU.AU',' 0449219024')")
        db?.execSQL("INSERT INTO post(TITLE,EMAIL,USERID,DES,LOCATION,APPLICATION,CONTRACT,DATE) VALUES ('IT manager','Chingsien09@gmail.com',1,'manage the group','VIC','fulltime','on-going','12/12/22')")
        db?.execSQL("INSERT INTO post(TITLE,EMAIL,USERID,DES,LOCATION,APPLICATION,CONTRACT,DATE) VALUES ('IT support','lylymeng@gmail.com',1,'manage the group','VIC','parttime','on-going','12/12/23')")
        db?.execSQL("INSERT INTO post(TITLE,EMAIL,USERID,DES,LOCATION,APPLICATION,CONTRACT,DATE) VALUES ('Retail','Chingsien762@gmail.com',2,'manage the group of Rewtail','VIC','fulltime','on-going','12/12/24')")
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
                postArrayList.add(eachLineHolder)
            }
        }
        cursor.close()
        db.close()
        return postArrayList
    }
}