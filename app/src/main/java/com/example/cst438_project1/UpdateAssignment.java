package com.example.cst438_project1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cst438_project1.DbFiles.Assignment;
import com.example.cst438_project1.DbFiles.AssignmentDao;
import com.example.cst438_project1.DbFiles.Course;
import com.example.cst438_project1.DbFiles.CourseDao;
import com.example.cst438_project1.DbFiles.StudentDatabase;

import java.util.ArrayList;
import java.util.List;

public class UpdateAssignment extends AppCompatActivity {
    private static final String UPDATE_ASSIGNMENT_ID = "com.example.cst438_project1.UpdateAssignment";

    int id;

    TextView updateAssignmentText;
    Button goBack;

    EditText assignName;
    EditText assignGot;
    EditText assignTotal;
    Button assignUpdate;

    private CourseDao mCourseDao;
    private AssignmentDao mAssignmentDao;

    Spinner spinnerCourses; // otherwise known as a drop down
    Spinner spinnerAssignments;

    Assignment currentAssignment = null;

    List<Course> courses;
    List<String> coursesNames;
    List<Assignment> assignments;
    List<String> assignmentsNames;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_assignment);

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

    public void to_logged_in_screen(int userId){
        Intent intent = LoggedInHome.LoggedInIntent(UpdateAssignment.this, userId);
        Log.i("Moving view","From UpdateAssignment to LoggedIn");
        startActivity(intent);
    }

    public void populate_courses(int id){
        coursesNames = new ArrayList<String>();
        courses = mCourseDao.getUserCourses(id);

        if(courses.isEmpty()){
            /// notify and kick the user out
            toast_maker("Sorry you seem to have no classes yet, make some!");
            to_logged_in_screen(id);
        }
        else{
            for(Course course: courses){
                coursesNames.add(course.getCourseName());
            }

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, coursesNames);

            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            spinnerCourses.setAdapter(dataAdapter);
        }
    }

    public void populate_assignments(int userId, int courseId){
        assignmentsNames = new ArrayList<String>();
        assignments = mAssignmentDao.getUserSpecificCourseAssignments(userId, courseId);

        if(assignments.isEmpty()){
            toast_maker("Sorry you seem to have no assignments for this class, make some!");
        }

        for(Assignment assignment: assignments){
            assignmentsNames.add(assignment.getAssignmentName());
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, assignmentsNames);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerAssignments.setAdapter(dataAdapter);

    }

    public void set_current_assignment(Assignment assignment){
        currentAssignment = assignment;
    }

    public void set_edits(){
        assignName.setText(currentAssignment.getAssignmentName());
        assignGot.setText(Double.toString(currentAssignment.getScore()));
        assignTotal.setText(Double.toString(currentAssignment.getMaxScore()));
    }

    public boolean check_if_possible(){
        if(currentAssignment == null){
            toast_maker("Sorry no assignment is available");
            return false;
        }
        return true;
    }

    public void updateAssignment(){
        currentAssignment.setAssignmentName(assignName.getText().toString());
        currentAssignment.setScore(Double.parseDouble(assignGot.getText().toString()));
        currentAssignment.setMaxScore(Double.parseDouble(assignTotal.getText().toString()));
        mAssignmentDao.update(currentAssignment);
    }

    public void get_screen(){
        updateAssignmentText = findViewById(R.id.updateAssignmentText);
        assignName = findViewById(R.id.assignmentName);
        assignGot = findViewById(R.id.assignmentGot);
        assignTotal = findViewById(R.id.assignmentTotal);


        Intent incoming = getIntent();
        id = incoming.getIntExtra(UPDATE_ASSIGNMENT_ID, -1);

        updateAssignmentText.setText("UPDATE ASSIGNMENT USER ID:" + Integer.toString(id));

        goBack = findViewById(R.id.goBack);
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                to_logged_in_screen(id);
            }
        });

        spinnerCourses = findViewById(R.id.spinnerCourses);
        spinnerAssignments = findViewById(R.id.spinnerAssignments);


        populate_courses(id);
        spinnerCourses.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                toast_maker("something selected!"+ Integer.toString(i)); // i here is the index value
//                courses.get(i); // so this is the object in question
                Log.i("The name of the course is ", coursesNames.get(i) + " which is at index " + Integer.toString(i));
                Log.i("On courses it is ", courses.get(i).getCourseName()); // they are the same, just needed to make sure

                populate_assignments(id, courses.get(i).getCourseId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                toast_maker("nothing selected it seems");
            }
        });

        spinnerAssignments.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                toast_maker("The assignment chosen is " + assignments.get(i).getAssignmentName());
                set_current_assignment(assignments.get(i));
                set_edits();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                toast_maker("Sorry no assignment to be selected");
            }
        });

        assignUpdate = findViewById(R.id.buttonUpdate);
        assignUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check_if_possible()){
                    updateAssignment();
                    toast_maker("Assignment has being updated!");
                    to_logged_in_screen(id);
                }

            }
        });

    }

    public static Intent UpdateAssignmentIntent(Context context, int userId){
        Intent intent = new Intent(context, UpdateAssignment.class);
        intent.putExtra(UPDATE_ASSIGNMENT_ID, userId);
        return intent;
    }
}