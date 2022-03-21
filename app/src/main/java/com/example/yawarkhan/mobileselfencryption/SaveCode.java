package com.example.yawarkhan.mobileselfencryption;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SaveCode extends AppCompatActivity {

    EditText editText;
    Button code_save_btn;
    String existed_code;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_token);
        editText=(EditText)findViewById(R.id.code_input);
        code_save_btn=(Button)findViewById(R.id.code_save_btn);

        SharedPreferences shf = getSharedPreferences("user_code", MODE_PRIVATE);
        boolean hasPassword = shf.contains("code");
        if(hasPassword) {


            SharedPreferences sharedPreferenceslocation = getSharedPreferences("user_code", Context.MODE_PRIVATE);
            existed_code = sharedPreferenceslocation.getString("code", "");
            editText.setText(existed_code);
        }

        code_save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String code_text=editText.getText().toString();
                if(code_text.equals(""))
                {
                    editText.setError("Can't be empty");
                }
                else {
                    SharedPreferences sharedPreferences = getSharedPreferences("user_code", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("code", code_text);
                    editor.apply();

                    Intent intent = new Intent(SaveCode.this, ActivityDefault.class);
                    startActivity(intent);
                    Toast.makeText(SaveCode.this, "Code Saved", Toast.LENGTH_SHORT).show();
                }
            }
        });





    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }






}
