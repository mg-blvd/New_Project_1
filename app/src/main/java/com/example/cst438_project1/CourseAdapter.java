package com.example.cst438_project1;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cst438_project1.DbFiles.Course;

import java.util.List;
//!!Recycler View code template drived from https://guides.codepath.com/android/using-the-recyclerview!!
public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder>{
    private List<Course> mCourses;

    public CourseAdapter(List<Course> courses){
        mCourses = courses;
    }

    @Override
    public CourseAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
        Context context = viewGroup.getContext();
        LayoutInflater li = LayoutInflater.from(context);

        View courseView = li.inflate(R.layout.recycler_view_item, viewGroup, false);
        return new ViewHolder(courseView);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseAdapter.ViewHolder holder, int position) {
        Course course = mCourses.get(position);

        TextView nameView = holder.courseName;
        nameView.setText(course.getCourseName());

        TextView gradeView = holder.courseGrade;
        Double grade = course.getCurrentGrade();
        gradeView.setText(grade.toString());
    }

    @Override
    public int getItemCount() {
        return mCourses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView courseName;
        public TextView courseGrade;

        public ViewHolder(View v){
            super(v);

            courseName = (TextView) v.findViewById(R.id.course_name);
            courseGrade = (TextView) v.findViewById(R.id.course_grade);
        }
    }
}
