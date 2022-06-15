package com.example.ps24414_assignmentgd1_trananhvu.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ps24414_assignmentgd1_trananhvu.DetailStudent;
import com.example.ps24414_assignmentgd1_trananhvu.Model.Student;
import com.example.ps24414_assignmentgd1_trananhvu.R;
import com.example.ps24414_assignmentgd1_trananhvu.my_interface.onItemClickedInterface;

import java.util.ArrayList;

public class stAdapter extends RecyclerView.Adapter<stAdapter.stAdapterViewHolder>{

    private ArrayList<Student> listSt;
    private onItemClickedInterface onItemClickedInterface;
    public stAdapter(ArrayList<Student> listSt, onItemClickedInterface onItemClickedInterface) {
        this.onItemClickedInterface = onItemClickedInterface;
        this.listSt = listSt;
    }

    @NonNull
    @Override
    public stAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.newlist_student, parent, false);
        return new stAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull stAdapterViewHolder holder, int position) {
        Student student = listSt.get(position);
        if(student == null){
            return;
        }
        int c = (int) Math.round(Math.random() * 2);
        if(c < 2){
            holder.imgStudent.setImageResource(R.drawable.totalclass);
        }else{
            holder.imgStudent.setImageResource(R.drawable.totalstudent);
        }
        holder.tvClassName.setText(student.getClassID());
        holder.tvStudentName.setText(String.format("%s", student.getName()));
        holder.studentLayoutItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickedInterface.onItemClick(student);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listSt.size();
    }

    class stAdapterViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout studentLayoutItem;
        private TextView tvStudentName, tvClassName;
        private ImageView imgStudent;
        public stAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            tvStudentName = itemView.findViewById(R.id.tv_name);
            studentLayoutItem = itemView.findViewById(R.id.student_card);
            tvClassName = itemView.findViewById(R.id.tv_className);
            imgStudent = itemView.findViewById(R.id.img_student_list);
        }
    }
}
