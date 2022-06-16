package com.example.ps24414_assignmentgd1_trananhvu;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ps24414_assignmentgd1_trananhvu.Model.ClassSt;
import com.example.ps24414_assignmentgd1_trananhvu.Model.Student;
import com.example.ps24414_assignmentgd1_trananhvu.SQLLite.ClassDAO;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    final int RESULT_UPDATE_CLASS = 753;
    final int RESULT_DELETE_CLASS = 951;
    final int RESULT_ADD_CLASS = 961;
    final int RESULT_UPDATE_STUDENT = 7503;
    final int RESULT_DELETE_STUDENT = 9501;
    final int RESULT_ADD_STUDENT = 9511;
    RelativeLayout imageClass, imageStudent, imageTotalStudent, imageTotalClass;
    TextView amountStudent, amountClass;
    ClassDAO db;
    ArrayList<Student> listSt;
    ArrayList<ClassSt> listClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
//        install database
        db = new ClassDAO(MainActivity.this);
        getList();
        imageClass = (RelativeLayout) findViewById(R.id.img_class);
        imageStudent = (RelativeLayout) findViewById(R.id.img_student);
        imageTotalClass = (RelativeLayout) findViewById(R.id.img_total_class);
        imageTotalStudent = (RelativeLayout) findViewById(R.id.img_total_student);
        amountClass = findViewById(R.id.lblAmountClass);
        amountStudent = findViewById(R.id.lblAmountStudent);
        updateAmount();
        imageClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendListClass();
            }
        });

        imageStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendListStudent();
            }
        });
    }

    public void getList() {
        listSt = db.getListStudent();
        if (listSt == null) {
            listSt = new ArrayList<>();
        }
        listClass = db.getListClass();
        if (listClass == null) {
            listClass = new ArrayList<>();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 888) {
            if (resultCode == RESULT_ADD_CLASS || resultCode == RESULT_UPDATE_CLASS) {
                listClass = db.getListClass();
            }
            if (resultCode == RESULT_DELETE_CLASS) {
                listClass = db.getListClass();
                listSt = db.getListStudent();
            }
        } else if (requestCode == 999) {
            if (resultCode == RESULT_ADD_STUDENT || resultCode == RESULT_DELETE_STUDENT || resultCode == RESULT_UPDATE_STUDENT){
                listSt = db.getListStudent();
            }
        }
        updateAmount();
    }

    public void sendListClass() {
        Intent classManagement = new Intent(MainActivity.this, Classmanagement.class);
        classManagement.putExtra("listClass", listClass);
        classManagement.putExtra("listStudent", listSt);
        startActivityForResult(classManagement, 888);
    }

    public void sendListStudent() {
        Intent studentManagement = new Intent(MainActivity.this, Studentmanagement.class);
        studentManagement.putExtra("listStudent", listSt);
        studentManagement.putExtra("listClass", listClass);
        startActivityForResult(studentManagement, 999);
    }

    public void updateAmount() {
        amountStudent.setText(listSt.size() + " học sinh");
        amountClass.setText(listClass.size() + " lớp");
    }
}