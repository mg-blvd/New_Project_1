package com.example.cst438_project1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.cst438_project1.DbFiles.Assignment;
import com.example.cst438_project1.DbFiles.AssignmentDao;
import com.example.cst438_project1.DbFiles.StudentDatabase;

import java.util.List;

/**
 * <h1>Assignment View</h1>
 * <p>
 *     Displays the course's assignments in a RecyclerView with their names and letter grades.
 * </p>
 * @see androidx.appcompat.app.AppCompatActivity
 */
public class AssignmentView extends AppCompatActivity {
    private List<Assignment> mAssignments;
    int courseID, studentID;
    private AssignmentDao mAssignmentDao;

    TextView courseName;
    Button back;

    /**
     * Sets up the activity and sets up the DAO, assigns the GUI elements and calls the getScreen() method
     * @param savedInstanceState Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment_view);

        mAssignmentDao = Room.databaseBuilder(this, StudentDatabase.class, StudentDatabase.ASSIGNMENT_TABLE)
                .allowMainThreadQueries()
                .build()
                .getAssignmentDao();

        courseName = (TextView) findViewById(R.id.currentCourseName);
        back = (Button) findViewById(R.id.backButton);

        getScreen();
    }

    /**
     * Sets up the RecyclerView and gets the student and course ID from the application intent.
     */
    public void getScreen() {
        Intent intent = getIntent();
        studentID = intent.getIntExtra("studentID", -1);
        courseID = intent.getIntExtra("courseID", -1);
        courseName.setText(intent.getStringExtra("courseName"));

        RecyclerView rvAssignments = (RecyclerView) findViewById(R.id.rvAssignments);
        mAssignments = mAssignmentDao.getUserSpecificCourseAssignments(studentID, courseID);
        AssignmentAdapter adapter = new AssignmentAdapter(mAssignments);
        rvAssignments.setAdapter(adapter);
        rvAssignments.setLayoutManager(new LinearLayoutManager(this));

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toCompute = Compute.ComputeIntent(AssignmentView.this, studentID);
                startActivity(toCompute);
            }
        });
    }
}