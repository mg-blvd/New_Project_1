package com.example.cst438_project1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoggedInHome extends AppCompatActivity {

    public static final String LOGGED_IN_ID = "com.example.cst438_project1.LoggedInHome";

    TextView loggedInText;
    Integer id; // Current user's Id

    Button returnOne;
    Button addAssignment;
    Button addCourse;
    Button updateAssignment;
    Button compute;
    Button deleteAssignment;
    Button deleteCourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in_home);


        get_screen();
    }

    /**
     *  Moves view to the Add Assignment activity
     * @param userId - need to pass on the current user's id for querying
     * @author Jonathan Quintero
     */
    public void to_add_assignment(int userId){
        Intent intent = AddAssignment.AddAssignmentIntent(LoggedInHome.this, userId);
        Log.i("Moving view","From LoggedIn to AddAssignment");
        startActivity(intent);
    }

    /**
     *  Moves view to the Add Course activity
     * @param userId - need to pass on the current user's id for querying
     * @author Jonathan Quintero
     */
    public void to_add_course(int userId){
        Intent intent = AddCourse.AddCourseIntent(LoggedInHome.this, userId);
        Log.i("Moving view", "From LoggedIn to AddCourse");
        startActivity(intent);
    }

    /**
     *  Moves view to the Update Assignment activity
     * @param userId - need to pass on the current user's id for querying
     * @author Jonathan Quintero
     */
    public void to_update_assignment(int userId){
        Intent intent = UpdateAssignment.UpdateAssignmentIntent(LoggedInHome.this, userId);
        Log.i("Moving view", "From LoggedIn to UpdateAssignment");
        startActivity(intent);
    }

    /**
     *  Moves view to the To Compute activity
     * @param userId - need to pass on the current user's id for querying
     * @author Jonathan Quintero
     */
    public void to_compute(int userId){
        Intent intent = Compute.ComputeIntent(LoggedInHome.this, userId);
        Log.i("Moving view", "From LoggedIn to Compute");
        startActivity(intent);
    }

    /**
     *  Moves view to the Delete Assignment activity
     * @param userId - need to pass on the current user's id for querying
     * @author Jonathan Quintero
     */
    public void to_delete_assignment(int userId){
        Intent intent = DeleteAssignment.DeleteAssignmentIntent(LoggedInHome.this, userId);
        Log.i("Moving view", "From LoggedIn to DeleteAssignment");
        startActivity(intent);
    }

    /**
     * Moves view to the Delete Course activity
     * @param userId - need to pass on the current user's id for querying
     * @author Jonathan Quintero
     */
    public void to_delete_course(int userId){
        Intent intent = DeleteCourse.DeleteCourseIntent(LoggedInHome.this, userId);
        Log.i("Moving view", "From LoggedIn to DeleteCourse");
        startActivity(intent);
    }

    /**
     *  Moves view to the Main activity
     * @author Jonathan Quintero
     */
    public void return_one(){
        Intent intent = new Intent(LoggedInHome.this, MainActivity.class);
        startActivity(intent);
        Log.i("Moving view", "From Logged In to Main");
    }

    /**
     * Gets view attributes and attaches functionality
     * @author Jonathan Quintero
     */
    public void get_screen(){
        loggedInText = findViewById(R.id.loggedInText);
        Intent incoming = getIntent();
        id = incoming.getIntExtra(LOGGED_IN_ID, -1);

        returnOne = findViewById(R.id.backOne);
        returnOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                return_one();
            }
        });

        addAssignment = findViewById(R.id.addAssignment);
        addAssignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                to_add_assignment(id);
            }
        });

        addCourse = findViewById(R.id.addCourse);
        addCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                to_add_course(id);
            }
        });

        updateAssignment = findViewById(R.id.updateAssignmetn);
        updateAssignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                to_update_assignment(id);
            }
        });

        compute = findViewById(R.id.compute);
        compute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                to_compute(id);
            }
        });

        deleteAssignment = findViewById(R.id.deleteAssignment);
        deleteAssignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                to_delete_assignment(id);
            }
        });

        deleteCourse = findViewById(R.id.deleteCOurse);
        deleteCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                to_delete_course(id);
            }
        });

    }

    /**
     * The Log In intent
     * @param context - The activity that is calling on the intent
     * @param userId - the user's id
     * @return - intent that moves that moves the current activity to this activity
     */
    public static Intent LoggedInIntent(Context context, int userId){
        Intent intent = new Intent(context, LoggedInHome.class);
        intent.putExtra(LOGGED_IN_ID, userId);
        return intent;
    }
}