package com.example.yawarkhan.mobileselfencryption;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Button messages=(Button)findViewById(R.id.Messages);
        messages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), MessageActivity.class);
                startActivity(i);
            }
        });



        Button contact=(Button)findViewById(R.id.Contacts);
        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), ContactActivity.class);
                startActivity(i);
            }
        });

        Button File=(Button)findViewById(R.id.Files);
        File.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), FileActivity.class);
                startActivity(i);
            }
        });

        Button setting=(Button)findViewById(R.id.Settings);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), CreatePinActivity.class);
                startActivity(i);
            }
        });


    }
}
