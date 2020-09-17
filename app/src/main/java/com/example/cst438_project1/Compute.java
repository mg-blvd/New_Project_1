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

/**
 * <h1>Compute</h1>
 * <p>
 *     Displays the user's active courses by displaying them in a RecyclerView with letter grades.
 *     The user will have the option to tap on a course name to peek at the course's assignments
 *     and grades.
 * </p>
 * @see androidx.appcompat.app.AppCompatActivity
 */
public class Compute extends AppCompatActivity {
    private static final String COMPUTE_ID = "com.example.cst438_project1.Compute";
    private List<Course> mCourses;
    int id;
    private CourseDao mCourseDao;

    Button goBack;

    /**
     * Sets up the activity and sets up the DAO and calls the get_screen() method
     * @param savedInstanceState Bundle
     */
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
        Intent intent = LoggedInHome.LoggedInIntent(Compute.this, userId);
        Log.i("Moving view","From Compute to LoggedIn");
        startActivity(intent);
    }

    /**
     * Sets up the GUI elements and populates the RecyclerView with a list of courses and their
     * letter grades
     */
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

    /**
     * Gets a list of courses associated with the current user. No user will receive other user's
     * courses.
     * @return List&ltCourse&gt: A list of the user's courses
     */
    public List<Course> getCourses() {
        List<Course> userCourses = mCourseDao.getUserCourses(id);
        return userCourses;
    }

    /**
     * Gets the current intent and passes the user ID to a different activity
     * @param context Context: Context of the app
     * @param userId int: ID of current active user
     * @return Intent
     */
    public static Intent ComputeIntent(Context context, int userId){
        Intent intent = new Intent(context, Compute.class);
        intent.putExtra(COMPUTE_ID, userId);
        return intent;
    }
}