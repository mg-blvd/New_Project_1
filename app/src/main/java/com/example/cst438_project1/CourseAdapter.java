package com.example.cst438_project1;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cst438_project1.DbFiles.Course;

import java.util.List;

/**
 * <h1>Course Adapter</h1>
 * <p>
 *      Binds the course information to the RecyclerView so all the active courses appear in a list
 *      with their grades.
 * </p>
 * @see androidx.recyclerview.widget.RecyclerView
 * @see androidx.recyclerview.widget.RecyclerView.Adapter
 */
//!!Recycler View code template derived from https://guides.codepath.com/android/using-the-recyclerview!!
public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder>{
    private List<Course> mCourses;

    /**
     * Populates the courses list with a list passes in through a parameter
     * @param courses List&ltCourses&gt: List of courses from Compute
     */
    public CourseAdapter(List<Course> courses){
        mCourses = courses;
    }

    /**
     * Creates a View to contain information of a course.
     * @param viewGroup ViewGroup
     * @param viewType int
     * @return New ViewHolder
     */
    @Override
    public CourseAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
        Context context = viewGroup.getContext();
        LayoutInflater li = LayoutInflater.from(context);

        View courseView = li.inflate(R.layout.recycler_view_courses, viewGroup, false);
        return new ViewHolder(courseView);
    }

    /**
     * Binds the Course information to each view in the RecyclerView and sets up a link to the
     * AssignmentView activity for each course.
     * @param holder CourseAdapter.ViewHolder: The view holder
     * @param position int
     */
    @Override
    public void onBindViewHolder(@NonNull CourseAdapter.ViewHolder holder, int position) {
        final Course course = mCourses.get(position);

        TextView nameView = holder.courseName;
        nameView.setText(course.getCourseName());
        nameView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent assignmentIntent = new Intent(view.getContext(), AssignmentView.class);
                assignmentIntent.putExtra("courseID", course.getCourseId());
                assignmentIntent.putExtra("studentID", course.getStudentId());
                assignmentIntent.putExtra("courseName", course.getCourseName());
                view.getContext().startActivity(assignmentIntent);
            }
        });

        TextView gradeView = holder.courseGrade;
        String grade = getLetterGrade(course.getCurrentGrade());
        gradeView.setText(grade);
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
     * Gets the number of items inside of the adapter
     * @return Number of items in the current adapter
     */
    @Override
    public int getItemCount() {
        return mCourses.size();
    }

    /**
     * <h1>View Holder</h1>
     * <p>
     *     A container for the course name and grade.
     * </p>
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView courseName;
        public TextView courseGrade;

        /**
         * Sets the TextViews to their respective GUI elements.
         * @param v View: The View inside of the RecyclerView
         */
        public ViewHolder(View v){
            super(v);

            courseName = (TextView) v.findViewById(R.id.course_name);
            courseGrade = (TextView) v.findViewById(R.id.course_grade);
        }
    }
}
