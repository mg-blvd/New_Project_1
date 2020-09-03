package com.example.cst438_project1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AddCourse extends AppCompatActivity {
    private static final String ADD_COURSE_ID = "com.example.cst438_project1.AddCourse";


    int id;

    TextView addCourseText;
    Button goBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);

        get_screen();
    }

    public void toast_maker(String str){
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    public void to_logged_in_screen(int userId){
        Intent intent = LoggedInHome.LoggedInIntent(AddCourse.this, userId);
        Log.i("Moving view","From AddCourse to LoggedIn");
        startActivity(intent);
    }

    public void get_screen(){
        addCourseText = findViewById(R.id.addCourseText);

        Intent incoming = getIntent();
        id = incoming.getIntExtra(ADD_COURSE_ID, -1);

        addCourseText.setText("Add Course ID: " + Integer.toString(id));

        goBack = findViewById(R.id.goBack);
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                to_logged_in_screen(id);
            }
        });

    }

    public static Intent AddCourseIntent(Context context, int userId){
        Intent intent = new Intent(context, AddCourse.class);
        intent.putExtra(ADD_COURSE_ID, userId);
        return intent;
    }

}