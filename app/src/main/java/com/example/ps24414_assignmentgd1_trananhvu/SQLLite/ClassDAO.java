package com.example.ps24414_assignmentgd1_trananhvu.SQLLite;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.ps24414_assignmentgd1_trananhvu.Model.ClassSt;
import com.example.ps24414_assignmentgd1_trananhvu.Model.Student;

import java.util.ArrayList;

public class ClassDAO {
    SQLiteDatabase db;
    Context context;
    public ClassDAO(Context context){
        this.context = context;
        MyDB myDB = new MyDB(context);
        db = myDB.getWritableDatabase();
    }

    public void AddClass(ClassSt classSt){
        ContentValues values = new ContentValues();
        values.put("className", classSt.getClassName());
        values.put("id_class", classSt.getId());
        db.insert("class", null, values);
    }

    public void AddStudent(Student student){
        ContentValues values1 = new ContentValues();
        values1.put("id_student", student.getId());
        values1.put("studentName", student.getName());
        values1.put("birthDay", student.getBirthDay());
        values1.put("id_class", student.getClassID());
        db.insert("student",null, values1);
    }

    public ArrayList<ClassSt> getListClass(){
        ArrayList<ClassSt> listClass = new ArrayList<>();
        String sql = "SELECT * FROM class";
        @SuppressLint("Recycle")
        Cursor cs = db.rawQuery(sql, null);

        if(cs.moveToFirst()){
            do{
                String _id = cs.getString(0);
                String className = cs.getString(1);
                listClass.add(new ClassSt(_id, className));
            }while(cs.moveToNext());
        }
        return listClass;
    }

    public ArrayList<Student> getListStudent(){
        ArrayList<Student> listStudent = new ArrayList<>();
        String sql = "SELECT * FROM student";
        @SuppressLint("Recycle")
        Cursor cs = db.rawQuery(sql, null);

        if(cs.moveToFirst()){
            do{
                String idStudent = cs.getString(0);
                String studentName = cs.getString(1);
                String idClass = cs.getString(2);
                String birthDay = cs.getString(3);
                listStudent.add(new Student(idStudent, studentName, birthDay, idClass));
            }while(cs.moveToNext());
        }
        return listStudent;
    }

    public void deleteStudent(String id){
        db.delete("student", "id_student = ?", new String[]{id});
    }

    public void deleteClass(String _id){
        db.delete("class", "id_class = ?", new String[]{_id});
    }

    public void updateClass(ClassSt classSt){
//        id cữ và tên lớp là mới
//        update class set className = ? where _id = ?
        ContentValues values = new ContentValues();
        values.put("className", classSt.getClassName());
        db.update("class", values, "id_class = ?",new String[]{classSt.getId()});
    }

    public void updateStudent(Student student){
        ContentValues values = new ContentValues();
        values.put("studentName", student.getName());
        values.put("id_class", student.getClassID());
        values.put("birthDay", student.getBirthDay());
        db.update("student", values, "id_student = ?", new String[]{student.getId()});
    }

}
