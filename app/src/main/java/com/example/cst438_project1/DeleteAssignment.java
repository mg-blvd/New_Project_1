package com.example.cst438_project1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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
import com.example.cst438_project1.DbFiles.CourseBasicInfo;
import com.example.cst438_project1.DbFiles.CourseDao;
import com.example.cst438_project1.DbFiles.StudentDatabase;
import com.example.cst438_project1.RecyclerViewFiles.DeleteAssignmentAdapter;

import java.util.ArrayList;
import java.util.List;

public class DeleteAssignment extends AppCompatActivity {
    private static final String DELETE_ASSIGNMENT_ID = "com.example.cst438_project1.DeleteAssignment";

    int id;
    CourseDao mCourseDao;
    AssignmentDao mAssignmentDao;
    List<CourseBasicInfo> mCourses;
    List<Assignment> mAssignments;

    TextView deleteAssignmentText;
    Button goBack;
    Spinner courseDropDown;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_assignment);

        setupDaos();
        get_screen();
    }

    public void toast_maker(String str){
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    public void to_logged_in_screen(int userId){
        Intent intent = LoggedInHome.LoggedInIntent(DeleteAssignment.this, userId);
        Log.i("Moving view","From DeleteAssignment to LoggedIn");
        startActivity(intent);
    }

    public void get_screen(){
        //// do the get screen
        deleteAssignmentText = findViewById(R.id.deleteAssignmentText);

        Intent incoming = getIntent();
        id = incoming.getIntExtra(DELETE_ASSIGNMENT_ID, -1);

        deleteAssignmentText.setText("Delete Assignemnt,USER ID:" + Integer.toString(id));

        goBack = findViewById(R.id.goBack);
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                to_logged_in_screen(id);
            }
        });

        courseDropDown = findViewById(R.id.deleteCourseDropdown);
        mCourses = mCourseDao.getUserCourseBasicInfo(id);
        populateDropdown();
        /// make the button in the loggedin and stuff

        mRecyclerView = findViewById(R.id.deleteCourseRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        populateRecyclerAdapter();
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void setupDaos() {
        mCourseDao = Room.databaseBuilder(this, StudentDatabase.class, StudentDatabase.COURSE_TABLE)
                .allowMainThreadQueries()
                .build()
                .getCourseDao();

        mAssignmentDao = Room.databaseBuilder(this, StudentDatabase.class, StudentDatabase.ASSIGNMENT_TABLE)
                .allowMainThreadQueries()
                .build()
                .getAssignmentDao();
    }

    private void populateDropdown() {
        List<String> dropdownVals = extractCourseNamesToList();

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, dropdownVals);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        courseDropDown.setAdapter(dataAdapter);
    }

    private void populateRecyclerAdapter() {
        String courseName = courseDropDown.getSelectedItem().toString();
        Integer courseId = 1;
        for(CourseBasicInfo course : mCourses) {
            if(course.getCourseId().equals(courseName)) {
                courseId = course.getCourseId();
            }
        }

        mAssignments = mAssignmentDao.getUserSpecificCourseAssignments(id, courseId);
        mAdapter = new DeleteAssignmentAdapter(mAssignments);
    }

    private List<String> extractCourseNamesToList() {
        List<String> courseNames = new ArrayList<>();

        for(CourseBasicInfo course : mCourses) {
            courseNames.add(course.getCourseName());
        }

        return courseNames;
    }


    public static Intent DeleteAssignmentIntent(Context context, int userId){
        Intent intent = new Intent(context, DeleteAssignment.class);
        intent.putExtra(DELETE_ASSIGNMENT_ID, userId);
        return intent;
    }
}