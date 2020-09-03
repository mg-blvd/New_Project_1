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

public class DeleteAssignment extends AppCompatActivity {
    private static final String DELETE_ASSIGNMENT_ID = "com.example.cst438_project1.DeleteAssignment";

    int id;

    TextView deleteAssignmentText;
    Button goBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_assignment);

        get_screen();
    }

    public void toast_maker(String str){
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    public void to_logged_in_screen(int userId){
        Intent intent = LoggedInHome.LoggedInIntent(DeleteAssignment.this, userId);
        Log.i("Moving view","From DeleteAssignment to LoggedIn");
        startActivity(intent);
    }

    public void get_screen(){
        //// do the get screen
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

        /// make the button in the loggedin and stuff
    }


    public static Intent DeleteAssignmentIntent(Context context, int userId){
        Intent intent = new Intent(context, DeleteAssignment.class);
        intent.putExtra(DELETE_ASSIGNMENT_ID, userId);
        return intent;
    }
}