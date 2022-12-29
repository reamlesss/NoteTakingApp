package com.example.notetakingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashSet;

public class addnoteactivity extends AppCompatActivity {
    String note = null;
    String titleadd = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnoteactivity);
        EditText r = (EditText) findViewById(R.id.Textedit);
        EditText title = (EditText) findViewById(R.id.title2);
        Intent ab = getIntent();
        Button done = findViewById(R.id.done2);
        Button delete = findViewById(R.id.delete2);

        title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                         titleadd = charSequence.toString();


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        r.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                 note = charSequence.toString();





            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sh = getApplicationContext().getSharedPreferences("com.example.notetakingapp", Context.MODE_PRIVATE);
                MainActivity.notes.add(note);
                MainActivity.titles.add(titleadd);

                MainActivity.adapter.notifyDataSetChanged();

                HashSet<String> setN = new HashSet<>(MainActivity.notes);
                sh.edit().putStringSet("notes",setN).apply();
                HashSet<String> setT = new HashSet<>(MainActivity.titles);
                sh.edit().putStringSet("titles",setT).apply();
                Toast.makeText(getApplicationContext(),"Note saved",Toast.LENGTH_SHORT).show();
                finish();

            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}