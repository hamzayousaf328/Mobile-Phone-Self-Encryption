package com.example.yawarkhan.mobileselfencryption;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Yawar Khan on 5/2/2018.
 */
public class CreatePinActivity extends AppCompatActivity{

    EditText setPinText;
    EditText confirnPinText;
    Button createPin;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_pin);

        setPinText = (EditText)findViewById(R.id.pinEditText);
        confirnPinText = (EditText)findViewById(R.id.ConfirmPin);
        createPin = (Button)findViewById(R.id.createPin);

        createPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text1 = setPinText.getText().toString();
                String text2 = confirnPinText.getText().toString();

                if(text1.equals("")||text2.equals("")){
                    Toast.makeText(CreatePinActivity.this,"No Password Entered!" , Toast.LENGTH_SHORT).show();
                }
                else{
                    if(text1.equals(text2)){
                        SharedPreferences settings = getSharedPreferences("PREFS", 0);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString("pin", text1);
                        editor.apply();
                        Intent i = new Intent(CreatePinActivity.this, MainActivity.class);
                        startActivity(i);
                        finish();

                    }
                    else{
                        Toast.makeText(CreatePinActivity.this,"Password Mismatch!" , Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
}
