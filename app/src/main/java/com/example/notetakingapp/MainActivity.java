package com.example.notetakingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import android.widget.Toast;



import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {

   static ArrayList<String> notes = new ArrayList<>();
   static ArrayList<String> titles = new ArrayList<>();

    static ArrayAdapter adapter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.overflowmenu,menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.deleteall:
                notes.clear();
                titles.clear();
                MainActivity.adapter.notifyDataSetChanged();
                SharedPreferences sh = getApplicationContext().getSharedPreferences("com.example.notetakingapp", Context.MODE_PRIVATE);
                HashSet<String> setN = new HashSet<>(notes);
                HashSet<String> setT = new HashSet<>(titles);
                sh.edit().putStringSet("notes",setN).apply();
                sh.edit().putStringSet("titles",setT).apply();
                Toast.makeText(this,"All notes have been deleted",Toast.LENGTH_LONG).show();
                break;
            case R.id.Hint:
                hintDialog();
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;

    }

    public void hintDialog(){
        HintDialog hintDialog = new HintDialog();
        hintDialog.show(getSupportFragmentManager(),"hintdialog");

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);




        ListView listView = (ListView) findViewById(R.id.listview);

        SharedPreferences sh = getApplicationContext().getSharedPreferences("com.example.notetakingapp", Context.MODE_PRIVATE);

        HashSet<String> setT = (HashSet<String>) sh.getStringSet("titles",null);
        HashSet<String> setN = (HashSet<String>) sh.getStringSet("notes",null);




        if(setN == null || setT == null){

            titles.add("example");

            notes.add("u can put here the note");

        }else{
            notes = new ArrayList(setN);
            titles = new ArrayList(setT);


        }

         adapter = new ArrayAdapter(getApplicationContext(), R.layout.text,titles);

        listView.setAdapter(adapter);
        Button add = findViewById(R.id.button);


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent add = new Intent(getApplicationContext(),addnoteactivity.class);
                startActivity(add);
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(getApplicationContext(),edit.class);

                intent.putExtra("noteID",i);

                startActivity(intent);
            }
        });

    }
}