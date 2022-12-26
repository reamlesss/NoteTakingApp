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

public class edit extends AppCompatActivity {

    String notechange = null;
    String titlechange = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        System.out.println("IT STARTED AT LEAST");
        setContentView(R.layout.activity_edit);
        EditText t = (EditText) findViewById(R.id.Textedit);
        EditText title = (EditText) findViewById(R.id.title2);
        Intent intent = getIntent();

        Button delete = findViewById(R.id.delete);
        Button done = findViewById(R.id.done);

        System.out.println("button work");


        int noteid = intent.getIntExtra("noteID",-1);
        System.out.println(noteid);




        if(noteid != -1){

            t.setText(MainActivity.notes.get(noteid));
        }
        if(noteid != -1){
            title.setText(MainActivity.titles.get(noteid));
        }







        title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                notechange = charSequence.toString();


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        t.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                notechange = charSequence.toString();

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.notes.set(noteid,notechange);
                MainActivity.titles.set(noteid,titlechange);

                SharedPreferences sh = getApplicationContext().getSharedPreferences("com.example.notetakingapp", Context.MODE_PRIVATE);
                HashSet<String> setT = new HashSet<>(MainActivity.titles);
                sh.edit().putStringSet("titles",setT).apply();
                HashSet<String> setN = new HashSet<>(MainActivity.notes);
                sh.edit().putStringSet("notes",setN).apply();
                MainActivity.adapter.notifyDataSetChanged();
                Toast.makeText(getApplicationContext(),"Note edited",Toast.LENGTH_SHORT).show();
                finish();

            }
        });



        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                t.setText("");
                title.setText("");
                MainActivity.titles.remove(noteid);
                MainActivity.notes.remove(noteid);
                MainActivity.adapter.notifyDataSetChanged();
                SharedPreferences sh = getApplicationContext().getSharedPreferences("com.example.notetakingapp", Context.MODE_PRIVATE);
                HashSet<String> setN = new HashSet<>(MainActivity.notes);
                HashSet<String> setT = new HashSet<>(MainActivity.titles);
                sh.edit().putStringSet("notes",setN).apply();
                sh.edit().putStringSet("titles",setT).apply();

                finish();

            }
        });
    }
}