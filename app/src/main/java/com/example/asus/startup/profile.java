package com.example.asus.startup;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

public class profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Button btn = (Button)findViewById(R.id.button);
        final EditText ed = (EditText)findViewById(R.id.editText2);
        final EditText ed2 = (EditText)findViewById(R.id.editText);
        Switch s1 = (Switch) findViewById(R.id.switch1);
        Switch s2 = (Switch) findViewById(R.id.switch2);

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        ed.setText( prefs.getString("name", ""));
        ed2.setText( prefs.getString("tel", ""));


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = getSharedPreferences("prefs", MODE_PRIVATE).edit();
                editor.putString("name", String.valueOf(ed.getText()));
                editor.putString("tel", String.valueOf(ed2.getText()));
                editor.apply();
            }
        });
    }
}
