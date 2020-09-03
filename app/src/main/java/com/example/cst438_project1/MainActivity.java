package com.example.cst438_project1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cst438_project1.DbFiles.AssignmentDao;
import com.example.cst438_project1.DbFiles.CourseDao;
import com.example.cst438_project1.DbFiles.StudentDatabase;
import com.example.cst438_project1.DbFiles.User;
import com.example.cst438_project1.DbFiles.UserDao;


public class MainActivity extends AppCompatActivity {


    TextView homeView;
    EditText userName;
    EditText passWord; /// for the sake of convention!

    Button logIn;
    Button signUp;


    private UserDao mUserDao;
    private AssignmentDao mAssignmentDao;
    private CourseDao mCourseDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mUserDao = Room.databaseBuilder(this, StudentDatabase.class, StudentDatabase.USER_TABLE)
                .allowMainThreadQueries()
                .build()
                .getUserDao();

//        mAssignmentDao = Room.databaseBuilder(this, StudentDatabase.class, StudentDatabase.ASSIGNMENT_TABLE)
//                .allowMainThreadQueries()
//                .build()
//                .getAssignmentDao();
//
//        mCourseDao = Room.databaseBuilder(this, StudentDatabase.class, StudentDatabase.COURSE_TABLE)
//                .allowMainThreadQueries()
//                .build()
//                .getCourseDao();


//        Toast.makeText(this, "Everything was built correctly", Toast.LENGTH_SHORT).show();

        get_screen();
    }

    public void toast_maker(String str){
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    public void check_exists(){
        User user = mUserDao.getUserWithUsername(userName.getText().toString());
        if(user == null){
            toast_maker("No Username " + userName.getText().toString());
            return;
        }
        if(user.getPassword().equals(passWord.getText().toString())){
            toast_maker("Welcome " + user.getUsername());
            to_logged_in(user.getId());
        }
        else{
            toast_maker("Wrong Password");
        }
    }

    public void to_logged_in(int userId){
        Intent intent = LoggedInHome.LoggedInIntent(MainActivity.this, userId);
        Log.i("Moving view","From Main to LoggedIn");
        startActivity(intent);
    }


    public void get_screen(){
        homeView = findViewById(R.id.homeView);
        userName = findViewById(R.id.userName);
        passWord = findViewById(R.id.passWord);


        signUp = findViewById(R.id.signUp);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SignUp.class);
                startActivity(intent);
                Log.i("Moving view", "From Main to Sign Up");
            }
        });

        logIn = findViewById(R.id.logIn);
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check_exists();
            }
        });


    }

}