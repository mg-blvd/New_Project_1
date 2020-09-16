package com.example.cst438_project1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cst438_project1.DbFiles.Assignment;
import com.example.cst438_project1.DbFiles.AssignmentDao;
import com.example.cst438_project1.DbFiles.Course;
import com.example.cst438_project1.DbFiles.CourseBasicInfo;
import com.example.cst438_project1.DbFiles.CourseDao;
import com.example.cst438_project1.DbFiles.StudentDatabase;
import com.example.cst438_project1.RecyclerViewFiles.DeleteAssignmentAdapter;

import java.util.ArrayList;
import java.util.List;

public class DeleteAssignment extends AppCompatActivity {
    private static final String DELETE_ASSIGNMENT_ID = "com.example.cst438_project1.DeleteAssignment";
    int id; //student ID we get from the intent
    CourseDao mCourseDao;
    AssignmentDao mAssignmentDao;
    List<CourseBasicInfo> mCourses;
    List<Assignment> mAssignments;

    TextView deleteAssignmentText;
    Button goBack;
    Spinner courseDropDown;
    private RecyclerView mRecyclerView;
    private DeleteAssignmentAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_assignment);

        setupDaos();
        get_screen();
    }

    /**
     * A quick utility function to create intents quickly
     * @param str - The message that we need to print
     */
    public void toast_maker(String str){
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    /**
     * Funtion that allows us to go back to the logged-in screen with all the buttons
     * @param userId - we must pass in the the ID of the user
     */
    public void to_logged_in_screen(int userId){
        Intent intent = LoggedInHome.LoggedInIntent(DeleteAssignment.this, userId);
        Log.i("Moving view","From DeleteAssignment to LoggedIn");
        startActivity(intent);
    }

    /**
     * Does all the screen setup by connecting buttons, spinners, etc.
     * @author Jonathan Quintero
     */
    public void get_screen(){
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

        initializeCourseSpinner();
        initializeRecyclerView();
    }

    /**
     * Initializes the spinner that displays all the courses
     * @author Misael Guijarro
     */
    private void initializeCourseSpinner() {
        courseDropDown = findViewById(R.id.deleteCourseDropdown);
        mCourses = mCourseDao.getUserCourseBasicInfo(id); //list to populate spinner

        courseDropDown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getAssignments(); //we get the assignments for the selected course
                mAdapter.onRefreshAdapter(mAssignments); //we refresh RecyclerView with new assignments
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        populateDropdown();
    }

    /**
     * Initializes the RecyclerView
     * @author Misael Guijarro
     */
    private void initializeRecyclerView() {
        mRecyclerView = findViewById(R.id.deleteCourseRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        createRecyclerAdapter();
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new DeleteAssignmentAdapter.OnItemClickeListener() {
            @Override
            public void onItemClick(int position) {
                Assignment pickedAssignment = mAssignments.get(position);
                onDeleteAlertDialog(pickedAssignment);
                //When an assignment is chosen, we send a message to the user to confirm
            }
        });
    }


    /**
     * sets up the Daos
     * @author Misael Guijarro
     */
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

    /**
     * populates the spinner with the user's courses
     * @author Misael Guijarro
     */
    private void populateDropdown() {
        List<String> dropdownVals = extractCourseNamesToList();

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, dropdownVals);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        courseDropDown.setAdapter(dataAdapter);
    }

    /**
     * gets all the assignments for the currently selected course in the spinner.
     */
    private void getAssignments() {
        String courseName = courseDropDown.getSelectedItem().toString();
        Integer courseId = 1;
        //Loop over all the courses and see which matches with the name we got from the spinner
        for(CourseBasicInfo course : mCourses) {
            if(course.getCourseName().equals(courseName)) {
                courseId = course.getCourseId();
            }
        }
        mAssignments = mAssignmentDao.getUserSpecificCourseAssignments(id, courseId);
    }

    /**
     * creates and initializes the recycler for first time use
     * @author Misael Guijarro
     */
    private void createRecyclerAdapter() {
        getAssignments();
        mAdapter = new DeleteAssignmentAdapter(mAssignments);
    }

    /**
     * Gets all the names of the courses and saves them into a list
     * @return - a list of strings that contain the name of the courses the user has registered
     */
    private List<String> extractCourseNamesToList() {
        List<String> courseNames = new ArrayList<>();

        for(CourseBasicInfo course : mCourses) {
            courseNames.add(course.getCourseName());
        }

        return courseNames;
    }

    /**
     * The intent to come to the delete activity
     * @param context - The context we are coming from
     * @param userId - the id of the user
     * @return - an intent to bea able to come to this activity
     */
    public static Intent DeleteAssignmentIntent(Context context, int userId){
        Intent intent = new Intent(context, DeleteAssignment.class);
        intent.putExtra(DELETE_ASSIGNMENT_ID, userId);
        return intent;
    }

    /*
    referenced from:
    https://developer.android.com/guide/topics/ui/dialogs
     */

    /**
     * We create a dialog to see if the user actually wants to delete the assignment they have clicked
     * @param pickedAssignment - This is the assignmet that the user picked
     */
    private void onDeleteAlertDialog(final Assignment pickedAssignment) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to delete " +
                        pickedAssignment.getAssignmentName() + "?")
                .setTitle("Delete Assignment Alert");

        builder.setPositiveButton(R.string.delete_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //If so, we delete the assignment and go back to the logged in page
                toast_maker("Assignment was deleted");
                deleteAnAssignment(pickedAssignment);
                Intent intent = LoggedInHome.LoggedInIntent(getParent(), id);
                startActivity(intent);
            }
        });
        builder.setNegativeButton(R.string.delete_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //If not, we just a print a message for the user
                toast_maker("Assignment was not deleted.");
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * We delete the assignment from the DB
     * @param assignment - The assignment we want to delete
     * @author Misael Guijarro
     */
    private void deleteAnAssignment(Assignment assignment) {

        int courseId = assignment.getCourseId();
        Course courseToModify = mCourseDao.getCourseFromId(courseId);
        mAssignmentDao.delete(assignment);
        List<Assignment> assignmentList = mAssignmentDao.getUserSpecificCourseAssignments(id, courseId);
        //After deleting the assignment, we recalculate the student's grade in the course
        Utility.recalculateGrade(courseToModify, assignmentList, mCourseDao);
    }
}