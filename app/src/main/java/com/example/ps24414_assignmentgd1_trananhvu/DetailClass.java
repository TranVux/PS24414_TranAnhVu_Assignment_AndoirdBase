package com.example.ps24414_assignmentgd1_trananhvu;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ps24414_assignmentgd1_trananhvu.Model.ClassSt;
import com.example.ps24414_assignmentgd1_trananhvu.SQLLite.ClassDAO;

import java.util.ArrayList;

public class DetailClass extends AppCompatActivity {
    EditText classId, className;
    Button btnUpdateClass, btnDeleteClass;
    int indexOfClass;
    final int RESULT_UPDATE_CLASS = 753;
    final int RESULT_DELETE_CLASS = 951;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.class_detaillayout);
        classId = findViewById(R.id.txtIDClass);
        classId.setEnabled(false);
        className = findViewById(R.id.txtclassname);
        btnDeleteClass = findViewById(R.id.btn_delete_class);
        btnUpdateClass = findViewById(R.id.btn_update_class);
        Bundle data = getIntent().getExtras();
        if(data == null){
            return;
        }
        ClassSt classSt = (ClassSt) data.get("objectClass");
        indexOfClass = (int) data.get("indexOfClass");
        Log.d("indexOfClassDetail", "onCreate: "  + indexOfClass);
        classId.setText(classSt.getId() + "");
        className.setText(classSt.getClassName());
        btnUpdateClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(className.getText().toString().isEmpty()){
                    Toast.makeText(DetailClass.this, "Không được để trống thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent data = new Intent();
                Bundle bundle = new Bundle();
                bundle.putInt("indexOfClass", indexOfClass);
                bundle.putSerializable("ObjectClass", new ClassSt(classId.getText().toString(), className.getText().toString()));
                data.putExtras(bundle);
                data.putExtra("indexOfClass", indexOfClass);
                setResult(RESULT_UPDATE_CLASS, data);
                Toast.makeText(DetailClass.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
            }
        });
        btnDeleteClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent();
                data.putExtra("idClass", classSt.getId());
                data.putExtra("indexOfClass", indexOfClass);
                setResult(RESULT_DELETE_CLASS, data);
                Toast.makeText(DetailClass.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
