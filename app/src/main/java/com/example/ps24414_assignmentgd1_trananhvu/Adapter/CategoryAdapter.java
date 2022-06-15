package com.example.ps24414_assignmentgd1_trananhvu.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.ps24414_assignmentgd1_trananhvu.Model.Category;
import com.example.ps24414_assignmentgd1_trananhvu.R;

import java.util.List;

public class CategoryAdapter extends ArrayAdapter<Category> {
    public CategoryAdapter(@NonNull Context context, int resource, @NonNull List<Category> objects) {
        super(context, resource, objects);
    }

    @SuppressLint("ViewHolder")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_spinner, parent, false);
        TextView classSelected = convertView.findViewById(R.id.classSelected);

        Category category = this.getItem(position);

        if(category != null){
            classSelected.setText(category.getName());
        }
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_spinner, parent, false);
        TextView className = convertView.findViewById(R.id.item_category);

        Category category = this.getItem(position);

        if(category != null){
            className.setText(category.getName());
        }
        return convertView;
    }
}
