package com.example.asus.startup;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class blacklist extends Activity {
    int preSelectedIndex = -1;
    boolean CheckState = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blacklist);


        ListView listView = (ListView) findViewById(R.id.listView);
        final ImageView checkBtn = (ImageView) findViewById(R.id.checkBtn);
        final TextView checkText = (TextView)findViewById(R.id.checkText);
        Button shareBtn = (Button)findViewById(R.id.shareBtn);


        final List<Aliments> elements = new ArrayList<>();
        elements.add(new Aliments(false, "Dharm"));
        elements.add(new Aliments(false, "Mark"));
        elements.add(new Aliments(false, "Singh"));
        elements.add(new Aliments(false, "The Mobile Era"));

        final CustomAdapter adapter = new CustomAdapter(this, elements);
        listView.setAdapter(adapter);

        checkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             for(int i =0; i< elements.size();i++){
                 elements.get(i).setSelected(CheckState);

             }
             adapter.notifyDataSetChanged();
            if(CheckState){
                 CheckState = false;
                 checkBtn.setBackgroundResource(R.drawable.checked);
                 checkText.setText("Uncheck All");
            }else{
                 CheckState = true;
                 checkBtn.setBackgroundResource(R.drawable.check);
                checkText.setText("Check All");

            }
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Aliments ali = elements.get(i);

                if (ali.isSelected())
                    ali.setSelected(false);

                else
                    ali.setSelected(true);

                elements.set(i, ali);

                //now update adapter
                adapter.updateRecords(elements);
            }
        });

        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
          String message = "Allergy Causing Products : \n";
                for(int i =0; i< elements.size();i++){
                   if( elements.get(i).isSelected){
                     message += elements.get(i).getName();
                     message +="\n";
                   }

                    Intent intent2 = new Intent(); intent2.setAction(Intent.ACTION_SEND);
                    intent2.setType("text/plain");
                    intent2.putExtra(Intent.EXTRA_TEXT, message );
                    startActivity(Intent.createChooser(intent2, "Share via"));


                }
            }
        });

    }
    }

