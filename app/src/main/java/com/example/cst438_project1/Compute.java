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

public class Compute extends AppCompatActivity {
    private static final String COMPUTE_ID = "com.example.cst438_project1.Compute";



    int id;

    TextView computeText;
    Button goBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compute);

        get_screen();
    }

    public void toast_maker(String str){
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    public void to_logged_in_screen(int userId){
        Intent intent = LoggedInHome.LoggedInIntent(Compute.this, userId);
        Log.i("Moving view","From Compute to LoggedIn");
        startActivity(intent);
    }

    public void get_screen(){
        computeText = findViewById(R.id.computeText);

        Intent incoming = getIntent();
        id = incoming.getIntExtra(COMPUTE_ID, -1);

        computeText.setText("Compute,USER ID:" + Integer.toString(id));


        goBack = findViewById(R.id.goBack);
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                to_logged_in_screen(id);
            }
        });

    }

    public static Intent ComputeIntent(Context context, int userId){
        Intent intent = new Intent(context, Compute.class);
        intent.putExtra(COMPUTE_ID, userId);
        return intent;
    }
}