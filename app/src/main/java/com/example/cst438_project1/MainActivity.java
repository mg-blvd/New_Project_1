package com.example.cst438_project1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.widget.Toast;

import com.example.cst438_project1.DbFiles.AssignmentDao;
import com.example.cst438_project1.DbFiles.CourseDao;
import com.example.cst438_project1.DbFiles.StudentDatabase;
import com.example.cst438_project1.DbFiles.UserDao;


public class MainActivity extends AppCompatActivity {

    private UserDao mUserDao;
    private AssignmentDao mAssignmentDao;
    private CourseDao mCourseDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mUserDao = Room.databaseBuilder(this, StudentDatabase.class, StudentDatabase.USER_TABLE)
                .allowMainThreadQueries()
                .build()
                .getUserDao();

        mAssignmentDao = Room.databaseBuilder(this, StudentDatabase.class, StudentDatabase.ASSIGNMENT_TABLE)
                .allowMainThreadQueries()
                .build()
                .getAssignmentDao();

        mCourseDao = Room.databaseBuilder(this, StudentDatabase.class, StudentDatabase.COURSE_TABLE)
                .allowMainThreadQueries()
                .build()
                .getCourseDao();


        Toast.makeText(this, "Everything was built correctly", Toast.LENGTH_LONG).show();
    }

}