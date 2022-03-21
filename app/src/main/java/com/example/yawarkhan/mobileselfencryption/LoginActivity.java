package com.example.yawarkhan.mobileselfencryption;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class LoginActivity extends AppCompatActivity  {

    private Toolbar mToolbar;

    private EditText mLoginEmail;
    private EditText mLoginPassword;

    private Button mLogin_btn;
    int counter=2;
    private ProgressDialog mLoginProgress;
    TextView registerlink,forgetpassword;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    Button videolink;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

//Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_login);

        mLoginProgress = new ProgressDialog(this);
        mLoginEmail = (EditText) findViewById(R.id.login_email);
        //layoutpassword=(TextInputLayout)findViewById(R.id.layoutpassword);
        mLoginPassword = (EditText) findViewById(R.id.login_password);
        mLogin_btn = (Button) findViewById(R.id.login_btn);
        mLoginPassword.setTransformationMethod(new PasswordTransformationMethod());
      //  mLoginPassword.setTransformationMethod(null);
        forgetpassword=(TextView)findViewById(R.id.forgetpassword);

        forgetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginActivity.this, "Forget Password", Toast.LENGTH_SHORT).show();
           //     startActivity(new Intent(LoginActivity.this, ForgetPassword.class));
            }
        });

        mLogin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = mLoginEmail.getText().toString();
                String password = mLoginPassword.getText().toString();
                if(email.equals(""))

                {
                    mLoginEmail.setError("Enter Email");

                }
                else if(password.equals(""))
                {
                    mLoginPassword.setError("Enter Password");
                }
                else if (email.matches(emailPattern) == false) {
                    mLoginEmail.setError("Invalid email");

                }
                else {


          //          mLoginProgress.setTitle("Logging In");
                //    mLoginProgress.setMessage("Please wait while we check your credentials.");
                /////    mLoginProgress.setCanceledOnTouchOutside(false);
                ///    mLoginProgress.show();

                  //  loginUser(email, password);

                }

            }
        });

        mLoginPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (mLoginPassword.getRight() -    mLoginPassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // your action here
                        counter++;

                        if ( counter%2!=0){//set here your drawable name(your_drawable)
                            int imgResource = R.drawable.ic_action_visible;
                           // Toast.makeText(getApplicationContext(), "working", Toast.LENGTH_SHORT).show();
                            int imgResource2 = R.drawable.ic_action_pass;
                            mLoginPassword.setCompoundDrawablesWithIntrinsicBounds(imgResource2, 0, imgResource, 0);
                            mLoginPassword.setTransformationMethod(null);

                        }else{
                            int imgResource = R.drawable.ic_action_invisible;
                            int imgResource2 = R.drawable.ic_action_pass;
                            mLoginPassword.setCompoundDrawablesWithIntrinsicBounds(imgResource2, 0, imgResource, 0);
                            mLoginPassword.setTransformationMethod(new PasswordTransformationMethod());
                            //mLoginPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        }



                      //  mLoginPassword.setTransformationMethod(new PasswordTransformationMethod());

                        //  Toast.makeText(getApplicationContext(), "clickd", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                }
                return false;
            }
        });


    }


}
