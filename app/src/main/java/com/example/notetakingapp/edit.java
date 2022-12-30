package com.example.notetakingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.Calendar;
import java.util.HashSet;

public class edit extends AppCompatActivity {

    String notechange = null;
    String titlechange = null;




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        System.out.println("IT STARTED AT LEAST");
        setContentView(R.layout.activity_edit);
        EditText notetext = (EditText) findViewById(R.id.Textedit);
        EditText title = (EditText) findViewById(R.id.title2);
        Intent intent = getIntent();
        int noteid = intent.getIntExtra("noteID",-1);



        Button delete = findViewById(R.id.delete);
        Button done = findViewById(R.id.done);
        Button share = findViewById(R.id.sharebutton);

        System.out.println("button work");

        if(noteid != -1){

            notetext.setText(MainActivity.notes.get(noteid).getDescription());
        }
        if(noteid != -1) {
            title.setText(MainActivity.notes.get(noteid).getName());
        }



        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent send = new Intent(Intent.ACTION_SEND);
                send.setType("text/plain");
                String shareTittle = MainActivity.notes.get(noteid).getName();
                String shareNote = MainActivity.notes.get(noteid).getDescription();
                String shareboth = shareTittle+": "+shareNote;
                send.putExtra(Intent.EXTRA_TEXT,shareboth);
                startActivity(Intent.createChooser(send,"Share with"));
            }
        });













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

        notetext.addTextChangedListener(new TextWatcher() {
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
                if(titlechange != null){
                    MainActivity.notes.get(noteid).setName(titlechange);
                }
                if(notechange != null){
                    MainActivity.notes.get(noteid).setDescription(notechange);
                }


                SharedPreferences sharedPreferences = getSharedPreferences("notes", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                Gson gson = new Gson();
                String json = gson.toJson(MainActivity.notes);
                editor.putString("notes",json);
                editor.apply();

                MainActivity.adapter.notifyDataSetChanged();
                Toast.makeText(getApplicationContext(),"Note edited",Toast.LENGTH_SHORT).show();
                finish();

            }
        });



        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.notes.remove(noteid);

//                t.setText("");
//                title.setText("");
//                MainActivity.titles.remove(noteid);
//                MainActivity.notes.remove(noteid);
                MainActivity.adapter.notifyDataSetChanged();
//                SharedPreferences sh = getApplicationContext().getSharedPreferences("com.example.notetakingapp", Context.MODE_PRIVATE);
//                HashSet<String> setN = new HashSet<>(MainActivity.notes);
//                HashSet<String> setT = new HashSet<>(MainActivity.titles);
//                sh.edit().putStringSet("notes",setN).apply();
//                sh.edit().putStringSet("titles",setT).apply();

                finish();

            }
        });
    }

}