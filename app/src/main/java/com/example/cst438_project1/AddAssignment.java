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

public class AddAssignment extends AppCompatActivity {
    private static final String ADD_ASSIGNMENT_ID = "com.example.cst438_project1.AddAssignment";


    int id;

    TextView addAssignmentText;

    Button goBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assignment);


        get_screen();
    }

    public void toast_maker(String str){
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    public void to_logged_in_screen(int userId){
        Intent intent = LoggedInHome.LoggedInIntent(AddAssignment.this, userId);
        Log.i("Moving view","From AddAssignment to LoggedIn");
        startActivity(intent);
    }

    public void get_screen(){
        addAssignmentText = findViewById(R.id.addAssignmentText);

        Intent incoming = getIntent();
        id = incoming.getIntExtra(ADD_ASSIGNMENT_ID, -1);

        addAssignmentText.setText("Add Assignemnt,USER ID:" + Integer.toString(id));

        goBack = findViewById(R.id.goBack);
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                to_logged_in_screen(id);
            }
        });

    }

    public static Intent AddAssignmentIntent(Context context, int userId){
        Intent intent = new Intent(context, AddAssignment.class);
        intent.putExtra(ADD_ASSIGNMENT_ID, userId);
        return intent;
    }

}