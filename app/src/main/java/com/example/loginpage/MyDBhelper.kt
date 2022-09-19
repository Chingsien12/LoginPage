package com.example.loginpage

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MyDBhelper(context: Context):SQLiteOpenHelper(context,"CORE1",null,1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE USERS (USERID INTEGER PRIMARY KEY AUTOINCREMENT, UNAME TEXT, PWD TEXT,DOB TEXT, SKILL TEXT,LANGUAGE TEXT, EMAIL TEXT, PHONE TEXT,ADDRESS TEXT, HISTORY TEXT )")
        db?.execSQL("CREATE TABLE JOBS ( PID INTEGER PRIMARY KEY AUTOINCREMENT, TITLE TEXT, DESCRIPTION TEXT, CONTRACT TEXT, POSITION TEXT, LOCATION TEXT, EMAIL TEXT, PHONE_NUMBER TEXT )")
    //db?.execSQL("INSERT INTO USERS (UNAME,PWD,SKILL,LANGUAGA,EMAIL,PHONE,ADDRESS, HISTORY) VALUES ('CHINGSIEN LY','CHINGSIEN12','IT MANAGER','KHMER, ENGLISH','Chingsien09@gmail.com','0449219024','85 peel str, kew VIC 3101','Work at Smart company')")
        //db?.execSQL("INSERT INTO JOBS (TITLE,DESCRIPTION,CONTRACT,POSITION,LOCATION,EMAIL,PHONE_NUMBER) VALUES ('IT MANAGER','MANAGER SMALL GROUP OF THE PEOPLE','ON-GOING','FULL TIME','24 WAKEDFIELD STR, HAWTHORN VIC 3122','EDU@SWIN.EDU.AU',' 0449219024')")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

}