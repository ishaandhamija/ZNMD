package com.example.ishaandhamija.znmd;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    ViewPager viewPager;
    TabLayout tabLayout;
    Fragment1 fragment1;
    Fragment2 fragment2;
    Fragment3 fragment3;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    public String mailMessage="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                promptSpeechInput();
//                if (mailMessage != ""){
//                    Log.d("YY", "onClick: Yaha hu");
////                    sendMessage("9958321789",mailMessage);
//                }
            }
        });


        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        viewPager.setOffscreenPageLimit(4);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager)
    {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        fragment1 = new Fragment1();
        fragment2 = new Fragment2();
        fragment3 = new Fragment3();
        adapter.addFragment(fragment1,"Emergency");
        adapter.addFragment(fragment3," SOS Contacts");
        adapter.addFragment(fragment2,"Crime Analysis");
        viewPager.setAdapter(adapter);
    }

    //---------------------------------------------------------------------------voice recogn.

    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "Say something");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    "Sorry! Your device doesn\\'t support speech input",
                    Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    //Toast.makeText(this,result.get(0) , Toast.LENGTH_SHORT).show();
//                    mailMessage=result.get(0);
//                    sendMessage("9958421789",result.get(0));
//                    String i = Fragment3.sosContacts.get(0);
                    for (int i=0;i<5;i++){
                        sendMessage(Fragment3.sosContacts.get(i),result.get(0));
                    }
                }
                break;
            }

        }
    }
    //------------------------------------------------------------------------

    public boolean sendMessage(String phno, String msg) {
        try {
            if (phno == null) {
                return false;
            } else {
                SmsManager smsmanager = SmsManager.getDefault();
                smsmanager.sendTextMessage(phno, null, msg + "\nI am at A 13 A Noida Uttar Pradesh", null, null);
                Toast.makeText(this, "Message Sent", Toast.LENGTH_SHORT).show();
                return true;
            }
        }
        catch (Exception e)
        {
            Toast.makeText(this, "MessgAct Ecxp", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

}
