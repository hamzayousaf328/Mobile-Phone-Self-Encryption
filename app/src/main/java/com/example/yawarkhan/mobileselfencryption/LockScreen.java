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
 * Created by Yawar Khan on 5/23/2018.
 */
public class LockScreen extends AppCompatActivity {

    String pin;

    EditText pinText;
    Button enterPin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enter_pin);

        SharedPreferences settings = getSharedPreferences("PREFS", 0);
        pin = settings.getString("pin", "");

        pinText = (EditText)findViewById(R.id.pinEditTextEnter);
        enterPin = (Button)findViewById(R.id.enterPinButton);

        enterPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = pinText.getText().toString();
                if(text.equals(pin)){;
                    finish();
                }
                else{
                    Intent startHomescreen = new Intent(Intent.ACTION_MAIN);
                    startHomescreen.addCategory(Intent.CATEGORY_HOME);
                    startHomescreen.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(startHomescreen);
                    Toast.makeText(LockScreen.this,"Wrong Password!" , Toast.LENGTH_SHORT).show();
                }
            }
        });



    }

}
