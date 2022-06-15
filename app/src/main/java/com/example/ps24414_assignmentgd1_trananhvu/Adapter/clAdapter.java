package com.example.ps24414_assignmentgd1_trananhvu.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ps24414_assignmentgd1_trananhvu.Model.ClassSt;
import com.example.ps24414_assignmentgd1_trananhvu.R;
import com.example.ps24414_assignmentgd1_trananhvu.my_interface.onItemClassClicked;

import java.util.ArrayList;

public class clAdapter extends RecyclerView.Adapter<clAdapter.clAdapterViewHolder>{
    private ArrayList<ClassSt> listClass;
    private onItemClassClicked onItemClassClicked;
    public clAdapter(ArrayList<ClassSt> listClass, onItemClassClicked onItemClassClicked) {
        this.listClass = listClass;
        this.onItemClassClicked = onItemClassClicked;
    }

    @NonNull
    @Override
    public clAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.newlist_class, parent, false);
        return new clAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull clAdapterViewHolder holder, int position) {
        ClassSt classSt = listClass.get(position);
        if(classSt == null){
            return;
        }

        int c = (int) Math.round(Math.random() * 2);
        if(c < 2){
            holder.imgClass.setImageResource(R.drawable.totalclass);
        }else{
            holder.imgClass.setImageResource(R.drawable.totalstudent);
        }

        holder.className.setText(classSt.getClassName());
        holder.amountOfClass.setText("Chưa cập nhật");

        holder.classListItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClassClicked.onItemClassClicked(classSt);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listClass.size();
    }

    class clAdapterViewHolder extends RecyclerView.ViewHolder{
        private RelativeLayout classListItem;
        private ImageView imgClass;
        private TextView className, amountOfClass;

        public clAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            classListItem = itemView.findViewById(R.id.class_card);
            imgClass = itemView.findViewById(R.id.img_class_list);
            className = itemView.findViewById(R.id.tv_className1);
            amountOfClass = itemView.findViewById(R.id.tv_amountOfClass);
        }
    }
}
