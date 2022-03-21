package com.example.yawarkhan.mobileselfencryption;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {


    FirebaseAuth auth;
    EditText email;
    EditText password;
    Button signup;
    Button login;
    Button offline;
    String pin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences settings = getSharedPreferences("PREFS", 0);
        pin = settings.getString("pin", "");

        signup = (Button)findViewById(R.id.Signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, SignupActivity.class);
                startActivity(i);
            }
        });

        email = (EditText)findViewById(R.id.Email);
        password = (EditText)findViewById(R.id.Password);
        auth = FirebaseAuth.getInstance();



      //final LottieAnimationView animationView = (LottieAnimationView)findViewById(R.id.animation_view);
        /*animationView.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                Intent i = new Intent(MainActivity.this, MenuActivity.class);
                startActivity(i);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });*/
        offline = (Button)findViewById(R.id.OfflineButton);
        offline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pin.equals("")){
                    Intent i = new Intent(MainActivity.this, CreatePinActivity.class);
                    startActivity(i);
                    finish();
                }
                else{
                    Intent i = new Intent(MainActivity.this, EnterPinActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        });
        login=(Button)findViewById(R.id.Login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){


                String em = email.getText().toString();
                final String pass = password.getText().toString();

                if (TextUtils.isEmpty(em)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(pass)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }


                auth.signInWithEmailAndPassword(em, pass).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            if (password.length() < 6) {
                                password.setError("Short Password");
                            } else {
                                Toast.makeText(MainActivity.this, "Authorization Failed", Toast.LENGTH_LONG).show();
                            }
                        }
                        else{
                            Intent i = new Intent(MainActivity.this, MenuActivity.class);
                            startActivity(i);
                            finish();
                        }
                    }
                });


            }
        });

    }
}
