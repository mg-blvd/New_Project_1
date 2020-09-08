package com.example.cst438_project1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.cst438_project1.DbFiles.Course;
import com.example.cst438_project1.DbFiles.CourseDao;
import com.example.cst438_project1.DbFiles.StudentDatabase;

import java.util.List;

public class Compute extends AppCompatActivity {
    private static final String COMPUTE_ID = "com.example.cst438_project1.Compute";
    private List<Course> mCourses;
    int id;
    private CourseDao mCourseDao;

    Button goBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compute);
        mCourseDao = Room.databaseBuilder(this, StudentDatabase.class, StudentDatabase.COURSE_TABLE)
                .allowMainThreadQueries()
                .build()
                .getCourseDao();

        get_screen();
    }

    public void toast_maker(String str){
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    public void to_logged_in_screen(int userId){
        Intent intent = LoggedInHome.LoggedInIntent(Compute.this, userId);
        Log.i("Moving view","From Compute to LoggedIn");
        startActivity(intent);
    }

    public void get_screen(){

        Intent incoming = getIntent();
        id = incoming.getIntExtra(COMPUTE_ID, -1);


        RecyclerView rvCourse = (RecyclerView) findViewById(R.id.rvCourses);

        mCourses = getCourses();

        CourseAdapter adapter = new CourseAdapter(mCourses);

        rvCourse.setAdapter(adapter);

        rvCourse.setLayoutManager(new LinearLayoutManager(this));


        goBack = findViewById(R.id.goBack);
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                to_logged_in_screen(id);
            }
        });

    }

    public List<Course> getCourses() {
        List<Course> userCourses = mCourseDao.getUserCourses(id);
        return userCourses;
    }

    public static Intent ComputeIntent(Context context, int userId){
        Intent intent = new Intent(context, Compute.class);
        intent.putExtra(COMPUTE_ID, userId);
        return intent;
    }
}