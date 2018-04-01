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
import android.graphics.drawable.Drawable;
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
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class profile extends Activity {
    DatabaseAccess dbAccess;
    ImageButton bt;
    DatabaseAccess db1,db;
    Switch s1,s2,s3,s4;
    FirebaseUser user;
    String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        final Button update= (Button)findViewById(R.id.update);
        final TextView namee = (TextView) findViewById(R.id.name);
        final EditText phone = (EditText)findViewById(R.id.phone);
         s1 = (Switch) findViewById(R.id.switch1);
         s2 = (Switch) findViewById(R.id.switch2);
         s3 = (Switch) findViewById(R.id.switch3);
         s4 = (Switch) findViewById(R.id.switch4);

        bt=(ImageButton) findViewById(R.id.logout);
        de.hdodenhof.circleimageview.CircleImageView image =(de.hdodenhof.circleimageview.CircleImageView) findViewById(R.id.profile_image);
        update.setVisibility(View.GONE);
         db = DatabaseAccess.getInstance(this);
        db1 = DatabaseAccess.getInstance(this);
        db1.openToWrite();
        db.openToRead();


         user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
           String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();
            // Check if user's email is verified
            boolean emailVerified = user.isEmailVerified();
            namee.setText(name);
            if (photoUrl!=null)
            Picasso.get().load(photoUrl)
                    .resize(80, 80)
                    .centerCrop()
                    .into(image);
             uid = user.getUid();
            //ArrayList<Boolean> allergi =db.getswitch(uid);
            //String num =db.getNum(uid);
//            phone.setText(num);
//            s1.setChecked(allergi.get(0));
//            s2.setChecked(allergi.get(1));
//            s3.setChecked(allergi.get(2));
//            s4.setChecked(allergi.get(3));

        }else{
            //ArrayList<Boolean> allergi =db.getswitch(uid);
//            s1.setChecked(allergi.get(0));
//            s2.setChecked(allergi.get(1));
//            s3.setChecked(allergi.get(2));
//            s4.setChecked(allergi.get(3));
            //String num =db.getNum("OFFLINE");
           // phone.setText(num);
            namee.setText("OFFLINE USER");
            //ArrayList<Boolean> allergi =db.getswitch("OFFLINE");

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
 //                               ArrayList<Boolean> allergi =new ArrayList<>();
//                                allergi.add(s1.isChecked());
//                                allergi.add(s2.isChecked());
//                                allergi.add(s3.isChecked());
//                                allergi.add(s4.isChecked());
                                if (user==null){
//                                db1.updateProfile(allergi,phone.getText().toString(),"OFFLINE");

                                }else{
//                                db1.updateProfile(allergi,phone.getText().toString(),uid);

                                }


                                Toast.makeText(profile.this, "updated!", Toast.LENGTH_SHORT).show();
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
