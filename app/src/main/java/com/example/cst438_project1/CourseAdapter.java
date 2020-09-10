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

//!!Recycler View code template derived from https://guides.codepath.com/android/using-the-recyclerview!!
public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder>{
    private List<Course> mCourses;

    public CourseAdapter(List<Course> courses){
        mCourses = courses;
    }

    @Override
    public CourseAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
        Context context = viewGroup.getContext();
        LayoutInflater li = LayoutInflater.from(context);

        View courseView = li.inflate(R.layout.recycler_view_courses, viewGroup, false);
        return new ViewHolder(courseView);
    }

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

    @Override
    public int getItemCount() {
        return mCourses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView courseName;
        public TextView courseGrade;

        public ViewHolder(View v){
            super(v);

            courseName = (TextView) v.findViewById(R.id.course_name);
            courseGrade = (TextView) v.findViewById(R.id.course_grade);
        }
    }
}
