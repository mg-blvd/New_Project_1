package com.example.cst438_project1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cst438_project1.DbFiles.Assignment;

import java.util.List;

public class AssignmentAdapter extends RecyclerView.Adapter<AssignmentAdapter.ViewHolder> {
    private List<Assignment> mAssignments;

    public AssignmentAdapter(List<Assignment> assignments) {
        mAssignments = assignments;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater li = LayoutInflater.from(context);

        View assignmentView = li.inflate(R.layout.recycler_view_assignments, parent, false);
        return new ViewHolder(assignmentView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Assignment assignment = mAssignments.get(position);

        TextView assignView = holder.assignmentName;
        assignView.setText(assignment.getAssignmentName());

        TextView assignGrade = holder.assignmentGrade;
        Double percent = getPercentage(assignment);
        String letterGrade = getLetterGrade(percent);
        String output = percent.toString() + "% - " + letterGrade;
        assignGrade.setText(output);
    }

    @Override
    public int getItemCount() {
        return mAssignments.size();
    }

    public Double getPercentage(Assignment assignment) {
        double ans = assignment.getScore() / assignment.getMaxScore();
        return Math.round(ans * 100.0) / 1.0;
    }

    public String getLetterGrade(double grade) {
        if (grade >= 93 && grade <= 100)
            return "A";
        else if (grade >= 90 && grade <= 92)
            return "A-";
        else if (grade >= 87 && grade <= 89)
            return "B+";
        else if (grade >= 83 && grade <= 86)
            return "B";
        else if (grade >= 80 && grade <= 82)
            return "B-";
        else if (grade >= 77 && grade <= 79)
            return "C+";
        else if (grade >= 73 && grade <= 76)
            return "C";
        else if (grade >= 70 && grade <= 72)
            return "C-";
        else if (grade >= 67 && grade <= 69)
            return "D+";
        else if (grade >= 63 && grade <= 66)
            return "D";
        else if (grade >= 60 && grade <= 62)
            return "D-";
        else
            return "F";
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView assignmentName;
        public TextView assignmentGrade;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            assignmentName = (TextView) itemView.findViewById(R.id.assignmentName);
            assignmentGrade = (TextView) itemView.findViewById(R.id.assignmentGrade);
        }
    }
}
