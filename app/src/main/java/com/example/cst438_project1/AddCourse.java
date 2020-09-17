package com.example.cst438_project1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cst438_project1.DbFiles.Course;
import com.example.cst438_project1.DbFiles.CourseDao;
import com.example.cst438_project1.DbFiles.StudentDatabase;

/**
 * <h1>Add Course</h1>
 * <p>
 *     Creates a new course for the user to add assignments.
 * </p>
 * @see androidx.appcompat.app.AppCompatActivity
 */
public class AddCourse extends AppCompatActivity {
    private static final String ADD_COURSE_ID = "com.example.cst438_project1.AddCourse";

    int id;

    TextView addCourseText;
    EditText getCourseName;
    Button goBack;
    Button addCourseButton;

    private CourseDao mCourseDao;

    /**
     * Sets up the activity and sets up the DAO and calls the get_screen() method
     * @param savedInstanceState Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);

        mCourseDao = Room.databaseBuilder(this, StudentDatabase.class, StudentDatabase.COURSE_TABLE)
                .allowMainThreadQueries()
                .build()
                .getCourseDao();

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
        Intent intent = LoggedInHome.LoggedInIntent(AddCourse.this, userId);
        Log.i("Moving view","From AddCourse to LoggedIn");
        startActivity(intent);
    }

    /**
     * Sets up the screen by associating the Buttons and the EditTexts with their
     * respective GUI elements. It also gets the course ID from the application intent.
     */
    public void get_screen(){
        addCourseText = findViewById(R.id.addCourseText);
        getCourseName = findViewById(R.id.editTextCourse);

        Intent incoming = getIntent();
        id = incoming.getIntExtra(ADD_COURSE_ID, -1);

        addCourseText.setText("Add Course ID: " + Integer.toString(id));

        goBack = findViewById(R.id.goBack);
        addCourseButton = findViewById(R.id.addCourseButton);
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                to_logged_in_screen(id);
            }
        });
        addCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Course newCourse = new Course(getCourseName.getText().toString(), id);
                mCourseDao.insert(newCourse);
                to_logged_in_screen(id);
            }
        });

    }

    /**
     * Gets the current intent and passes the user ID to a different activity
     * @param context Context: Context of the app
     * @param userId int: ID of current active user
     * @return Intent
     */
    public static Intent AddCourseIntent(Context context, int userId){
        Intent intent = new Intent(context, AddCourse.class);
        intent.putExtra(ADD_COURSE_ID, userId);
        return intent;
    }

}