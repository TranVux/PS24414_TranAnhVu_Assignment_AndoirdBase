package com.example.ps24414_assignmentgd1_trananhvu;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ps24414_assignmentgd1_trananhvu.Adapter.CategoryAdapter;
import com.example.ps24414_assignmentgd1_trananhvu.Adapter.CategoryAdapterStudentDetail;
import com.example.ps24414_assignmentgd1_trananhvu.Model.Category;
import com.example.ps24414_assignmentgd1_trananhvu.Model.ClassSt;
import com.example.ps24414_assignmentgd1_trananhvu.Model.Student;

import java.util.ArrayList;

public class DetailStudent extends AppCompatActivity {
    final int RESULT_UPDATE_STUDENT = 7503;
    final int RESULT_DELETE_STUDENT = 9501;
    final int RESULT_ADD_STUDENT = 9511;
    EditText txtID, txtName, txtBirthDay;
    Button btnUpdateStudent, btnDeleteStudent;
    Spinner spinnerClassName;
    int indexOfStudent;
    ArrayList<ClassSt> listClass;
    ClassSt[] currentClass = new ClassSt[1];
    Student student;
    int indexOfClass;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_detaillayout);

        txtID = findViewById(R.id.txtID);
        txtID.setEnabled(false);
        txtName = findViewById(R.id.txtName);
        txtBirthDay = findViewById(R.id.txtBirthDay);
        btnDeleteStudent = findViewById(R.id.btn_delete_student);
        btnUpdateStudent = findViewById(R.id.btn_update_student);
//        Student student = (Student) getIntent().getExtras().get("studentObject");
        Bundle data = getIntent().getExtras();
        if (data == null) {
            return;
        }
        listClass = (ArrayList<ClassSt>) data.get("listClass");
        Log.d("TAGCheck", "onCreate: " + listClass.get(0).getClassName());
        student = (Student) data.get("ObjectStudent");
        getIDClass();
        indexOfStudent = (int) data.get("indexOfStudent");
        txtID.setText(student.getId());
        txtName.setText(student.getName());
        txtBirthDay.setText(student.getBirthDay());

        CategoryAdapterStudentDetail categoryAdapter = new CategoryAdapterStudentDetail(this, R.layout.category_spinner_detailstudent, getListClassId());
        spinnerClassName = findViewById(R.id.spinerClassname);
        spinnerClassName.setAdapter(categoryAdapter);
        spinnerClassName.setSelection(indexOfClass);
        spinnerClassName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentClass[0] = (ClassSt) listClass.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        btnUpdateStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtID.getText().toString().isEmpty() || txtName.getText().toString().isEmpty() || txtBirthDay.getText().toString().isEmpty()) {
                    Toast.makeText(DetailStudent.this, "Kh??ng ???????c ????? tr???ng th??ng tin c???a sinh vi??n", Toast.LENGTH_SHORT).show();
                    return;
                }
                Student student1 = new Student(txtID.getText().toString(), txtName.getText().toString(), txtBirthDay.getText().toString(), currentClass[0].getId());
                Intent data = new Intent();
                data.putExtra("ObjectStudent", student1);
                data.putExtra("indexOfStudent", indexOfStudent);
                setResult(RESULT_UPDATE_STUDENT, data);
                Toast.makeText(DetailStudent.this, "C???p nh???t sinh vi??n th??nh c??ng", Toast.LENGTH_SHORT).show();
            }
        });

        btnDeleteStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idStudent = student.getId();
                Intent data = new Intent();
                data.putExtra("IDStudent", idStudent);
                data.putExtra("indexOfStudent", indexOfStudent);
                setResult(RESULT_DELETE_STUDENT, data);
                finish();
                Toast.makeText(DetailStudent.this, "X??a sinh vi??n th??nh c??ng", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void getIDClass(){
        for(ClassSt classSt : listClass){
            if (classSt.getId().equals(student.getClassID())){
                indexOfClass = listClass.indexOf(classSt);
                break;
            }
        }
    }

    public ArrayList<Category> getListClassId() {
        ArrayList<Category> listClassName = new ArrayList<>();

        if (listClass.size() == 0) {
            return listClassName;
        } else {
            for (ClassSt classSt : listClass) {
                listClassName.add(new Category(classSt.getId()));
            }
        }
        return listClassName;
    }
}
