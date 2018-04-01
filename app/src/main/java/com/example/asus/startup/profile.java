package com.example.asus.startup;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.media.Image;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

import java.io.IOException;

public class profile extends Activity {
    DatabaseAccess dbAccess;
    ImageButton bt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        final Button update= (Button)findViewById(R.id.update);
        final TextView namee = (TextView) findViewById(R.id.name);
        final EditText phone = (EditText)findViewById(R.id.phone);
        Switch s1 = (Switch) findViewById(R.id.switch1);
        Switch s2 = (Switch) findViewById(R.id.switch2);
        Switch s3 = (Switch) findViewById(R.id.switch3);
        Switch s4 = (Switch) findViewById(R.id.switch4);

        bt=(ImageButton) findViewById(R.id.logout);
        de.hdodenhof.circleimageview.CircleImageView image =(de.hdodenhof.circleimageview.CircleImageView) findViewById(R.id.profile_image);
        update.setVisibility(View.GONE);
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        phone.setText( prefs.getString("tel", ""));


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
           String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();
            // Check if user's email is verified
            boolean emailVerified = user.isEmailVerified();
            namee.setText(name);
            image.setImageURI(photoUrl);

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            String uid = user.getUid();
        }
bt.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        PopupMenu pop=new PopupMenu(profile.this,bt);
        pop.getMenuInflater().inflate(R.menu.log,pop.getMenu());

        pop.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                    int id=menuItem.getItemId();

                    if (id==R.id.modif){
                        update.setVisibility(View.VISIBLE);
                        phone.setEnabled(true);

                        update.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                SharedPreferences.Editor editor = getSharedPreferences("prefs", MODE_PRIVATE).edit();
                                editor.putString("tel", String.valueOf(phone.getText()));


                                editor.apply();
                                update.setVisibility(View.GONE);
                                phone.setEnabled(false);



                            }
                        });

                    }else if (id==R.id.logout){

                        AlertDialog.Builder builder1 = new AlertDialog.Builder(profile.this);
                        builder1.setMessage("Are You Sure?");
                        builder1.setCancelable(true);

                        builder1.setPositiveButton(
                                "Yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        FirebaseAuth.getInstance().signOut();
                                        Intent i = new Intent(profile.this,LoginActivity.class);
                                        startActivity(i);
                                        dialog.cancel();
                                    }
                                });

                        builder1.setNegativeButton(
                                "No",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                        AlertDialog alert11 = builder1.create();
                        alert11.show();

                    }




                return true;
            }
        });

        pop.show();

    }
});







    }
}
