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

/**
 * <h1>Assignment Adapter</h1>
 * <p>
 *      Like CourseAdapter, AssignmentAdapter binds the assignment information to the RecyclerView
 *      so all the entered assignments appear in a list with their grades.
 * </p>
 * @see androidx.recyclerview.widget.RecyclerView
 * @see androidx.recyclerview.widget.RecyclerView.Adapter
 */
public class AssignmentAdapter extends RecyclerView.Adapter<AssignmentAdapter.ViewHolder> {
    private List<Assignment> mAssignments;

    public AssignmentAdapter(List<Assignment> assignments) {
        mAssignments = assignments;
    }

    /**
     * Creates a View to contain information of a course.
     * @param parent ViewGroup
     * @param viewType int
     * @return New ViewHolder
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater li = LayoutInflater.from(context);

        View assignmentView = li.inflate(R.layout.recycler_view_assignments, parent, false);
        return new ViewHolder(assignmentView);
    }

    /**
     * Binds the Assignment information to each view in the RecyclerView and calculates the letter
     * grade of the earned score and the max score.
     * @param holder CourseAdapter.ViewHolder: The view holder
     * @param position int
     */
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

    /**
     * Gets the number of items inside of the adapter
     * @return Number of items in the current adapter
     */
    @Override
    public int getItemCount() {
        return mAssignments.size();
    }

    /**
     * Divides the earned score and the max score and rounds the percentage to two decimal places.
     * @param assignment Assignment: Current assignment
     * @return A Double in between 0 - 100 rounded to two decimal places
     */
    public Double getPercentage(Assignment assignment) {
        double ans = assignment.getScore() / assignment.getMaxScore();
        return Math.round(ans * 100.0) / 1.0;
    }

    /**
     * Converts the grade percentage to a letter grade
     * @param grade double: Grade percentage
     * @return Letter grade
     */
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

    /**
     * <h1>View Holder</h1>
     * <p>
     *     A container for the course name and grade.
     * </p>
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView assignmentName;
        public TextView assignmentGrade;

        /**
         * Sets the TextViews to their respective GUI elements.
         * @param itemView View: The View inside of the RecyclerView
         */
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            assignmentName = (TextView) itemView.findViewById(R.id.assignmentName);
            assignmentGrade = (TextView) itemView.findViewById(R.id.assignmentGrade);
        }
    }
}
