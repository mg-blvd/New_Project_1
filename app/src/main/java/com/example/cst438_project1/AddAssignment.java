package com.example.cst438_project1;

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

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.cst438_project1.DbFiles.Assignment;
import com.example.cst438_project1.DbFiles.AssignmentDao;
import com.example.cst438_project1.DbFiles.Course;
import com.example.cst438_project1.DbFiles.CourseDao;
import com.example.cst438_project1.DbFiles.StudentDatabase;

import java.util.Arrays;
import java.util.List;

/**
 * <h1>Add Assignment</h1>
 * <p>
 *     The student adds a custom assignment with a name, score earned, and the maximum score and
 *     adds the assignment to an existing course.
 * </p>
 * @see androidx.appcompat.app.AppCompatActivity
 * @see android.widget.AdapterView.OnItemSelectedListener
 */
public class AddAssignment extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final String ADD_ASSIGNMENT_ID = "com.example.cst438_project1.AddAssignment";
    private List<Course> mCourses;
    private CourseDao mCourseDao;
    private List<Assignment> mAssignments;
    private AssignmentDao mAssignmentDao;
    private String[] courseNames;


    int studentID, courseID;
    TextView addAssignmentText;
    EditText assignmentName, earnedPoints, maxPoints;
    Button goBack;
    Button confirm;
    Spinner courseSpinner;

    /**
     * Sets up the activity and sets up the DAOs and calls the get_screen() method
     * @param savedInstanceState Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assignment);

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

    /**
     * Makes a simple pop-up toast message with a provided string or variables converted to
     * string.
     * @param str String
     */
    public void toast_maker(String str){
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    /**
     * Transfers the user back to the home screen.
     * @param userId int: ID of the current active user
     */
    public void to_logged_in_screen(int userId){
        Intent intent = LoggedInHome.LoggedInIntent(AddAssignment.this, userId);
        Log.i("Moving view","From AddAssignment to LoggedIn");
        startActivity(intent);
    }

    /**
     * Sets up the screen by associating the Spinner, Buttons, and the EditTexts with their
     * respective GUI elements. It also populates the Spinner with course names and gets the
     * assignment ID through the application intent.
     */
    public void get_screen() {
        addAssignmentText = findViewById(R.id.addAssignmentText);

        Intent incoming = getIntent();
        studentID = incoming.getIntExtra(ADD_ASSIGNMENT_ID, -1);


        mCourses = mCourseDao.getUserCourses(studentID);

        addAssignmentText.setText("Add Assignement, USER ID: " + Integer.toString(studentID));

        assignmentName = (EditText) findViewById(R.id.editAssignmentName);
        earnedPoints = (EditText) findViewById(R.id.editEarned);
        maxPoints = (EditText) findViewById(R.id.editPossible);

        courseNames = getCourseNames();
        toast_maker(Arrays.toString(courseNames) + " Array");

        courseSpinner = (Spinner) findViewById(R.id.courseSpinner);
        courseSpinner.setOnItemSelectedListener(this);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, courseNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        courseSpinner.setAdapter(adapter);

        goBack = findViewById(R.id.goBack);
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                to_logged_in_screen(studentID);
            }
        });

        confirm = (Button) findViewById(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isEmpty(assignmentName) || isEmpty(earnedPoints) || isEmpty(maxPoints)) {
                    toast_maker("One or more fields are empty, please fill them in");
                    return;
                }

                Assignment newAssignment = new Assignment(textToString(assignmentName),
                        textToDouble(earnedPoints),
                        textToDouble(maxPoints),
                        courseID,
                        studentID);

                Course course = getCourse(courseID);


                mAssignmentDao.insert(newAssignment);
                toast_maker(textToString(assignmentName) + " added");
            }
        });

    }

    /**
     * Converts numbers in the text to a double.
     * @param text EditText: Text the user provided in the associated EditText.
     * @return The number in the string as a double.
     */
    public Double textToDouble(EditText text) {
        String str = text.getText().toString();
        Double dou = Double.parseDouble(str);
        return dou;
    }

    /**
     * Converts the text in the provided EditText to string
     * @param text EditText: The EditText field to be converted
     * @return The text as String
     */
    public String textToString(EditText text) {
        return text.getText().toString();
    }

    /**
     * Checks if the EditText field is empty or has no input.
     * @param text EditText
     * @return A boolean state if the field is empty
     */
    public boolean isEmpty(EditText text) {
        return text.getText().toString().matches("");
    }

    /**
     * Gets the current intent and passes the user ID to a different activity
     * @param context Context: Context of the app
     * @param userId int: ID of current active user
     * @return Intent
     */
    public static Intent AddAssignmentIntent(Context context, int userId) {
        Intent intent = new Intent(context, AddAssignment.class);
        intent.putExtra(ADD_ASSIGNMENT_ID, userId);
        return intent;
    }

    /**
     * Acquires the Courses from the course list and retrieves all the course names.
     * @return A String array of course names
     */
    public String[] getCourseNames() {
        String names[] = new String[mCourses.size()];
        for (int i = 0; i < names.length; i++)
            names[i] = mCourses.get(i).getCourseName();
        return names;
    }

    /**
     * Gets a Course object from its associated course ID.
     * @param courseID int: The ID of the requested course.
     * @return A single Course object if found, otherwise null.
     */
    public Course getCourse(int courseID) {
        Course c = null;
        for (int i = 0; i < mCourses.size(); i++)
            if (mCourses.get(i).getCourseId() == courseID) {
                c = mCourses.get(i);
                break;
            }
        return c;
    }

    /**
     * When the user enters the information in other required fields and taps the "Add Assignment"
     * button, it retrieves the course ID from the course list to apply the ID to the new assignment
     * for association.
     * @param adapterView AdapterView&lt&gt: The Spinner where the course was selected
     * @param view View: The View within the Spinner
     * @param position int: The indexed position in the Spinner
     * @param l long: The row ID for the current selection
     * @see android.widget.AdapterView.OnItemSelectedListener
     */
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        for (int i = 0; i < mCourses.size(); i++)
            if (courseNames[position].equals(mCourses.get(i).getCourseName())) {
                courseID = mCourses.get(i).getCourseId();
                break;
            }
    }

    /**
     * Not used, supposed to be invoked when the selection disappears from view
     * @param adapterView AdapterView&lt&gt: The Spinner with no selected item
     */
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}