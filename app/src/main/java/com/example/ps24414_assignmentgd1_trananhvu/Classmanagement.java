package com.example.ps24414_assignmentgd1_trananhvu;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ps24414_assignmentgd1_trananhvu.Adapter.clAdapter;
import com.example.ps24414_assignmentgd1_trananhvu.Model.ClassSt;
import com.example.ps24414_assignmentgd1_trananhvu.Model.Student;

import com.example.ps24414_assignmentgd1_trananhvu.SQLLite.ClassDAO;
import com.example.ps24414_assignmentgd1_trananhvu.my_interface.onItemClassClicked;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Classmanagement extends AppCompatActivity {
    final int RESULT_UPDATE_CLASS = 753;
    final int RESULT_DELETE_CLASS = 951;
    final int RESULT_ADD_CLASS = 961;
    ArrayList<Student> listSt;
    ArrayList<ClassSt> listClass;
    RecyclerView recyclerViewClass;
    ImageView btnAddClass;
    clAdapter adapter;
    ClassDAO db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.classmanagement);
        listSt = (ArrayList<Student>) getIntent().getExtras().get("listStudent");
        listClass = (ArrayList<ClassSt>) getIntent().getExtras().get("listClass");
        db = new ClassDAO(Classmanagement.this);
        recyclerViewClass = findViewById(R.id.listClass_new);
        setUpRecycleView();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Classmanagement.this);
        recyclerViewClass.setLayoutManager(linearLayoutManager);
        btnAddClass = findViewById(R.id.btn_addClass);

        btnAddClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogAddClass(Gravity.CENTER);
            }
        });
    }

    public void setUpRecycleView() {
        adapter = new clAdapter(listClass, new onItemClassClicked() {
            @Override
            public void onItemClassClicked(ClassSt classSt) {
                onClickItemClass(classSt);
            }
        });
        recyclerViewClass.setAdapter(adapter);
    }

    public void onClickItemClass(ClassSt classSt) {
        Intent showDetailClass = new Intent(Classmanagement.this, DetailClass.class);
        int indexOfClass = listClass.indexOf(classSt);
        Log.d("indexOfClass", "onClickItemClass: "  + indexOfClass);
        Bundle data = new Bundle();
        data.putSerializable("objectClass", classSt);
        data.putInt("indexOfClass", indexOfClass);
        showDetailClass.putExtras(data);
        startActivityForResult(showDetailClass, 321);
    }

    public void openDialogAddClass(int gravity) {
        final Dialog dialogAddClass = new Dialog(Classmanagement.this);
        dialogAddClass.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogAddClass.setContentView(R.layout.add_class_dialog);

        Window window = dialogAddClass.getWindow();
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowAttribute = window.getAttributes();
        window.setAttributes(windowAttribute);

        if (gravity == Gravity.CENTER) {
            dialogAddClass.setCancelable(true);
        } else {
            dialogAddClass.setCancelable(false);
        }

        //install variable
        EditText inputIdClass, inputClassName;
        Button btnAddClassDialog, btnOutAddClass;

        inputClassName = dialogAddClass.findViewById(R.id.input_ClassName);
        inputIdClass = dialogAddClass.findViewById(R.id.input_IDClass);
        btnAddClassDialog = dialogAddClass.findViewById(R.id.btn_addClassFromDialog);
        btnOutAddClass = dialogAddClass.findViewById(R.id.btn_outAddClass);

        btnAddClassDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputClassName.getText().toString().isEmpty() || inputIdClass.getText().toString().isEmpty()) {
                    Toast.makeText(Classmanagement.this, "Phải nhập đầy đủ thông tin trước khi thêm", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!check(inputIdClass))return;
                ClassSt newClass = new ClassSt(inputIdClass.getText().toString(), inputClassName.getText().toString());
                Toast.makeText(Classmanagement.this, "Đã thêm thành công", Toast.LENGTH_SHORT).show();
                listClass.add(newClass);
                setUpRecycleView();
                dialogAddClass.dismiss();
                db.AddClass(newClass);
                Intent daIntent = new Intent();
                setResult(RESULT_ADD_CLASS, daIntent);
            }
        });

        btnOutAddClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogAddClass.dismiss();
            }
        });

        dialogAddClass.show();
    }

    public boolean check(EditText inputID) {
        for (int i = 0; i < listClass.size(); i++) {
            if (inputID.getText().toString().equals(listClass.get(i).getId())) {
                Toast.makeText(this, "Id không được trùng với các lớp trước đó", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 321) {
            if (resultCode == RESULT_UPDATE_CLASS) {
                int indexOfClass = (int) data.getExtras().get("indexOfClass");
                ClassSt classSt = (ClassSt) data.getExtras().get("ObjectClass");
                listClass.set(indexOfClass, classSt);
                setUpRecycleView();
                db.updateClass(classSt);
                Intent daIntent = new Intent();
                setResult(RESULT_UPDATE_CLASS, daIntent);
            }
            if (resultCode == RESULT_DELETE_CLASS) {
                int indexOfClass = (int) data.getExtras().get("indexOfClass");
                Log.d("indexOfClass", "onActivityResult: "  + indexOfClass);
                String idClass = (String) data.getExtras().get("idClass");
                listClass.remove(indexOfClass);
                setUpRecycleView();
                db.deleteClass(idClass);
                Intent daIntent = new Intent();
                setResult(RESULT_UPDATE_CLASS, daIntent);
            }
        }
    }
}
