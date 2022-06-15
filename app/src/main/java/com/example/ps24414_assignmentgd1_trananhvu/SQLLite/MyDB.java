package com.example.ps24414_assignmentgd1_trananhvu.SQLLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyDB extends SQLiteOpenHelper {
    public static String dbName = "StudentManagement";
    public static int dbVs = 1;
    public MyDB(@Nullable Context context) {
        super(context, dbName, null, dbVs);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        create table class(
        /*
        *
        * _id integer primary key autoincrement
        * tenLop Text
        * )
        * create table student
        *(
        * _id integer primary key autoincrement
        * tenSinhVien text
        * id_lop integer
        *)
        *
        * */
        String createTblClass = "create table class" +
                "(" +
                "id_class text primary key not null," +
                "className text" +
                ")";
        db.execSQL(createTblClass);
        String createTblStudent = "create table student" +
                "(" +
                "id_student text primary key not null," +
                "studentName text," +
                "id_class text," +
                "birthDay text" +
                ")";
        db.execSQL(createTblStudent);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql1 = "DROP TABLE IF EXISTS class";
        db.execSQL(sql1);
        String sql2 = "DROP TABLE IF EXISTS student";
        db.execSQL(sql2);
        onCreate(db);
    }

}
