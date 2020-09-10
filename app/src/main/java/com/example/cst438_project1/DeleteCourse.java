package com.example.cst438_project1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cst438_project1.DbFiles.Assignment;
import com.example.cst438_project1.DbFiles.AssignmentDao;
import com.example.cst438_project1.DbFiles.Course;
import com.example.cst438_project1.DbFiles.CourseDao;
import com.example.cst438_project1.DbFiles.StudentDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DeleteCourse extends AppCompatActivity {
    private static final String DELETE_COURSE_ID = "com.example.cst438_project1.DeleteCourse";

    int id;

    private CourseDao mCourseDao;
    private AssignmentDao mAssignmentDao;

    TextView deleteCourseText;

    Spinner spinner; // otherwise known as a drop down

    Button goBack;
    Button deleteCourse;

    List<Course> courses;
    List<String> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_course);

        mCourseDao = Room.databaseBuilder(this, StudentDatabase.class, StudentDatabase.COURSE_TABLE)
                .allowMainThreadQueries()
                .build()
                .getCourseDao();


        mAssignmentDao = Room.databaseBuilder(this, StudentDatabase.class, StudentDatabase.ASSIGNMENT_TABLE)
                .allowMainThreadQueries()
                .build()
                .getAssignmentDao();

        get_screen();

    }

    public void toast_maker(String str){
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    public void make_random(){
        List<Course> randoms = new ArrayList<Course>();
        List<Assignment> randoms_assignments = new ArrayList<Assignment>();

        for(int i = 0; i < 2; i++){
            randoms.add(new Course("random" + Integer.toString(i)+ Calendar.getInstance().getTime().toString(), Double.valueOf(0.75), id));
            randoms_assignments.add(new Assignment("assign" + Calendar.getInstance().getTime().toString(), Double.valueOf(3), Double.valueOf(4), Integer.valueOf(i), Integer.valueOf(id)));
        }

        for(Course course: randoms){
            mCourseDao.insert(course);
        }
        for(Assignment assignment: randoms_assignments){
            mAssignmentDao.insert(assignment);
        }

        List<Course> get_courses = mCourseDao.getUserCourses(id);
        for(Course course: get_courses){
            Log.i("Course name", course.getCourseName());
        }
        List<Assignment> get_assignments = mAssignmentDao.getUserAllAssignments(id);
        for(Assignment assignment: get_assignments){
            Log.i("Assignments", assignment.getAssignmentName() +" course id is " + assignment.getCourseId());
        }
//        Log.i("is the get_courses empty?", String.valueOf(get_courses.isEmpty()));


    }

    public void to_logged_in_screen(int userId){
        Intent intent = LoggedInHome.LoggedInIntent(DeleteCourse.this, userId);
        Log.i("Moving view","From DeleteCourse to LoggedIn");
        startActivity(intent);
    }

    public void populate_spinner(){
        items = new ArrayList<String>();
        courses = mCourseDao.getUserCourses(id);
        Log.i("is the courses empty?", String.valueOf(courses.isEmpty()));


        if(courses.isEmpty()){
            /// notify and kick the user out
            toast_maker("Sorry you seem to have no classes yet, make some!");
            to_logged_in_screen(id);
        }
        else{
            for(Course course: courses){
                items.add(course.getCourseName());
            }

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, items);

            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            spinner.setAdapter(dataAdapter);
        }

    }

    public int get_dropdown_value(){
        return spinner.getSelectedItemPosition();
    }

    public int get_course_id(int val){
        return courses.get(val).getCourseId();
    }

    public void delete_assignments(int userId, int courseId){
        List<Assignment> assignments = mAssignmentDao.getUserSpecificCourseAssignments(userId, courseId);

        for(Assignment assignment: assignments){
            mAssignmentDao.delete(assignment);
        }
        toast_maker("Deleted all related assignments");
    }

    public void delete_course(int index){
        mCourseDao.delete(courses.get(index));
        toast_maker("Deleted the course");
    }

    public void get_screen(){
        deleteCourseText = findViewById(R.id.deleteCourseText);

        Intent incoming = getIntent();
        id = incoming.getIntExtra(DELETE_COURSE_ID, -1);

//        make_random();
//        deleteCourseText.setText("Delete Course,USER ID:" + Integer.toString(id));

        spinner = findViewById(R.id.spinnerCourses);
        populate_spinner();

        // populate spinner

        goBack = findViewById(R.id.goBack);
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                to_logged_in_screen(id);
            }
        });

        deleteCourse = findViewById(R.id.buttonDeleteCourse);
        deleteCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int index = get_dropdown_value();
                int course_id = get_course_id(index);
                delete_assignments(id, course_id);
                delete_course(index);
                to_logged_in_screen(id);
            }
        });

    }

    public static Intent DeleteCourseIntent(Context context, int userId){
        Intent intent = new Intent(context, DeleteCourse.class);
        intent.putExtra(DELETE_COURSE_ID, userId);
        return intent;
    }


}