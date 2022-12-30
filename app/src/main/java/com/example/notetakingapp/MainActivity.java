package com.example.notetakingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


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


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import java.lang.reflect.Type;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

   static ArrayList<Note> notes = new ArrayList<>();

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
                saveData();
                MainActivity.adapter.notifyDataSetChanged();
                Toast.makeText(this,"All notes have been deleted",Toast.LENGTH_LONG).show();
                break;
            case R.id.Hint:
                hintDialog();
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;

    }
        public void saveData(){
         SharedPreferences sharedPreferences = getSharedPreferences("notes", MODE_PRIVATE);
         SharedPreferences.Editor editor = sharedPreferences.edit();
            Gson gson = new Gson();
            String json = gson.toJson(notes);
            editor.putString("notes",json);
            editor.apply();
        }

        public void loadData(){
            SharedPreferences sharedPreferences = getSharedPreferences("notes", MODE_PRIVATE);
            Gson gson = new Gson();
            String json = sharedPreferences.getString("notes",null);
            Type type = new TypeToken<ArrayList<Note>>() {}.getType();
            notes = gson.fromJson(json,type);



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
        loadData();

        System.out.println(notes);


        if(notes.size() <= 0){

            notes.add(new Note("Example","This is a example note, here you can write your note"));
            saveData();

        }else{
            notes = new ArrayList(notes);



        }

         adapter = new ArrayAdapter(getApplicationContext(), R.layout.text,notes);

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