package com.example.cst438_project1.RecyclerViewFiles;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.cst438_project1.DbFiles.Assignment;
import com.example.cst438_project1.R;
import com.example.cst438_project1.Utility;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DeleteAssignmentAdapter extends RecyclerView.Adapter<DeleteAssignmentAdapter.ExampleViewHolder> {
    private List<Assignment> mAssignments;

    public static class ExampleViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextViewName;
        public TextView mTextViewGrade;

        public ExampleViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextViewGrade = itemView.findViewById(R.id.assignmentGrade);
            mTextViewName = itemView.findViewById(R.id.assignmentName);
        }
    }

    public DeleteAssignmentAdapter(List<Assignment> assignments) {
        mAssignments = assignments;
    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_assignments, parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {
        Assignment currentItem = mAssignments.get(position);
        String grade = Utility.computeGrade(currentItem.getScore(), currentItem.getMaxScore());

        holder.mTextViewName.setText(currentItem.getAssignmentName());
        holder.mTextViewGrade.setText(grade);
    }

    @Override
    public int getItemCount() {
        return mAssignments.size();
    }
}
