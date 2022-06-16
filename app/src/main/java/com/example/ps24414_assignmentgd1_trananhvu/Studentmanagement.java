package com.example.ps24414_assignmentgd1_trananhvu;

import android.app.Dialog;
import android.content.Intent;
import android.content.SyncAdapterType;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ps24414_assignmentgd1_trananhvu.Adapter.CategoryAdapter;
import com.example.ps24414_assignmentgd1_trananhvu.Adapter.stAdapter;
import com.example.ps24414_assignmentgd1_trananhvu.Model.Category;
import com.example.ps24414_assignmentgd1_trananhvu.Model.ClassSt;
import com.example.ps24414_assignmentgd1_trananhvu.Model.Student;
import com.example.ps24414_assignmentgd1_trananhvu.SQLLite.ClassDAO;
import com.example.ps24414_assignmentgd1_trananhvu.my_interface.onItemClickedInterface;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Studentmanagement extends AppCompatActivity {
    final int RESULT_UPDATE_STUDENT = 7503;
    final int RESULT_DELETE_STUDENT = 9501;
    final int RESULT_ADD_STUDENT = 9511;
    ArrayList<Student> listSt;
    ArrayList<ClassSt> listClass;
    RecyclerView listStudent;
    stAdapter adapter;
    ImageView btnAdd;
    ClassDAO db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.studentmanagement);
        listStudent = findViewById(R.id.listStudent_new);
        btnAdd = findViewById(R.id.btn_addST);
//        listSt = (ArrayList<Student>) getIntent().getExtras().get("listStudent");
//        listClass = (ArrayList<ClassSt>) getIntent().getExtras().get("listClass");
        db = new ClassDAO(Studentmanagement.this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Studentmanagement.this);
        listStudent.setLayoutManager(linearLayoutManager);
        setupRecycleView();
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogAddStudent(Gravity.CENTER);
            }
        });
    }

    public void setupRecycleView() {
        listSt = db.getListStudent();
        listClass = db.getListClass();
        adapter = new stAdapter(listSt, new onItemClickedInterface() {
            @Override
            public void onItemClick(Student student) {
                onClickToItem(student);
            }
        });
        listStudent.setAdapter(adapter);
    }

    public void onClickToItem(Student student) {
        Intent showDetail = new Intent(Studentmanagement.this, DetailStudent.class);
        Bundle newBundle = new Bundle();
        newBundle.putSerializable("ObjectStudent", student);
        newBundle.putInt("indexOfStudent", listSt.indexOf(student));
//        newBundle.putInt("indexOfClass", listClass.indexOf());
        showDetail.putExtra("listClass", listClass);
        showDetail.putExtras(newBundle);
        startActivityForResult(showDetail, 123);
    }

    public void openDialogAddStudent(int gravity) {
        final Dialog dialogAddStudent = new Dialog(Studentmanagement.this);
        dialogAddStudent.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogAddStudent.setContentView(R.layout.add_student_dialog);

        Window window = dialogAddStudent.getWindow();
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowAttribute = window.getAttributes();
        window.setAttributes(windowAttribute);

//        turn off the dialog when click out of area of dialog

        if (gravity == Gravity.CENTER) {
            dialogAddStudent.setCancelable(true);
        } else {
            dialogAddStudent.setCancelable(false);
        }

//        variable
        EditText inputID, inputName, inputBirthday;
        Spinner choiceClass;
        Button btnOutAddStudent, btnAddStudent;
        ClassSt[] currentClass = new ClassSt[1];

        inputID = dialogAddStudent.findViewById(R.id.input_ID);
        inputName = dialogAddStudent.findViewById(R.id.input_studentName);
        inputBirthday = dialogAddStudent.findViewById(R.id.input_birthDay);
        btnOutAddStudent = dialogAddStudent.findViewById(R.id.btn_outAdd);
        btnAddStudent = dialogAddStudent.findViewById(R.id.btn_addSTDialog);

        CategoryAdapter categoryAdapter = new CategoryAdapter(this, R.layout.custom_spinner, getListClassName());
        choiceClass = dialogAddStudent.findViewById(R.id.choiceClass);
        choiceClass.setAdapter(categoryAdapter);
        choiceClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                xử lý thêm học sinh
                currentClass[0] = (ClassSt) listClass.get(position);
                Log.d("debug", "onItemSelected: " + currentClass[0].getClassName());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        btnOutAddStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogAddStudent.dismiss();// hidden dialog
            }
        });

        btnAddStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputID.getText().toString().isEmpty() || inputName.getText().toString().isEmpty() || inputBirthday.getText().toString().isEmpty()) {
                    Toast.makeText(Studentmanagement.this, "Bạn phải nhập đầy đủ thông tin trước khi thêm sinh viên", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!check(inputID, inputBirthday)) return;
                Student newStudent = new Student(inputID.getText().toString(), inputName.getText().toString(), inputBirthday.getText().toString(), currentClass[0].getId());
                Toast.makeText(Studentmanagement.this, "Thêm sinh viên thành công", Toast.LENGTH_SHORT).show();
//                listSt.add(newStudent);
                db.AddStudent(newStudent);
                setupRecycleView();
                Intent data = new Intent();
                setResult(RESULT_ADD_STUDENT, data);
                dialogAddStudent.dismiss();
            }
        });

        dialogAddStudent.show();//show dialog
    }

    public ArrayList<Category> getListClassName() {
        ArrayList<Category> listClassName = new ArrayList<>();

        if (listClass.size() == 0) {
            return listClassName;
        } else {
            for (ClassSt classSt : listClass) {
                listClassName.add(new Category(classSt.getClassName()));
            }
        }
        return listClassName;
    }

    public boolean check(EditText inputID, EditText inputBirthday) {
        for (int i = 0; i < listSt.size(); i++) {
            if (inputID.getText().toString().equals(listSt.get(i).getId())) {
                Toast.makeText(this, "Id không được trùng với các sinh viên trước đó", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        Date td = new Date();
        Date birthday;
        try {
            SimpleDateFormat fomater = new SimpleDateFormat("dd/mm/yyyy");
            birthday = fomater.parse(inputBirthday.getText().toString());
        } catch (Exception e) {
            Log.d("check error", "check: error try");
            Toast.makeText(this, "Định dạng đúng là dd/mm/yyyy", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (birthday.getYear() > td.getYear()) {
            Toast.makeText(this, "Năm sinh của sinh viên không thể lớn hơn năm hiện tại", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 123) {
            if (resultCode == RESULT_UPDATE_STUDENT) {
                Student newStudent = (Student) data.getExtras().get("ObjectStudent");
                int indexOfStudent = (int) data.getExtras().get("indexOfStudent");
//                listSt.set(indexOfStudent, newStudent);
                db.updateStudent(newStudent);
                setupRecycleView();
                Intent intent = new Intent();
                setResult(RESULT_UPDATE_STUDENT, intent);
            }
            if (resultCode == RESULT_DELETE_STUDENT) {
                String idStudent = data.getStringExtra("IDStudent");
                int indexOfStudent = (int) data.getExtras().get("indexOfStudent");
//                listSt.remove(indexOfStudent);
                db.deleteStudent(idStudent);
                setupRecycleView();
                Intent intent1 = new Intent();
                setResult(RESULT_DELETE_STUDENT, intent1);
            }
        }
    }
}
