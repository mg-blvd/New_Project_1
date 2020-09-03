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

public class DeleteCourse extends AppCompatActivity {
    private static final String DELETE_COURSE_ID = "com.example.cst438_project1.DeleteCourse";

    int id;

    TextView deleteCourseText;

    Button goBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_course);

        get_screen();
    }

    public void toast_maker(String str){
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    public void to_logged_in_screen(int userId){
        Intent intent = LoggedInHome.LoggedInIntent(DeleteCourse.this, userId);
        Log.i("Moving view","From DeleteCourse to LoggedIn");
        startActivity(intent);
    }

    public void get_screen(){
        deleteCourseText = findViewById(R.id.deleteCourseText);

        Intent incoming = getIntent();
        id = incoming.getIntExtra(DELETE_COURSE_ID, -1);

        deleteCourseText.setText("Delete Course,USER ID:" + Integer.toString(id));

        goBack = findViewById(R.id.goBack);
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                to_logged_in_screen(id);
            }
        });
    }

    public static Intent DeleteCourseIntent(Context context, int userId){
        Intent intent = new Intent(context, DeleteCourse.class);
        intent.putExtra(DELETE_COURSE_ID, userId);
        return intent;
    }


}