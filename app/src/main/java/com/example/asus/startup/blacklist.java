package com.example.asus.startup;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.LabeledIntent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
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

        DatabaseAccess db = DatabaseAccess.getInstance(this);
        db.openToWrite();

           List<String> aller = new ArrayList<>();
           aller = db.getAllAllergin();
        final List<Aliments> elements = new ArrayList<>();
        for(int i =0;i<aller.size();i++){
            elements.add(new Aliments(false,aller.get(i)));
        }

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
                }
                   /* Intent intent2 = new Intent(); intent2.setAction(Intent.ACTION_SEND);
                    intent2.setType("text/plain");
                    intent2.putExtra(Intent.EXTRA_TEXT, message );
                    startActivity(Intent.createChooser(intent2, "Share via"));*/


                onShareClick(view,message);
            }
        });

    }




    public void onShareClick(View v,String msg) {
        Resources resources = getResources();

        Intent emailIntent = new Intent();
        emailIntent.setAction(Intent.ACTION_SEND);
        // Native email client doesn't currently support HTML, but it doesn't hurt to try in case they fix it
        emailIntent.putExtra(Intent.EXTRA_TEXT, msg);
       // emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Sha");
        emailIntent.setType("message/rfc822");

        PackageManager pm = getPackageManager();
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.setType("text/plain");


        Intent openInChooser = Intent.createChooser(emailIntent, "Share Via");

        List<ResolveInfo> resInfo = pm.queryIntentActivities(sendIntent, 0);
        List<LabeledIntent> intentList = new ArrayList<LabeledIntent>();
        for (int i = 0; i < resInfo.size(); i++) {
            // Extract the label, append it, and repackage it in a LabeledIntent
            ResolveInfo ri = resInfo.get(i);
            String packageName = ri.activityInfo.packageName;
            if(packageName.contains("android.email")) {
                emailIntent.setPackage(packageName);
            } else if( packageName.contains("facebook") || packageName.contains("mms") || packageName.contains("android.gm")) {
                Intent intent = new Intent();
                intent.setComponent(new ComponentName(packageName, ri.activityInfo.name));
                intent.setAction(Intent.ACTION_SEND);
                intent.setType("text/plain");
                if(packageName.contains("facebook")) {
                    // Warning: Facebook IGNORES our text. They say "These fields are intended for users to express themselves. Pre-filling these fields erodes the authenticity of the user voice."
                    // One workaround is to use the Facebook SDK to post, but that doesn't allow the user to choose how they want to share. We can also make a custom landing page, and the link
                    // will show the <meta content ="..."> text from that page with our link in Facebook.
                    intent.putExtra(Intent.EXTRA_TEXT, msg);
                } else if(packageName.contains("mms")) {
                    intent.putExtra(Intent.EXTRA_TEXT, msg);
                } else if(packageName.contains("android.gm")) { // If Gmail shows up twice, try removing this else-if clause and the reference to "android.gm" above
                    intent.putExtra(Intent.EXTRA_TEXT,msg);
                   // intent.putExtra(Intent.EXTRA_SUBJECT, resources.getString(R.string.share_email_subject));
                    intent.setType("message/rfc822");
                }

                intentList.add(new LabeledIntent(intent, packageName, ri.loadLabel(pm), ri.icon));
            }
        }

        // convert intentList to array
        LabeledIntent[] extraIntents = intentList.toArray( new LabeledIntent[ intentList.size() ]);

        openInChooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, extraIntents);
        startActivity(openInChooser);
    }

}

