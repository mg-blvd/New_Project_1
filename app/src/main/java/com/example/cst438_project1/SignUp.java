package com.example.cst438_project1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.cst438_project1.DbFiles.StudentDatabase;
import com.example.cst438_project1.DbFiles.User;
import com.example.cst438_project1.DbFiles.UserDao;

public class SignUp extends AppCompatActivity {

    TextView signUpText;
    EditText userName;
    EditText passWord;
    Button signUp;
    Button backOne;

    private UserDao mUserDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mUserDao = Room.databaseBuilder(this, StudentDatabase.class, StudentDatabase.USER_TABLE)
                .allowMainThreadQueries()
                .build()
                .getUserDao();

        get_screen();
    }

    /**
     *  Determines whether the incoming user can not be queried, see if user is unique
     * @return a boolean identifying the username is a duplicate(false) or not(true)
     * @author Jonathan Quintero
     */
    public boolean is_unique(){
        if(mUserDao.getUserWithUsername(userName.getText().toString()) == null){
            return true;
        }
        return false;
    }

    /**
     *  Moves the view to the Main activity
     * @author Jonathan Quintero
     */
    public void return_home(){
        Intent intent = new Intent(SignUp.this, MainActivity.class);
        startActivity(intent);
        Log.i("Moving view", "From Sign Up to Main");
    }

    /**
     * Determines if the user's inputs are valid for user creation
     * @param username - the string username the user wants
     * @param password - the string password the user wants
     * @author Jonathan Quintero
     */

    public boolean input_check(String username, String password){
        if(username.length() < 4 || password.length() < 4){
            signUpText.setText("Please make sure there are at least four characters in each field");
            toast_maker("Please make sure there are at least four characters in each field");
            return false;
        }
        else if(is_unique()){
            return true;
        }
        toast_maker("Please make the Username more Unique");
        return false;

    }

    /**
     * Adds the user to the database
     * @author Jonathan Quintero
     */
    public void insert_user(){
        User user = new User(userName.getText().toString(), passWord.getText().toString());
        mUserDao.insert(user);
        Log.i("Made Item", "Made user");
    }

    /**
     *  Creates toasts based on the string
     * @param str - string that the user will see in toast
     */
    public void toast_maker(String str){
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    /**
     *  Gets the view attributes and attaches functionality
     * @author Jonathan Quintero
     */
    public void get_screen(){
        signUpText = findViewById(R.id.signUpText);
        userName = findViewById(R.id.userName);
        passWord = findViewById(R.id.passWord);


        signUp = findViewById(R.id.button);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // check if user can sign up
                if(input_check(userName.getText().toString(), passWord.getText().toString())){
                    insert_user();
                    toast_maker("Welcome, " + userName.getText().toString());
                    return_home(); // then we go back to the Main activity
                }

            }
        });

        backOne = findViewById(R.id.returnBack);
        backOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                return_home();
            }
        });
    }
}